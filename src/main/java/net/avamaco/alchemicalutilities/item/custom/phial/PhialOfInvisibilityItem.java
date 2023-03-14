package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionUtilityItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class PhialOfInvisibilityItem extends PotionUtilityItem {
    public PhialOfInvisibilityItem(Item.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void UseOnEntity(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 900, 0));
    }
}
