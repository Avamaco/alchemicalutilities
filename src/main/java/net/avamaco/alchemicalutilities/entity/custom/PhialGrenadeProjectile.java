package net.avamaco.alchemicalutilities.entity.custom;

import net.avamaco.alchemicalutilities.entity.ModEntityTypes;
import net.avamaco.alchemicalutilities.item.ModItems;
import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.avamaco.alchemicalutilities.util.PhialsUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

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
    protected float getGravity() {
        return 0.04F;
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.PHIAL_GRENADE.get();
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);

        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 2.0F, Explosion.BlockInteraction.NONE);

        ItemStack phialStack = PhialsUtil.getChargedPhial(this.getItem());
        if (phialStack != null && phialStack.getItem() instanceof PotionPhialItem) {
            ((PotionPhialItem) phialStack.getItem()).UseExplosion(this.position(), this.getOwner());
        }

        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }
}
