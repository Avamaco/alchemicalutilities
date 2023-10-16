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
        //if (level.getGameTime() % 100 == 0) player.sendMessage(new TextComponent("ticking"), player.getUUID());
        if (player.getHealth() / player.getMaxHealth() <= 0.5) {
            long time = level.getGameTime();
            //if (time % 100 == 0) player.sendMessage(new TextComponent("low hp"), player.getUUID());
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
        player.sendMessage(new TextComponent("dziala"), player.getUUID());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if (PhialsUtil.getChargedPhial(pStack) == null) {
            pTooltipComponents.add(new TextComponent("Empty").withStyle(ChatFormatting.GRAY));
        }
        else {
            //String currentPhial = PhialsUtil.getChargedPhial(pStack).getItem().toString();
            //pTooltipComponents.add(new TextComponent(currentPhial));
            pTooltipComponents.add(PhialsUtil.getChargedPhial(pStack).getDisplayName());
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
