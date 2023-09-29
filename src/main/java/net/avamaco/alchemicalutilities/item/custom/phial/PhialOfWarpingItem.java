package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
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
        if (!entity.level.isClientSide()) {
            ((ServerLevel)entity.level).sendParticles(ParticleTypes.PORTAL, entity.getX(), entity.getY() + entity.level.random.nextDouble() * 2.0D, entity.getZ(),  32, 0, 0, 0, entity.level.random.nextGaussian());
            entity.getLevel().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 0.3F, 1.0F);
        }
    }

    @Override
    public void UseExplosion(Vec3 position, Entity source, Entity owner) {
        if (owner != null)
            owner.teleportTo(position.x, position.y, position.z);
        if (!source.level.isClientSide()) {
            ((ServerLevel)source.level).sendParticles(ParticleTypes.PORTAL, source.getX(), source.getY() + source.level.random.nextDouble() * 2.0D, source.getZ(),  32, 0, 0, 0, source.level.random.nextGaussian());
            source.getLevel().playSound(null, source.getX(), source.getY(), source.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 0.3F, 1.0F);
        }
    }

    @Override
    public void UseOnBlock(Vec3 position, BlockPos blockPos, Direction direction, Entity source) {
        Entity owner = null;
        if (source instanceof Projectile)
            owner = ((Projectile)source).getOwner();
        if (owner != null)
            owner.teleportTo(position.x, position.y, position.z);
        if (!source.level.isClientSide()) {
            ((ServerLevel)source.level).sendParticles(ParticleTypes.PORTAL, source.getX(), source.getY() + source.level.random.nextDouble() * 2.0D, source.getZ(),  32, 0, 0, 0, source.level.random.nextGaussian());
            source.getLevel().playSound(null, source.getX(), source.getY(), source.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 0.3F, 1.0F);
        }
    }
}
