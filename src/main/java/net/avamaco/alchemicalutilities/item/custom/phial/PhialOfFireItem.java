package net.avamaco.alchemicalutilities.item.custom.phial;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class PhialOfFireItem extends PotionPhialItem {
    public PhialOfFireItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = 0xFFFF5733;
    }

    @Override
    public void UseOnEntity(LivingEntity entity, LivingEntity user) {
        entity.setSecondsOnFire(15);
    }
}
