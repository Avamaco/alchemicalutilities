package net.avamaco.alchemicalutilities.item.custom;

import net.minecraft.world.item.Item;

public class MutagenItem extends Item {

    protected int COLOR;

    public MutagenItem(int color, Properties pProperties) {
        super(pProperties);
        COLOR = color;
    }

    public int getColor(int layer) {
        return layer == 1 ? this.COLOR : -1;
    }
}
