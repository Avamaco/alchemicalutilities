package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
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
        Level level = entity.getLevel();
        if (!level.isClientSide()) {
            ((ServerLevel)level).sendParticles(ParticleTypes.FIREWORK, entity.getX(), entity.getY(0.1D), entity.getZ(),  20, 0, 0, 0, 0.15D);
        }
        entity.push(0.0D, 1.5D, 0.0D);
    }

    @Override
    public void UseExplosion(Vec3 position, Entity source) {
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -2; dz <= 2; dz++) {
                    if (Math.abs(dx) == 2 && Math.abs(dz) == 2)
                        continue;
                    Vec3 offsetPos = new Vec3(dx, dy, dz);
                    BlockPos blockPos = new BlockPos(position.add(offsetPos));
                    BlockState blockState = source.level.getBlockState(blockPos);
                    // make a new FallingBlockEntity if possible
                    if (!source.level.isEmptyBlock(blockPos)
                            && PistonBaseBlock.isPushable(blockState, source.level, blockPos, Direction.UP, true, Direction.UP)) {

//                        double offsetX = (source.level.random.nextDouble() - 0.5D) / 3.0D;
//                        double offsetY = (source.level.random.nextDouble() - 0.5D) / 3.0D;
//                        double offsetZ = (source.level.random.nextDouble() - 0.5D) / 3.0D;
//
//                        launchBlock(blockPos, source.level, (double)dx / 6.0D + offsetX, 1.0D + offsetY, (double)dz / 6.0D + offsetZ);
                        pushBlock(blockPos, source.level, Direction.UP, 5);
                    }
                }
            }
        }
    }

    @Override
    public void UseOnBlock(Vec3 position, BlockPos blockPos, Direction direction, Entity source) {
        BlockState blockState = source.level.getBlockState(blockPos);
        if (!source.level.isEmptyBlock(blockPos)
                && PistonBaseBlock.isPushable(blockState, source.level, blockPos, direction, true, direction)) {

//            double velocityX = direction.getStepX();
//            double velocityY = direction.getStepY();
//            double velocityZ = direction.getStepZ();
//
//            launchBlock(blockPos, source.level, velocityX, velocityY, velocityZ);
            pushBlock(blockPos, source.level, direction, 5);
        }
    }

    @Override
    public void UseOnEntityEnhanced(LivingEntity entity, Entity user) {
        Level level = entity.getLevel();
        if (!level.isClientSide()) {
            ((ServerLevel)level).sendParticles(ParticleTypes.FIREWORK, entity.getX(), entity.getY(0.1D), entity.getZ(),  20, 0, 0, 0, 0.15D);
        }
        entity.push(0.0D, 2.5D, 0.0D);
    }

    private void launchBlock(BlockPos blockPos, Level level, double velocityX, double velocityY, double velocityZ) {
        BlockState blockState = level.getBlockState(blockPos);
        FallingBlockEntity fallingBlockEntity = null;
        try {
            fallingBlockEntity = new FallingBlockEntity(EntityType.FALLING_BLOCK, level);
            fallingBlockEntity.absMoveTo((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D);
            // add velocity
            fallingBlockEntity.push(velocityX, velocityY, velocityZ);
            // change blockState by reaching a private field
//            Field field = fallingBlockEntity.getClass().getDeclaredField("blockState");
//            field.setAccessible(true);
//            field.set(fallingBlockEntity, blockState);
            // finish adding the FallingBlockEntity
            level.destroyBlock(blockPos, false);
            level.addFreshEntity(fallingBlockEntity);
        }
        catch(Exception e) {
            fallingBlockEntity = FallingBlockEntity.fall(level, blockPos, blockState);
            fallingBlockEntity.push(velocityX, velocityY, velocityZ);
        }
    }

    private void pushBlock(BlockPos blockPos, Level level, Direction direction, int power) {
        BlockState blockState = level.getBlockState(blockPos);
        BlockPos targetPos = blockPos;
        for (int i = 0; i < power; i++) {
            BlockPos newPos = targetPos.offset(direction.getNormal());
            if (level.getBlockState(newPos).isAir())
                targetPos = newPos;
            else
                break;
        }
        if (level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState()))
            level.setBlockAndUpdate(targetPos, blockState);
        if (!level.isClientSide()) {
            ((ServerLevel)level).sendParticles(ParticleTypes.FIREWORK, blockPos.getX(), blockPos.getY(), blockPos.getZ(),  20, direction.getStepX() * power, direction.getStepY() * power, direction.getStepZ() * power, 0.15D);
        }
    }
}
