package net.avamaco.alchemicalutilities.util;

import net.avamaco.alchemicalutilities.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class PhialsUtil {
    public static Item[] phials = {
            ModItems.PHIAL_OF_FIRE_RESISTANCE.get(), ModItems.PHIAL_OF_HARMING.get(), ModItems.PHIAL_OF_HEALING.get(),
            ModItems.PHIAL_OF_INVISIBILITY.get(), ModItems.PHIAL_OF_LEAPING.get(), ModItems.PHIAL_OF_NIGHT_VISION.get(),
            ModItems.PHIAL_OF_POISON.get(), ModItems.PHIAL_OF_REGENERATION.get(), ModItems.PHIAL_OF_SWIFTNESS.get(),
            ModItems.PHIAL_OF_SLOW_FALLING.get(), ModItems.PHIAL_OF_SLOWNESS.get(), ModItems.PHIAL_OF_STRENGTH.get(),
            ModItems.PHIAL_OF_WATER_BREATHING.get(), ModItems.PHIAL_OF_WEAKNESS.get(), ModItems.PHIAL_OF_FIRE.get(),
            ModItems.PHIAL_OF_WARPING.get(), ModItems.PHIAL_OF_EXPLOSION.get()};

    public static boolean isCharged(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTag();
        return compoundtag != null && compoundtag.contains("Charged") && compoundtag.getBoolean("Charged");
    }

    public static void setCharged(ItemStack itemStack, boolean pIsCharged) {
        CompoundTag compoundtag = itemStack.getOrCreateTag();
        compoundtag.putBoolean("Charged", pIsCharged);
    }


    @Nullable
    public static ItemStack getChargedPhial(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        if (compoundtag.contains("ChargedPhial")) {
            CompoundTag phialTag = compoundtag.getCompound("ChargedPhial");
            return ItemStack.of(phialTag);
        }
        else {
            return null;
        }
    }

    public static void addChargedPhial(ItemStack pStack, ItemStack pPhialStack) {
        setCharged(pStack, true);
        CompoundTag compoundtag = pStack.getOrCreateTag();
        CompoundTag ct = new CompoundTag();
        pPhialStack.save(ct);
        compoundtag.put("ChargedPhial", ct);
    }

    public static void clearChargedPhial(ItemStack pStack) {
        setCharged(pStack, false);
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag != null && compoundtag.contains("ChargedPhial")) {
            compoundtag.remove("ChargedPhial");
        }
    }
}
