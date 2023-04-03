package net.avamaco.alchemicalutilities.screen.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ModFuelSlot extends SlotItemHandler {
    public ModFuelSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return super.mayPlace(stack) && stack.getItem() == Items.BLAZE_POWDER;
    }
}
