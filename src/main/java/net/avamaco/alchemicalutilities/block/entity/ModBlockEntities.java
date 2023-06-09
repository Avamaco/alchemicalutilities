package net.avamaco.alchemicalutilities.block.entity;

import net.avamaco.alchemicalutilities.AlchemicalUtilities;
import net.avamaco.alchemicalutilities.block.ModBlocks;
import net.avamaco.alchemicalutilities.block.entity.custom.AlchemicalStationBlockEntity;
import net.avamaco.alchemicalutilities.block.entity.custom.PotionInjectorBlockEntity;
import net.avamaco.alchemicalutilities.block.entity.custom.SynthesisStationBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, AlchemicalUtilities.MOD_ID);

    public static final RegistryObject<BlockEntityType<AlchemicalStationBlockEntity>> ALCHEMICAL_STATION_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("alchemical_station_block_entity", () ->
                    BlockEntityType.Builder.of(AlchemicalStationBlockEntity::new,
                            ModBlocks.ALCHEMICAL_STATION.get()).build(null));

    public static final RegistryObject<BlockEntityType<SynthesisStationBlockEntity>> SYNTHESIS_STATION_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("synthesis_station_block_entity", () ->
                    BlockEntityType.Builder.of(SynthesisStationBlockEntity::new,
                            ModBlocks.SYNTHESIS_STATION.get()).build(null));

    public static final RegistryObject<BlockEntityType<PotionInjectorBlockEntity>> POTION_INJECTOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("potion_injector_block_entity", () ->
                    BlockEntityType.Builder.of(PotionInjectorBlockEntity::new,
                            ModBlocks.POTION_INJECTOR.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
