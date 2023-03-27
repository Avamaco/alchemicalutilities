package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class PhialOfWarpingItem extends PotionPhialItem {
    public PhialOfWarpingItem(Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFF2CCAAF;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, Entity user) {
        Vec3 direction = user.getLookAngle().scale(10.0F);
        entity.teleportTo(entity.getX() + direction.x, entity.getY() + direction.y, entity.getZ() + direction.z);
    }

    @Override
    public void UseExplosion(Vec3 position, Entity source, Entity owner) {
        if (owner != null)
            owner.teleportTo(position.x, position.y, position.z);
    }
}
