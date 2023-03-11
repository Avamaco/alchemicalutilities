package net.avamaco.myfirstmod.item;

import net.avamaco.myfirstmod.MyFirstMod;
import net.avamaco.myfirstmod.item.custom.CopperSyringeItem;
import net.avamaco.myfirstmod.item.custom.PotionUtilityItem;
import net.avamaco.myfirstmod.item.custom.phial.PhialOfRegenerationItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MyFirstMod.MOD_ID);

    public static final RegistryObject<Item> CITRINE = ITEMS.register("citrine",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MYFIRST_TAB)));

    public static final RegistryObject<Item> COPPER_SYRINGE = ITEMS.register("copper_syringe",
            () -> new CopperSyringeItem(new Item.Properties().tab(ModCreativeModeTab.MYFIRST_TAB).stacksTo(1)));

    public static final RegistryObject<Item> GLASS_PHIAL = ITEMS.register("glass_phial",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MYFIRST_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_HEALING = ITEMS.register("phial_of_healing",
            () -> new PotionUtilityItem(new Item.Properties().tab(ModCreativeModeTab.MYFIRST_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_REGENERATION = ITEMS.register("phial_of_regeneration",
            () -> new PhialOfRegenerationItem(new Item.Properties().tab(ModCreativeModeTab.MYFIRST_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
