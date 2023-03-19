package net.avamaco.alchemicalutilities.event.loot;

import net.avamaco.alchemicalutilities.AlchemicalUtilities;
import net.avamaco.alchemicalutilities.item.ModItems;
import net.avamaco.alchemicalutilities.item.custom.AlchemicalUtilityItem;
import net.avamaco.alchemicalutilities.item.custom.CopperSyringeItem;
import net.avamaco.alchemicalutilities.recipe.AlchemicalStationRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AlchemicalUtilities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, AlchemicalStationRecipe.Type.ID, AlchemicalStationRecipe.Type.INSTANCE);
    }

    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event){
        event.getItemColors().register((stack, tint) -> {
            if (stack.getItem() instanceof CopperSyringeItem) return ((CopperSyringeItem)stack.getItem()).getColor(stack, tint);
            else return -1;
            }, ModItems.COPPER_SYRINGE.get());

        event.getItemColors().register((stack, tint) -> {
            if (stack.getItem() instanceof AlchemicalUtilityItem) return ((AlchemicalUtilityItem)stack.getItem()).getColor(tint);
            else return -1;
            }, ModItems.PHIAL_OF_FIRE_RESISTANCE.get(), ModItems.PHIAL_OF_HARMING.get(), ModItems.PHIAL_OF_HEALING.get(),
                ModItems.PHIAL_OF_INVISIBILITY.get(), ModItems.PHIAL_OF_LEAPING.get(), ModItems.PHIAL_OF_NIGHT_VISION.get(),
                ModItems.PHIAL_OF_POISON.get(), ModItems.PHIAL_OF_REGENERATION.get(), ModItems.PHIAL_OF_SWIFTNESS.get(),
                ModItems.PHIAL_OF_SLOW_FALLING.get(), ModItems.PHIAL_OF_SLOWNESS.get(), ModItems.PHIAL_OF_STRENGTH.get(),
                ModItems.PHIAL_OF_WATER_BREATHING.get(), ModItems.PHIAL_OF_WEAKNESS.get());
    }
}
