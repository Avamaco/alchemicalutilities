package net.avamaco.alchemicalutilities.entity.custom;

import net.avamaco.alchemicalutilities.entity.ModEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class PhialGrenadeProjectile extends ThrowableItemProjectile {


    public PhialGrenadeProjectile(EntityType<? extends PhialGrenadeProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PhialGrenadeProjectile(EntityType<? extends PhialGrenadeProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public PhialGrenadeProjectile(EntityType<? extends PhialGrenadeProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }


    @Override
    protected Item getDefaultItem() {
        return null;
    }
}
