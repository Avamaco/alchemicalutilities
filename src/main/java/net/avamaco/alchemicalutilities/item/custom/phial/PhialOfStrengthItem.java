package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class PhialOfStrengthItem extends PotionPhialItem {
    public PhialOfStrengthItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFF962524;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, LivingEntity user) {
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 900, 0), user);
    }
}
