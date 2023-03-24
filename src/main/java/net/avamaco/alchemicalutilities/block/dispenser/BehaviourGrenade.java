package net.avamaco.alchemicalutilities.block.dispenser;

import net.avamaco.alchemicalutilities.entity.ModEntityTypes;
import net.avamaco.alchemicalutilities.entity.custom.PhialGrenadeProjectile;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BehaviourGrenade extends AbstractProjectileDispenseBehavior {
    @Override
    protected Projectile getProjectile(Level pLevel, Position pPosition, ItemStack pStack) {
        PhialGrenadeProjectile grenade = new PhialGrenadeProjectile(
                ModEntityTypes.PHIAL_GRENADE.get(), pPosition.x(), pPosition.y(), pPosition.z(), pLevel);
        grenade.setItem(pStack);
        return grenade;
    }
}
