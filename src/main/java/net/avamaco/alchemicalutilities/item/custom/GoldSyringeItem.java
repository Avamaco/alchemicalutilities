package net.avamaco.alchemicalutilities.item.custom;

import net.avamaco.alchemicalutilities.util.InventoryUtil;
import net.avamaco.alchemicalutilities.util.PhialsUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GoldSyringeItem extends CopperSyringeItem {
    public GoldSyringeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected boolean loadPhial(Player pShooter, ItemStack pStack) {
        if (!(InventoryUtil.checkForPhial(pShooter))) {
            return false;
        }
        ItemStack itemstack;
        ItemStack phialStack = pShooter.getInventory().getItem(getFirstTwoPhials(pShooter));

        itemstack = phialStack.split(2);
        if (phialStack.isEmpty()) {
            (pShooter).getInventory().removeItem(phialStack);
        }

        PhialsUtil.addChargedPhial(pStack, itemstack);
        return true;
    }

    private static int getFirstTwoPhials(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (!currentStack.isEmpty() && InventoryUtil.isPhial(currentStack) && currentStack.getCount() >= 2) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void inject(Player pPlayer, ItemStack syringe) {
        if (PhialsUtil.getChargedPhial(syringe) == null) {
            PhialsUtil.clearChargedPhial(syringe);
            return;
        }
        ItemStack usedPhial = PhialsUtil.getChargedPhial(syringe);
        if (usedPhial.getItem() instanceof PotionPhialItem) {
            ((PotionPhialItem) usedPhial.getItem()).UseOnEntityEnhanced(pPlayer, pPlayer);
        }
        PhialsUtil.clearChargedPhial(syringe);
    }
}
