package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;

public class PhialOfPropulsionItem extends PotionPhialItem {
    public PhialOfPropulsionItem(Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFF48FFFF;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, LivingEntity user) {
        entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 5), user);
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
                    if (!source.level.isEmptyBlock(blockpos)
                            && PistonBaseBlock.isPushable(blockState, source.level, blockpos, Direction.UP, true, Direction.UP)) {
                        FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(source.level, blockpos, source.level.getBlockState(blockpos));
                        fallingBlockEntity.push((double)dx / (4.0D + source.level.random.nextDouble()), 1.0D, (double)dz / (4.0D + source.level.random.nextDouble()));
                        source.level.destroyBlock(blockpos, false);
                    }
                }
            }
        }
    }


}
