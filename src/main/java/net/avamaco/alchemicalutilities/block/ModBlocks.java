package net.avamaco.alchemicalutilities.block;

import net.avamaco.alchemicalutilities.AlchemicalUtilities;
import net.avamaco.alchemicalutilities.block.custom.AlchemicalStationBlock;
import net.avamaco.alchemicalutilities.block.custom.SynthesisStationBlock;
import net.avamaco.alchemicalutilities.block.dispenser.BehaviourGrenade;
import net.avamaco.alchemicalutilities.item.ModCreativeModeTab;
import net.avamaco.alchemicalutilities.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AlchemicalUtilities.MOD_ID);

    public static final RegistryObject<Block> ALCHEMICAL_GLASS = registerBlock("alchemical_glass",
            () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()), ModCreativeModeTab.ALCHEMICAL_TAB);

    public static final RegistryObject<Block> ALCHEMICAL_STATION = registerBlock("alchemical_station",
            () -> new AlchemicalStationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()), ModCreativeModeTab.ALCHEMICAL_TAB);

    public static final RegistryObject<Block> SYNTHESIS_STATION = registerBlock("synthesis_station",
            () -> new SynthesisStationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()), ModCreativeModeTab.ALCHEMICAL_TAB);


    private static <T extends  Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void addDispenserBehaviours() {
        DispenserBlock.registerBehavior(ModItems.PHIAL_GRENADE.get(), new BehaviourGrenade());
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
