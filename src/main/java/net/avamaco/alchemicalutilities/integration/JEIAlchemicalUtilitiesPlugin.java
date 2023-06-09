package net.avamaco.alchemicalutilities.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.avamaco.alchemicalutilities.AlchemicalUtilities;
import net.avamaco.alchemicalutilities.recipe.AlchemicalStationRecipe;
import net.avamaco.alchemicalutilities.recipe.SynthesisStationRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIAlchemicalUtilitiesPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(AlchemicalUtilities.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AlchemicalStationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SynthesisStationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<AlchemicalStationRecipe> alchemicalStationRecipes = rm.getAllRecipesFor(AlchemicalStationRecipe.Type.INSTANCE);
        registration.addRecipes(new RecipeType<>(AlchemicalStationRecipeCategory.UID, AlchemicalStationRecipe.class), alchemicalStationRecipes);
        List<SynthesisStationRecipe> synthesisStationRecipes = rm.getAllRecipesFor(SynthesisStationRecipe.Type.INSTANCE);
        registration.addRecipes(new RecipeType<>(SynthesisStationRecipeCategory.UID, SynthesisStationRecipe.class), synthesisStationRecipes);
    }
}
