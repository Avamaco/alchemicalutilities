package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

public class PhialOfSlowFallingItem extends PotionPhialItem {
    public PhialOfSlowFallingItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFFFCF4D5;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, Entity user) {
        entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 900, 0), user);
    }

    @Override
    public void UseExplosion(Vec3 position, Entity source) {
        makeAreaOfEffectCloud(new MobEffectInstance(MobEffects.SLOW_FALLING, 100, 0), source, position);
    }
}
