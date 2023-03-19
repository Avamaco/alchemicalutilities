package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.AlchemicalUtilityItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class PhialOfSwiftnessItem extends AlchemicalUtilityItem {
    public PhialOfSwiftnessItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFF7EB2CA;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, LivingEntity user) {
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 900, 0), user);
    }
}
