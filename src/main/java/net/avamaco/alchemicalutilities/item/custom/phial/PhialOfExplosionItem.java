package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;

public class PhialOfExplosionItem extends PotionPhialItem {
    public PhialOfExplosionItem(Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFFAF3500;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, LivingEntity user) {
        entity.level.explode(entity, entity.getX(), entity.getY(), entity.getZ(), 1.5F, Explosion.BlockInteraction.NONE);
    }

    @Override
    public void UseExplosion(Vec3 position, Entity source) {
        source.level.explode(source, source.getX(), source.getY(0.0625D), source.getZ(), 3.0F, Explosion.BlockInteraction.BREAK);
    }
}
