package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
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
    }

    @Override
    public void UseExplosion(Vec3 position, Entity source) {
        particleBurst(source.level, position.x, position.y, position.z);
    }

    @Override
    public void UseOnBlock(Vec3 position, BlockPos blockPos, Direction direction, Entity source) {
        particleBurst(source.level, position.x, position.y, position.z);
    }

    private void particleBurst(Level level, double x, double y, double z) {
        level.addParticle(ParticleTypes.FLASH, x, y, z, 0, 0, 0);
        for (int yangle = -60; yangle <= 60; yangle+= 20) {
            for (int angle = 0; angle < 360; angle += 10) {
                level.addParticle(ParticleTypes.SMOKE, x, y, z,
                        0.15 * Math.cos(angle), 0.15 * Math.sin(yangle), 0.15 * Math.sin(angle));
            }
        }
    }
}
