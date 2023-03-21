package net.avamaco.alchemicalutilities.event.loot;

import net.avamaco.alchemicalutilities.AlchemicalUtilities;
import net.avamaco.alchemicalutilities.entity.ModEntityTypes;
import net.avamaco.alchemicalutilities.item.ModItems;
import net.avamaco.alchemicalutilities.item.custom.PhialGrenadeItem;
import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.avamaco.alchemicalutilities.item.custom.CopperSyringeItem;
import net.avamaco.alchemicalutilities.recipe.AlchemicalStationRecipe;
import net.avamaco.alchemicalutilities.util.PhialsUtil;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
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
            if (stack.getItem() instanceof PotionPhialItem) return ((PotionPhialItem)stack.getItem()).getColor(tint);
            else return -1;
            }, PhialsUtil.phials);

        event.getItemColors().register((stack, tint) -> {
            if (stack.getItem() instanceof PhialGrenadeItem) return ((PhialGrenadeItem)stack.getItem()).getColor(stack, tint);
            else return -1;
        }, ModItems.PHIAL_GRENADE.get());
    }

    @SubscribeEvent
    static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.PHIAL_GRENADE.get(), ThrownItemRenderer::new);
    }
}
