package net.avamaco.alchemicalutilities.recipe;

import net.avamaco.alchemicalutilities.AlchemicalUtilities;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AlchemicalUtilities.MOD_ID);

    public static final RegistryObject<RecipeSerializer<AlchemicalStationRecipe>> ALCHEMICAL_CRAFTING_SERIALIZER =
            SERIALIZERS.register("alchemical_crafting", () -> AlchemicalStationRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SynthesisStationRecipe>> ALCHEMICAL_SYNTHESIS_SERIALIZER =
            SERIALIZERS.register("alchemical_synthesis", () -> SynthesisStationRecipe.Serializer.INSTANCE);


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
