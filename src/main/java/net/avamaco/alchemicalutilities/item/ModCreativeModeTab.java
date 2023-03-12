package net.avamaco.alchemicalutilities.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab MYFIRST_TAB = new CreativeModeTab("myfirsttab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.CITRINE.get());
        }
    };
}
