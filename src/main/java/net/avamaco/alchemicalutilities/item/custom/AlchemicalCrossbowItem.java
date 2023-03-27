package net.avamaco.alchemicalutilities.item.custom;

import net.avamaco.alchemicalutilities.entity.ModEntityTypes;
import net.avamaco.alchemicalutilities.entity.custom.PhialGrenadeProjectile;
import net.avamaco.alchemicalutilities.entity.custom.PhialShotProjectile;
import net.avamaco.alchemicalutilities.util.InventoryUtil;
import net.avamaco.alchemicalutilities.util.PhialsUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AlchemicalCrossbowItem extends Item {
    public AlchemicalCrossbowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (PhialsUtil.isCharged(itemstack)) {
            shootPhial(pPlayer, pLevel, itemstack);
            pPlayer.getCooldowns().addCooldown(this, 15);
            return InteractionResultHolder.consume(itemstack);
        }
        else if (InventoryUtil.checkForPhial(pPlayer)) {
            loadPhial(pPlayer, itemstack);
            pPlayer.getCooldowns().addCooldown(this, 40);
            return InteractionResultHolder.consume(itemstack);
        }
        else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public void shootPhial(Player player, Level level, ItemStack itemStack) {
        ItemStack phialStack = PhialsUtil.getChargedPhial(itemStack);
        if (phialStack != null && phialStack.getItem() instanceof PotionPhialItem) {
            PhialShotProjectile shot = new PhialShotProjectile(ModEntityTypes.PHIAL_SHOT.get(), player, level);
            shot.setItem(phialStack);
            shot.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.6F, 1.0F);
            level.addFreshEntity(shot);
        }
        PhialsUtil.clearChargedPhial(itemStack);
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
