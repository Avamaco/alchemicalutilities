package net.avamaco.alchemicalutilities.item;

import net.avamaco.alchemicalutilities.AlchemicalUtilities;
import net.avamaco.alchemicalutilities.item.custom.AlchemicalCrossbowItem;
import net.avamaco.alchemicalutilities.item.custom.AlchemistSwordItem;
import net.avamaco.alchemicalutilities.item.custom.CopperSyringeItem;
import net.avamaco.alchemicalutilities.item.custom.PhialGrenadeItem;
import net.avamaco.alchemicalutilities.item.custom.phial.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AlchemicalUtilities.MOD_ID);

    public static final RegistryObject<Item> COPPER_SYRINGE = ITEMS.register("copper_syringe",
            () -> new CopperSyringeItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB).stacksTo(1)));

    public static final RegistryObject<Item> ALCHEMICAL_CROSSBOW = ITEMS.register("alchemical_crossbow",
            () -> new AlchemicalCrossbowItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB).stacksTo(1)));

    public static final RegistryObject<Item> ALCHEMIST_SWORD = ITEMS.register("alchemist_sword",
            () -> new AlchemistSwordItem(ModTiers.ALCHEMIST, 3, -2.4f,
                    new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));

    public static final RegistryObject<Item> PHIAL_GRENADE = ITEMS.register("phial_grenade",
            () -> new PhialGrenadeItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB).stacksTo(16)));

    public static final RegistryObject<Item> GLASS_PHIAL = ITEMS.register("glass_phial",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_HEALING = ITEMS.register("phial_of_healing",
            () -> new PotionEffectPhialItem(MobEffects.HEAL, 1, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_REGENERATION = ITEMS.register("phial_of_regeneration",
            () -> new PotionEffectPhialItem(MobEffects.REGENERATION, 440, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_SWIFTNESS = ITEMS.register("phial_of_swiftness",
            () -> new PotionEffectPhialItem(MobEffects.MOVEMENT_SPEED, 900, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_FIRE_RESISTANCE = ITEMS.register("phial_of_fire_resistance",
            () -> new PotionEffectPhialItem(MobEffects.FIRE_RESISTANCE, 900, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_NIGHT_VISION = ITEMS.register("phial_of_night_vision",
            () -> new PotionEffectPhialItem(MobEffects.NIGHT_VISION, 900, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_LEAPING = ITEMS.register("phial_of_leaping",
            () -> new PotionEffectPhialItem(MobEffects.JUMP, 900, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_INVISIBILITY = ITEMS.register("phial_of_invisibility",
            () -> new PotionEffectPhialItem(MobEffects.INVISIBILITY, 900, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_POISON = ITEMS.register("phial_of_poison",
            () -> new PotionEffectPhialItem(MobEffects.POISON, 440, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_HARMING = ITEMS.register("phial_of_harming",
            () -> new PotionEffectPhialItem(MobEffects.HARM, 1, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_SLOW_FALLING = ITEMS.register("phial_of_slow_falling",
            () -> new PotionEffectPhialItem(MobEffects.SLOW_FALLING, 900, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_SLOWNESS = ITEMS.register("phial_of_slowness",
            () -> new PotionEffectPhialItem(MobEffects.MOVEMENT_SLOWDOWN, 900, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_STRENGTH = ITEMS.register("phial_of_strength",
            () -> new PotionEffectPhialItem(MobEffects.DAMAGE_BOOST, 900, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_WATER_BREATHING = ITEMS.register("phial_of_water_breathing",
            () -> new PotionEffectPhialItem(MobEffects.WATER_BREATHING, 900, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_WEAKNESS = ITEMS.register("phial_of_weakness",
            () -> new PotionEffectPhialItem(MobEffects.WEAKNESS, 900, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_LEVITATION = ITEMS.register("phial_of_levitation",
            () -> new PotionEffectPhialItem(MobEffects.LEVITATION, 200, new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));

    public static final RegistryObject<Item> PHIAL_OF_FIRE = ITEMS.register("phial_of_fire",
            () -> new PhialOfFireItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_WARPING = ITEMS.register("phial_of_warping",
            () -> new PhialOfWarpingItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_EXPLOSION = ITEMS.register("phial_of_explosion",
            () -> new PhialOfExplosionItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_PROPULSION = ITEMS.register("phial_of_propulsion",
            () -> new PhialOfPropulsionItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_SMOKE = ITEMS.register("phial_of_smoke",
            () -> new PhialOfSmokeItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));
    public static final RegistryObject<Item> PHIAL_OF_LIGHT = ITEMS.register("phial_of_light",
            () -> new PhialOfLightItem(new Item.Properties().tab(ModCreativeModeTab.ALCHEMICAL_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
