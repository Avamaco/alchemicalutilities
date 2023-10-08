package net.avamaco.alchemicalutilities.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

public class PotionPhialItem extends Item {

    protected int COLOR;
    public PotionPhialItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = -1;
    }

    public void UseOnEntity(LivingEntity entity, Entity user) {}

    public void UseExplosion(Vec3 position, Entity source) {}

    public void UseExplosion(Vec3 position, Entity source, Entity owner) {
        this.UseExplosion(position, source);
    }

    public void UseOnBlock(Vec3 position, BlockPos blockPos, Direction direction, Entity source) {}

    public void UseOnEntityEnhanced(LivingEntity entity, Entity user) { this.UseOnEntity(entity, user);}

    public int getColor(int layer) {
        return layer == 1 ? this.COLOR : -1;
    }

    protected void makeAreaOfEffectCloud(MobEffectInstance effect, Entity source, Vec3 position) {
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(source.level, position.x, position.y, position.z);

        areaeffectcloud.setRadius(3.0F);
        areaeffectcloud.setRadiusOnUse(-0.5F);
        areaeffectcloud.setWaitTime(10);
        areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());
        areaeffectcloud.addEffect(effect);
        areaeffectcloud.setFixedColor(COLOR);

        source.level.addFreshEntity(areaeffectcloud);
    }

    protected void makeTinyAOECloud(MobEffectInstance effect, Entity source, Vec3 position) {
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(source.level, position.x, position.y, position.z);

        areaeffectcloud.setRadius(1.0F);
        areaeffectcloud.setRadiusOnUse(-0.3F);
        areaeffectcloud.setWaitTime(10);
        areaeffectcloud.setDuration(200);
        areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());
        areaeffectcloud.addEffect(effect);
        areaeffectcloud.setFixedColor(COLOR);

        source.level.addFreshEntity(areaeffectcloud);
    }
}
