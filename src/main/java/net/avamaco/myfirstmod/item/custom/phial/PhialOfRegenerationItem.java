package net.avamaco.myfirstmod.item.custom.phial;

import net.avamaco.myfirstmod.item.custom.PotionUtilityItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class PhialOfRegenerationItem extends PotionUtilityItem {
    public PhialOfRegenerationItem(Item.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void UseOnEntity(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
    }
}
