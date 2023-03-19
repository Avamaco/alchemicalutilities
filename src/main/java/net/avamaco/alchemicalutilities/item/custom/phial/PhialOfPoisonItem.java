package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.AlchemicalUtilityItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class PhialOfPoisonItem extends AlchemicalUtilityItem {
    public PhialOfPoisonItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFF4F9632;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, LivingEntity user) {
        entity.addEffect(new MobEffectInstance(MobEffects.POISON, 440, 0), user);
    }
}
