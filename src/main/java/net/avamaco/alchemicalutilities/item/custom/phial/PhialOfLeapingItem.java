package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.AlchemicalUtilityItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class PhialOfLeapingItem extends AlchemicalUtilityItem {
    public PhialOfLeapingItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFF23FC4D;
    }

    @Override
    public void UseOnEntity(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 900, 0));
    }
}
