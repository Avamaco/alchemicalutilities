package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PhialOfSmokeItem extends PotionPhialItem {
    public PhialOfSmokeItem(Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFF464646;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, Entity user) {
        particleBurst(entity.level, entity.getX(), entity.getEyeY(), entity.getZ());
        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 0), user);
    }

    @Override
    public void UseExplosion(Vec3 position, Entity source) {
        for (int i = 0; i < 4; i++)
            particleBurst(source.level, position.x, position.y, position.z);
    }

    @Override
    public void UseOnBlock(Vec3 position, BlockPos blockPos, Direction direction, Entity source) {
        particleBurst(source.level, position.x, position.y, position.z);
    }

    private void particleBurst(Level level, double x, double y, double z) {
        if (!level.isClientSide()) {
            ((ServerLevel)level).sendParticles(ParticleTypes.SMOKE, x, y, z,  50, 0, 0, 0, 0.15D);
            ((ServerLevel)level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z,  75, 0, 0, 0, 0.07D);
            ((ServerLevel)level).sendParticles(ParticleTypes.FLASH, x, y, z,  3, 0, 0, 0, 0);
        }
    }
}
