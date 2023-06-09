package net.avamaco.alchemicalutilities.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.avamaco.alchemicalutilities.AlchemicalUtilities;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AlchemicalStationRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public AlchemicalStationRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (recipeItems.size() < 2)
            throw new RuntimeException("recipeItems size too small. Check matches() in ASRecipe class.");
        return recipeItems.get(0).test(pContainer.getItem(0)) &&
                recipeItems.get(1).test(pContainer.getItem(1));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AlchemicalStationRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "alchemical_crafting";
    }

    public static class Serializer implements RecipeSerializer<AlchemicalStationRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(AlchemicalUtilities.MOD_ID, "alchemical_crafting");

        @Override
        public AlchemicalStationRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            if (ingredients.size() < 2)throw new RuntimeException("Json file doesn't work. Check fromJson() method.");

            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new AlchemicalStationRecipe(pRecipeId, output, inputs);
        }

        @Nullable
        @Override
        public AlchemicalStationRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new AlchemicalStationRecipe(pRecipeId, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, AlchemicalStationRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());
            for (Ingredient ing : pRecipe.getIngredients()) {
                ing.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(), false);
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}
