package net.avamaco.alchemicalutilities.util;

import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class InventoryUtil {
    public static int getFirstPhial(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (!currentStack.isEmpty() && isPhial(currentStack)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean checkForPhial(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (!currentStack.isEmpty() && isPhial(currentStack)) {
                return true;
            }
        }
        return false;
    }

    /*private static boolean isPhial(ItemStack itemStack) {
        return itemStack.is(ModTags.Items.POTION_PHIALS);
    }*/
    private static boolean isPhial(ItemStack itemStack) {
        return itemStack.getItem() instanceof PotionPhialItem;
    }
}
