package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.block.ModBlocks;
import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class PhialOfLightItem extends PotionPhialItem {
    public PhialOfLightItem(Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xffffff55;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, Entity user) {
        entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0), user);
        entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0), user);
    }

    @Override
    public void UseExplosion(Vec3 position, Entity source) {
        int dist = 4;
        for (int dx = -dist; dx <= dist; dx+=dist) {
            for (int dy = -dist; dy <= dist; dy+=dist) {
                for (int dz = -dist; dz <= dist; dz+=dist) {
                    if (Math.abs(dx) + Math.abs(dy) + Math.abs(dz) == 3*dist)
                        continue;
                    Vec3 offsetPos = new Vec3(dx, dy, dz);
                    BlockPos blockpos = new BlockPos(position.add(offsetPos));
                    if (source.level.isEmptyBlock(blockpos)) {
                        source.level.setBlockAndUpdate(blockpos, ModBlocks.LIGHT_WISP.get().defaultBlockState());
                    }
                }
            }
        }
    }

    @Override
    public void UseOnBlock(Vec3 position, BlockPos blockPos, Direction direction, Entity source) {
        if (source.level.getBlockState(blockPos).getBlock() == Blocks.AMETHYST_BLOCK) {
            source.level.setBlockAndUpdate(blockPos, ModBlocks.GLISTENING_AMETHYST.get().defaultBlockState());
        }
        else {
            BlockPos target = blockPos.offset(direction.getStepX(), direction.getStepY(), direction.getStepZ());
            if (source.level.isEmptyBlock(target)) {
                source.level.setBlockAndUpdate(target, ModBlocks.LIGHT_WISP.get().defaultBlockState());
        }
        }
    }
}
