package net.avamaco.alchemicalutilities.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class AlchemicalUtilityItem extends Item {

    protected int COLOR;
    public AlchemicalUtilityItem(Item.Properties pProperties) {
        super(pProperties);
        this.COLOR = -1;
    }

    public void UseOnEntity(LivingEntity entity) {}

    public int getColor(int layer) {
        return layer == 1 ? this.COLOR : -1;
    }
}
