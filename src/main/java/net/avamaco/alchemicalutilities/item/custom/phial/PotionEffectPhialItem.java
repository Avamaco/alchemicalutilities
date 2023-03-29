package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class PotionEffectPhialItem extends PotionPhialItem {
    private final MobEffect EFFECT;
    private final int DEFAULT_TIME;

    public PotionEffectPhialItem(MobEffect effect, int defaultTime, Properties pProperties) {
        super(pProperties);
        this.EFFECT = effect;
        this.DEFAULT_TIME = defaultTime;
        this.COLOR = effect.getColor();
    }

    @Override
    public void UseOnEntity(LivingEntity entity, Entity user) {
        entity.addEffect(new MobEffectInstance(EFFECT, DEFAULT_TIME, 0), user);
    }

    @Override
    public void UseExplosion(Vec3 position, Entity source) {
        makeAreaOfEffectCloud(new MobEffectInstance(EFFECT, Math.min(DEFAULT_TIME, 100), 0), source, position);
    }

    @Override
    public void UseOnBlock(Vec3 position, BlockPos blockPos, Direction direction, Entity source) {
        makeTinyAOECloud(new MobEffectInstance(EFFECT, Math.min(DEFAULT_TIME, 100), 0), source, position);
    }
}
