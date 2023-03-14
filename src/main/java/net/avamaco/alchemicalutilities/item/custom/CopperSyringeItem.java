package net.avamaco.alchemicalutilities.item.custom;

import net.avamaco.alchemicalutilities.util.ModTags;
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
        if (isCharged(itemstack)) {
            inject(pPlayer, itemstack);
            pPlayer.getCooldowns().addCooldown(this, 15);
            return InteractionResultHolder.consume(itemstack);
        }
        else if (checkForPhial(pPlayer)) {
            loadPhial(pPlayer, itemstack);
            pPlayer.getCooldowns().addCooldown(this, 60);
            return InteractionResultHolder.consume(itemstack);
        }
        else {
            return InteractionResultHolder.fail(itemstack);
        }
    }


    private void inject(Player pPlayer, ItemStack syringe) {
        if (getChargedPhial(syringe) == null) {
            clearChargedPhial(syringe);
            return;
        }
        ItemStack usedPhial = getChargedPhial(syringe);
        if (usedPhial.getItem() instanceof PotionUtilityItem) {
            ((PotionUtilityItem) usedPhial.getItem()).UseOnEntity(pPlayer);
        }
        clearChargedPhial(syringe);
    }

    public int getUseDuration() {
        return 40;
    }

//    public UseAnim getUseAnimation(ItemStack pStack) {
//        return UseAnim.BOW;
//    }

    public static boolean isCharged(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTag();
        return compoundtag != null && compoundtag.getBoolean("Charged");
    }

    public static void setCharged(ItemStack itemStack, boolean pIsCharged) {
        CompoundTag compoundtag = itemStack.getOrCreateTag();
        compoundtag.putBoolean("Charged", pIsCharged);
    }

    private static void addChargedPhial(ItemStack pStack, ItemStack pPhialStack) {
        setCharged(pStack, true);
        CompoundTag compoundtag = pStack.getOrCreateTag();
        CompoundTag ct = new CompoundTag();
        pPhialStack.save(ct);
        compoundtag.put("ChargedPhial", ct);
    }

    @Nullable
    private static ItemStack getChargedPhial(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        if (compoundtag.contains("ChargedPhial")) {
            CompoundTag phialtag = compoundtag.getCompound("ChargedPhial");
            return ItemStack.of(phialtag);
        }
        else {
            return null;
        }
    }

    private static void clearChargedPhial(ItemStack pStack) {
        setCharged(pStack, false);
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag != null && compoundtag.contains("ChargedPhial")) {
            compoundtag.remove("ChargedPhial");
        }
    }


    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (!(pEntityLiving instanceof Player)) {
            return;
        }
        Player pPlayer = (Player) pEntityLiving;
        pPlayer.sendMessage(new TextComponent("Skill Issue."), pPlayer.getUUID());
        int i = this.getUseDuration() - pTimeLeft;
        if (i >= this.getUseDuration() - 3 && !isCharged(pStack) && loadPhial(pPlayer, pStack)) {
            setCharged(pStack, true);
        }
    }

    private static boolean loadPhial(Player pShooter, ItemStack pStack) {
        if (!(checkForPhial(pShooter))) {
            return false;
        }
        ItemStack itemstack;
        ItemStack phialStack = pShooter.getInventory().getItem(getFirstPhial(pShooter));

        itemstack = phialStack.split(1);
        if (phialStack.isEmpty()) {
            (pShooter).getInventory().removeItem(phialStack);
        }

        addChargedPhial(pStack, itemstack);
        return true;
    }

    private static int getFirstPhial(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (!currentStack.isEmpty() && isPhial(currentStack)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean checkForPhial(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (!currentStack.isEmpty() && isPhial(currentStack)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if (getChargedPhial(pStack) == null) {
            pTooltipComponents.add(new TextComponent("Empty"));
        }
        else {
            String currentPhial = getChargedPhial(pStack).getItem().toString();
            pTooltipComponents.add(new TextComponent(currentPhial));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    private static boolean isPhial(ItemStack itemStack) {
        return itemStack.is(ModTags.Items.POTION_PHIALS);
    }

}
