package net.avamaco.alchemicalutilities.entity;

import net.avamaco.alchemicalutilities.AlchemicalUtilities;
import net.avamaco.alchemicalutilities.entity.custom.PhialGrenadeProjectile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, AlchemicalUtilities.MOD_ID);

    public static final RegistryObject<EntityType<PhialGrenadeProjectile>> PHIAL_GRENADE =
            ENTITY_TYPES.register("phial_grenade_projectile",
                    () -> EntityType.Builder.<PhialGrenadeProjectile>of(PhialGrenadeProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .build(new ResourceLocation(AlchemicalUtilities.MOD_ID, "phial_grenade_projectile").toString()));
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
