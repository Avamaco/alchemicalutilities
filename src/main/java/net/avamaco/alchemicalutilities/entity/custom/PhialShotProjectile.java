package net.avamaco.alchemicalutilities.entity.custom;

import net.avamaco.alchemicalutilities.item.ModItems;
import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.avamaco.alchemicalutilities.util.PhialsUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class PhialShotProjectile extends ThrowableItemProjectile {
    
    public PhialShotProjectile(EntityType<? extends PhialShotProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PhialShotProjectile(EntityType<? extends PhialShotProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public PhialShotProjectile(EntityType<? extends PhialShotProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.GLASS_PHIAL.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);

        if (pResult.getEntity() instanceof LivingEntity) {
            ItemStack phialStack = this.getItem();
            if (phialStack != null && phialStack.getItem() instanceof PotionPhialItem) {
                ((PotionPhialItem) phialStack.getItem()).UseOnEntity((LivingEntity)pResult.getEntity(), this.getOwner());
            }
        }

        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);

        ItemStack phialStack = this.getItem();
        if (phialStack != null && phialStack.getItem() instanceof PotionPhialItem) {
            ((PotionPhialItem) phialStack.getItem()).UseOnBlock(this.position(), pResult.getBlockPos(), pResult.getDirection(), this);
        }

        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }
}
