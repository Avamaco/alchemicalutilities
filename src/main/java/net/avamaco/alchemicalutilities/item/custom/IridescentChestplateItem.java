package net.avamaco.alchemicalutilities.item.custom;

import net.avamaco.alchemicalutilities.item.custom.phial.PhialOfPropulsionItem;
import net.avamaco.alchemicalutilities.util.PhialsUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IridescentChestplateItem extends ArmorItem {

    private final int MAX_COOLDOWN = 200;

    public IridescentChestplateItem(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (player.getHealth() / player.getMaxHealth() <= 0.5) {
            long time = level.getGameTime();
            if (isReady(stack, time)) {
                inject(stack, player);
                resetCooldown(stack, time);
            }
        }
    }

    private void resetCooldown(ItemStack stack, long time) {
        CompoundTag compoundtag = stack.getOrCreateTag();
        compoundtag.putLong("TimeUsed", time);
    }

    private boolean isReady(ItemStack stack, long time) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag == null || !compoundTag.contains("TimeUsed")) {
            resetCooldown(stack, time);
            return false;
        }
        return (compoundTag.getLong("TimeUsed") + MAX_COOLDOWN < time);
    }

    private void inject(ItemStack stack, Player player) {
        ItemStack phialStack = PhialsUtil.getChargedPhial(stack);
        if (phialStack.getItem() instanceof PotionPhialItem phial) {
            phial.UseOnEntity(player, player);
            if (phial instanceof PhialOfPropulsionItem) player.hurt(DamageSource.OUT_OF_WORLD, 0.01F);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if (PhialsUtil.getChargedPhial(pStack) == null) {
            pTooltipComponents.add(new TextComponent("Empty").withStyle(ChatFormatting.GRAY));
        }
        else {
            pTooltipComponents.add(PhialsUtil.getChargedPhial(pStack).getDisplayName());
            if (pLevel != null) {
                int MAX_BARS = 10;
                int bars = getChargeBars(pStack, pLevel, MAX_BARS);
                String chargeBar = "Charge: [" + "|".repeat(Math.max(0, bars)) +
                        ".".repeat(Math.max(0, MAX_BARS - bars)) +
                        "]";
                ChatFormatting formatting = (bars == MAX_BARS) ? ChatFormatting.GREEN : ChatFormatting.WHITE;
                pTooltipComponents.add(new TextComponent(chargeBar).withStyle(formatting));
            }
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    private int getChargeBars(ItemStack stack, Level level, int maxBars) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag == null || !compoundTag.contains("TimeUsed")) return 0;
        long result = level.getGameTime() - compoundTag.getLong("TimeUsed");
        result *= maxBars;
        result /= MAX_COOLDOWN;
        return (int) Math.min(maxBars, result);
    }
}
