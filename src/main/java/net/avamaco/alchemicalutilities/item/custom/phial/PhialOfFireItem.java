package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.Vec3;

public class PhialOfFireItem extends PotionPhialItem {
    public PhialOfFireItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFFFF5733;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, Entity user) {
        entity.setSecondsOnFire(15);
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
                    if (source.level.isEmptyBlock(blockpos)) {
                        source.level.setBlockAndUpdate(blockpos, BaseFireBlock.getState(source.level, blockpos));
                    }
                }
            }
        }
    }
}
