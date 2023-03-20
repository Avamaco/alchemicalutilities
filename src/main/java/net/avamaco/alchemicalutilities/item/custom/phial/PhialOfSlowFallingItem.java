package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class PhialOfSlowFallingItem extends PotionPhialItem {
    public PhialOfSlowFallingItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFFFCF4D5;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, LivingEntity user) {
        entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 900, 0), user);
    }
}
