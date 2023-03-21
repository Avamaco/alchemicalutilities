package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

public class PhialOfWeaknessItem extends PotionPhialItem {
    public PhialOfWeaknessItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFF494E49;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, LivingEntity user) {
        entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 900, 0), user);
    }

    @Override
    public void UseExplosion(Vec3 position, Entity user) {
        makeAreaOfEffectCloud(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0), user, position);
    }
}
