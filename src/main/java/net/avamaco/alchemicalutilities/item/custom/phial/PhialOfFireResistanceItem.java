package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

public class PhialOfFireResistanceItem extends PotionPhialItem {
    public PhialOfFireResistanceItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFFE89D3B;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, LivingEntity user) {
        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 900, 0), user);
    }

    @Override
    public void UseExplosion(Vec3 position, Entity source) {
        makeAreaOfEffectCloud(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0), source, position);
    }
}
