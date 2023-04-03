package net.avamaco.alchemicalutilities.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.avamaco.alchemicalutilities.AlchemicalUtilities;
import net.avamaco.alchemicalutilities.block.ModBlocks;
import net.avamaco.alchemicalutilities.item.ModItems;
import net.avamaco.alchemicalutilities.recipe.AlchemicalStationRecipe;
import net.avamaco.alchemicalutilities.recipe.SynthesisStationRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class SynthesisStationRecipeCategory implements IRecipeCategory<SynthesisStationRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(AlchemicalUtilities.MOD_ID, "alchemical_synthesis");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(AlchemicalUtilities.MOD_ID, "textures/gui/synthesis_station_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public SynthesisStationRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 4, 4, 167, 78);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SYNTHESIS_STATION.get()));
    }

    @Override
    public Component getTitle() {
        return new TextComponent("Synthesis Station");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends SynthesisStationRecipe> getRecipeClass() {
        return SynthesisStationRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SynthesisStationRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.CATALYST, 22, 42).addIngredients(Ingredient.of(Items.BLAZE_POWDER));

        builder.addSlot(RecipeIngredientRole.INPUT, 22, 16).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 58, 16).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 58, 42).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 94, 29).addIngredients(recipe.getIngredients().get(3));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 130, 29).addItemStack(recipe.getResultItem());
    }
}
