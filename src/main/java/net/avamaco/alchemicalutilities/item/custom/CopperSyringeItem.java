package net.avamaco.alchemicalutilities.item.custom;

import net.avamaco.alchemicalutilities.util.InventoryUtil;
import net.avamaco.alchemicalutilities.util.PhialsUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CopperSyringeItem extends Item {
    final private static int minimumChargeTime = 40;

    public CopperSyringeItem(Item.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (PhialsUtil.isCharged(itemstack)) {
            inject(pPlayer, itemstack);
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 0.3F, 2.5F);
            pPlayer.getCooldowns().addCooldown(this, 10);
            return InteractionResultHolder.consume(itemstack);
        }
        else if (InventoryUtil.checkForPhial(pPlayer)) {
            pPlayer.startUsingItem(pHand);
            CompoundTag compoundtag = itemstack.getOrCreateTag();
            compoundtag.putBoolean("Charging", true);
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

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 120;
    }

    public static int getChargeDuration() { return minimumChargeTime; }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (!(pLivingEntity instanceof Player)) {
            return;
        }
        Player pPlayer = (Player) pLivingEntity;
        int i = this.getUseDuration(pStack) - pTimeCharged;
        if (i >= minimumChargeTime) {
            loadPhial(pPlayer, pStack);
            pLevel.playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundEvents.CROSSBOW_LOADING_END, SoundSource.PLAYERS, 0.5F, 2.0F);
        }

        CompoundTag compoundtag = pStack.getOrCreateTag();
        compoundtag.putBoolean("Charging", false);
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
            pTooltipComponents.add(new TextComponent("Empty").withStyle(ChatFormatting.GRAY));
        }
        else {
            pTooltipComponents.add(PhialsUtil.getChargedPhial(pStack).getDisplayName());
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public static boolean isCharging(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTag();
        return compoundtag != null && compoundtag.contains("Charging") && compoundtag.getBoolean("Charging");
    }

    public int getColor(ItemStack stack, int layer) {
        ItemStack phial = PhialsUtil.getChargedPhial(stack);
        if (phial != null && phial.getItem() instanceof PotionPhialItem && layer == 1)
            return ((PotionPhialItem) phial.getItem()).getColor(1);
        else
            return -1;
    }

}
