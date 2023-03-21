package net.avamaco.alchemicalutilities.item.custom;

import net.avamaco.alchemicalutilities.util.InventoryUtil;
import net.avamaco.alchemicalutilities.util.PhialsUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CopperSyringeItem extends Item {

    public CopperSyringeItem(Item.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (PhialsUtil.isCharged(itemstack)) {
            inject(pPlayer, itemstack);
            pPlayer.getCooldowns().addCooldown(this, 15);
            return InteractionResultHolder.consume(itemstack);
        }
        else if (InventoryUtil.checkForPhial(pPlayer)) {
            loadPhial(pPlayer, itemstack);
            pPlayer.getCooldowns().addCooldown(this, 60);
            return InteractionResultHolder.consume(itemstack);
        }
        else {
            return InteractionResultHolder.fail(itemstack);
        }
    }


    private void inject(Player pPlayer, ItemStack syringe) {
        if (PhialsUtil.getChargedPhial(syringe) == null) {
            PhialsUtil.clearChargedPhial(syringe);
            return;
        }
        ItemStack usedPhial = PhialsUtil.getChargedPhial(syringe);
        if (usedPhial.getItem() instanceof PotionPhialItem) {
            ((PotionPhialItem) usedPhial.getItem()).UseOnEntity(pPlayer, pPlayer);
        }
        PhialsUtil.clearChargedPhial(syringe);
    }

    public int getUseDuration() {
        return 40;
    }

//    public UseAnim getUseAnimation(ItemStack pStack) {
//        return UseAnim.BOW;
//    }

    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (!(pEntityLiving instanceof Player)) {
            return;
        }
        Player pPlayer = (Player) pEntityLiving;
        pPlayer.sendMessage(new TextComponent("Skill Issue."), pPlayer.getUUID());
        int i = this.getUseDuration() - pTimeLeft;
        if (i >= this.getUseDuration() - 3 && !PhialsUtil.isCharged(pStack) && loadPhial(pPlayer, pStack)) {
            PhialsUtil.setCharged(pStack, true);
        }
    }

    private static boolean loadPhial(Player pShooter, ItemStack pStack) {
        if (!(InventoryUtil.checkForPhial(pShooter))) {
            return false;
        }
        ItemStack itemstack;
        ItemStack phialStack = pShooter.getInventory().getItem(InventoryUtil.getFirstPhial(pShooter));

        itemstack = phialStack.split(1);
        if (phialStack.isEmpty()) {
            (pShooter).getInventory().removeItem(phialStack);
        }

        PhialsUtil.addChargedPhial(pStack, itemstack);
        return true;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if (PhialsUtil.getChargedPhial(pStack) == null) {
            pTooltipComponents.add(new TextComponent("Empty"));
        }
        else {
            String currentPhial = PhialsUtil.getChargedPhial(pStack).getItem().toString();
            pTooltipComponents.add(new TextComponent(currentPhial));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public int getColor(ItemStack stack, int layer) {
        ItemStack phial = PhialsUtil.getChargedPhial(stack);
        if (phial != null && phial.getItem() instanceof PotionPhialItem && layer == 1)
            return ((PotionPhialItem) phial.getItem()).getColor(1);
        else
            return -1;
    }

}
