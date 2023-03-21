package net.avamaco.alchemicalutilities.item;

import net.avamaco.alchemicalutilities.AlchemicalUtilities;
import net.avamaco.alchemicalutilities.item.custom.AlchemistSwordItem;
import net.avamaco.alchemicalutilities.item.custom.CopperSyringeItem;
import net.avamaco.alchemicalutilities.item.custom.PhialGrenadeItem;
import net.avamaco.alchemicalutilities.item.custom.phial.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AlchemicalUtilities.MOD_ID);

    public static final RegistryObject<Item> CITRINE = ITEMS.register("citrine",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));

    public static final RegistryObject<Item> COPPER_SYRINGE = ITEMS.register("copper_syringe",
            () -> new CopperSyringeItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB).stacksTo(1)));

    public static final RegistryObject<Item> ALCHEMIST_SWORD = ITEMS.register("alchemist_sword",
            () -> new AlchemistSwordItem(ModTiers.ALCHEMIST, 4, 1.6f,
                    new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));

    public static final RegistryObject<Item> PHIAL_GRENADE = ITEMS.register("phial_grenade",
            () -> new PhialGrenadeItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));

    public static final RegistryObject<Item> GLASS_PHIAL = ITEMS.register("glass_phial",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_HEALING = ITEMS.register("phial_of_healing",
            () -> new PhialOfHealingItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_REGENERATION = ITEMS.register("phial_of_regeneration",
            () -> new PhialOfRegenerationItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_SWIFTNESS = ITEMS.register("phial_of_swiftness",
            () -> new PhialOfSwiftnessItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_FIRE_RESISTANCE = ITEMS.register("phial_of_fire_resistance",
            () -> new PhialOfFireResistanceItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_NIGHT_VISION = ITEMS.register("phial_of_night_vision",
            () -> new PhialOfNightVisionItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_LEAPING = ITEMS.register("phial_of_leaping",
            () -> new PhialOfLeapingItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_INVISIBILITY = ITEMS.register("phial_of_invisibility",
            () -> new PhialOfInvisibilityItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_POISON = ITEMS.register("phial_of_poison",
            () -> new PhialOfPoisonItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_HARMING = ITEMS.register("phial_of_harming",
            () -> new PhialOfHarmingItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_SLOW_FALLING = ITEMS.register("phial_of_slow_falling",
            () -> new PhialOfSlowFallingItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_SLOWNESS = ITEMS.register("phial_of_slowness",
            () -> new PhialOfSlownessItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_STRENGTH = ITEMS.register("phial_of_strength",
            () -> new PhialOfStrengthItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_WATER_BREATHING = ITEMS.register("phial_of_water_breathing",
            () -> new PhialOfWaterBreathingItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_WEAKNESS = ITEMS.register("phial_of_weakness",
            () -> new PhialOfWeaknessItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));

    public static final RegistryObject<Item> PHIAL_OF_FIRE = ITEMS.register("phial_of_fire",
            () -> new PhialOfFireItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
