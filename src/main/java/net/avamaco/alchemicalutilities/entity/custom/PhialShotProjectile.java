package net.avamaco.alchemicalutilities.entity.custom;

import net.avamaco.alchemicalutilities.item.ModItems;
import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.avamaco.alchemicalutilities.util.PhialsUtil;
import net.minecraft.sounds.SoundEvents;
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
import org.jetbrains.annotations.NotNull;

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
    @NotNull
    protected Item getDefaultItem() {
        return ModItems.GLASS_PHIAL.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);

        if (pResult.getEntity() instanceof LivingEntity) {
            ItemStack phialStack = this.getItem();
            if (phialStack.getItem() instanceof PotionPhialItem phial)
                phial.UseOnEntity((LivingEntity)pResult.getEntity(), this.getOwner());
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
        if (phialStack.getItem() instanceof PotionPhialItem phial)
            phial.UseOnBlock(this.position(), pResult.getBlockPos(), pResult.getDirection(), this);

        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        this.playSound(SoundEvents.GLASS_BREAK, 0.5F, 1.0F);
        super.onHit(pResult);
    }
}
