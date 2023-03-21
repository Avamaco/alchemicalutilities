package net.avamaco.alchemicalutilities.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.phys.Vec3;

public class PotionPhialItem extends Item {

    protected int COLOR;
    public PotionPhialItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = -1;
    }

    public void UseOnEntity(LivingEntity entity, LivingEntity user) {}

    public void UseExplosion(Vec3 position, Entity user) {}

    public int getColor(int layer) {
        return layer == 1 ? this.COLOR : -1;
    }

    protected void makeAreaOfEffectCloud(MobEffectInstance effect, Entity source, Vec3 position) {
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(source.level, position.x, position.y, position.z);
        if (source instanceof LivingEntity) {
            areaeffectcloud.setOwner((LivingEntity)source);
        }

        areaeffectcloud.setRadius(3.0F);
        areaeffectcloud.setRadiusOnUse(-0.5F);
        areaeffectcloud.setWaitTime(10);
        areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());
        areaeffectcloud.addEffect(effect);
        areaeffectcloud.setFixedColor(COLOR);

        source.level.addFreshEntity(areaeffectcloud);
    }
}
