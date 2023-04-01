package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

public class PhialOfPropulsionItem extends PotionPhialItem {
    public PhialOfPropulsionItem(Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFF48FFFF;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, Entity user) {
        entity.push(0.0D, 2.0D, 0.0D);
    }

    @Override
    public void UseExplosion(Vec3 position, Entity source) {
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -2; dz <= 2; dz++) {
                    if (Math.abs(dx) == 2 && Math.abs(dz) == 2)
                        continue;
                    Vec3 offsetPos = new Vec3(dx, dy, dz);
                    BlockPos blockpos = new BlockPos(position.add(offsetPos));
                    BlockState blockState = source.level.getBlockState(blockpos);
                    // make a new FallingBlockEntity if possible
                    if (!source.level.isEmptyBlock(blockpos)
                            && PistonBaseBlock.isPushable(blockState, source.level, blockpos, Direction.UP, true, Direction.UP)) {
                        FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(EntityType.FALLING_BLOCK, source.level);
                        fallingBlockEntity.absMoveTo((double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D);
                        // add velocity
                        double offsetX = (source.level.random.nextDouble() - 0.5D) / 2.0D;
                        double offsetZ = (source.level.random.nextDouble() - 0.5D) / 2.0D;
                        fallingBlockEntity.push((double)dx / 4.0D + offsetX, 1.0D, (double)dz / 4.0D + offsetZ);
                        // change blockstate by reaching a private field
                        try {
                            Field field = fallingBlockEntity.getClass().getDeclaredField("blockState");
                            field.setAccessible(true);
                            field.set(fallingBlockEntity, blockState);
                        }
                        catch(Exception ignored) {}
                        // finish adding the FallingBlockEntity
                        source.level.destroyBlock(blockpos, false);
                        source.level.addFreshEntity(fallingBlockEntity);
                    }
                }
            }
        }
    }


}
