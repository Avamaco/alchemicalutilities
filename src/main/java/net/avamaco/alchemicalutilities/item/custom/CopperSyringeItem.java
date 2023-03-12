package net.avamaco.alchemicalutilities.item.custom;

import com.google.common.collect.Lists;
import net.avamaco.alchemicalutilities.util.ModTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.List;

public class CopperSyringeItem extends Item {

    public CopperSyringeItem(Item.Properties pProperties) {
        super(pProperties);
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (isCharged(itemstack)) {
            inject(pPlayer, itemstack);
            pPlayer.sendMessage(new TextComponent("Injected."), pPlayer.getUUID());
            return InteractionResultHolder.consume(itemstack);
        }
        else if (checkForPhial(pPlayer)) {
            pPlayer.startUsingItem(pHand);
            pPlayer.sendMessage(new TextComponent("startUsingItem called."), pPlayer.getUUID());
            return InteractionResultHolder.consume(itemstack);
        }
        else {
            pPlayer.sendMessage(new TextComponent("Something failed."), pPlayer.getUUID());
            return InteractionResultHolder.fail(itemstack);
        }
    }

    private void inject(Player pPlayer, ItemStack syringe) {
        ItemStack usedPhial = getChargedPhials(syringe).get(0);
        if (usedPhial.getItem() instanceof PotionUtilityItem) {
            ((PotionUtilityItem) usedPhial.getItem()).UseOnEntity(pPlayer);
        }
        clearChargedPhials(syringe);
        setCharged(syringe, false);
    }

    public int getUseDuration() {
        return 40;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    public static boolean isCharged(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTag();
        return compoundtag != null && compoundtag.getBoolean("Charged");
    }

    public static void setCharged(ItemStack itemStack, boolean pIsCharged) {
        CompoundTag compoundtag = itemStack.getOrCreateTag();
        compoundtag.putBoolean("Charged", pIsCharged);
    }

    private static void addChargedPhial(ItemStack pStack, ItemStack pAmmoStack) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        ListTag listtag;
        if (compoundtag.contains("ChargedPhials", 9)) {
            listtag = compoundtag.getList("ChargedPhials", 10);
        } else {
            listtag = new ListTag();
        }

        CompoundTag compoundtag1 = new CompoundTag();
        pAmmoStack.save(compoundtag1);
        listtag.add(compoundtag1);
        compoundtag.put("ChargedPhials", listtag);
    }

    private static List<ItemStack> getChargedPhials(ItemStack pStack) {
        List<ItemStack> list = Lists.newArrayList();
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag != null && compoundtag.contains("ChargedPhials", 9)) {
            ListTag listtag = compoundtag.getList("ChargedPhials", 10);
            for(int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag1 = listtag.getCompound(i);
                list.add(ItemStack.of(compoundtag1));
            }
        }

        return list;
    }

    private static void clearChargedPhials(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag != null) {
            ListTag listtag = compoundtag.getList("ChargedPhials", 9);
            listtag.clear();
            compoundtag.put("ChargedPhials", listtag);
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
        if (checkForPhial(pShooter)) {
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

    private static boolean isPhial(ItemStack itemStack) {
        return itemStack.is(ModTags.Items.POTION_PHIALS);
    }
}
