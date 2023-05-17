package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class PhialOfLightItem extends PotionPhialItem {
    public PhialOfLightItem(Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xffffff55;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, Entity user) {
        entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0), user);
        entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0), user);
    }
}
