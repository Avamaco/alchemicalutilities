package net.avamaco.alchemicalutilities.item.custom;

import net.avamaco.alchemicalutilities.entity.ModEntityTypes;
import net.avamaco.alchemicalutilities.entity.custom.PhialGrenadeProjectile;
import net.avamaco.alchemicalutilities.util.PhialsUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PhialGrenadeItem extends Item {
    public PhialGrenadeItem(Properties pProperties) {
        super(pProperties);
    }

    @NotNull
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            PhialGrenadeProjectile grenade = new PhialGrenadeProjectile(ModEntityTypes.PHIAL_GRENADE.get(), pPlayer, pLevel);
            grenade.setItem(itemstack);
            grenade.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 0.8F, 1.0F);
            pLevel.addFreshEntity(grenade);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if (PhialsUtil.getChargedPhial(pStack) == null) {
            pTooltipComponents.add(new TextComponent("Empty").withStyle(ChatFormatting.GRAY));
        }
        else {
            pTooltipComponents.add(new TextComponent("Charged phial: " + PhialsUtil.getChargedPhial(pStack).getHoverName().getString())
                    .withStyle(ChatFormatting.YELLOW));
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
