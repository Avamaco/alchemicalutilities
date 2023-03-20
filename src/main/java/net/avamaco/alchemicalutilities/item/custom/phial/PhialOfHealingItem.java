package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class PhialOfHealingItem extends PotionPhialItem {


    public PhialOfHealingItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFFFC2524;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, LivingEntity user) {
        entity.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 0), user);
    }
}
