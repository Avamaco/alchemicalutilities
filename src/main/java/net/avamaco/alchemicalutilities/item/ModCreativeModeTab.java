package net.avamaco.alchemicalutilities.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab ALCHEMICAL_TAB = new CreativeModeTab("alchemical_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.GLASS_PHIAL.get());
        }
    };
}
