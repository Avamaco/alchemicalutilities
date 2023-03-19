package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.AlchemicalUtilityItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class PhialOfFireResistanceItem extends AlchemicalUtilityItem {
    public PhialOfFireResistanceItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFFE89D3B;
    }

    @Override
    public void UseOnEntity(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 900, 0));
    }
}
