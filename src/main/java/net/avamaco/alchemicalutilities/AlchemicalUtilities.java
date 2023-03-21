package net.avamaco.alchemicalutilities;

import com.mojang.logging.LogUtils;
import net.avamaco.alchemicalutilities.block.ModBlocks;
import net.avamaco.alchemicalutilities.block.entity.ModBlockEntities;
import net.avamaco.alchemicalutilities.entity.ModEntityTypes;
import net.avamaco.alchemicalutilities.item.ModItems;
import net.avamaco.alchemicalutilities.item.custom.CopperSyringeItem;
import net.avamaco.alchemicalutilities.recipe.ModRecipes;
import net.avamaco.alchemicalutilities.screen.AlchemicalStationScreen;
import net.avamaco.alchemicalutilities.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AlchemicalUtilities.MOD_ID)
public class AlchemicalUtilities
{
    public static final String MOD_ID = "alchemicalutilities";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public AlchemicalUtilities()
    {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);

        ModEntityTypes.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ALCHEMICAL_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ALCHEMICAL_STATION.get(), RenderType.translucent());

        MenuScreens.register(ModMenuTypes.ALCHEMICAL_STATION_MENU.get(), AlchemicalStationScreen::new);

        event.enqueueWork(() ->
                {
                    ItemProperties.register(ModItems.COPPER_SYRINGE.get(),
                            new ResourceLocation(AlchemicalUtilities.MOD_ID, "charged"),
                            (stack, level, living, id) -> {
                        return CopperSyringeItem.isCharged(stack) ? 1.0F : 0.0F;
                            });
                }
        );
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
