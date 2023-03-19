package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.AlchemicalUtilityItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class PhialOfRegenerationItem extends AlchemicalUtilityItem {
    public PhialOfRegenerationItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFFD15EAE;
    }

    @Override
    public void UseOnEntity(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 440, 0));
    }
}
