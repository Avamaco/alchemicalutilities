package net.avamaco.alchemicalutilities.screen;

import net.avamaco.alchemicalutilities.AlchemicalUtilities;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, AlchemicalUtilities.MOD_ID);

    public static final RegistryObject<MenuType<AlchemicalStationMenu>> ALCHEMICAL_STATION_MENU =
            registerMenuType(AlchemicalStationMenu::new, "alchemical_station_menu");

    public static final RegistryObject<MenuType<SynthesisStationMenu>> SYNTHESIS_STATION_MENU =
            registerMenuType(SynthesisStationMenu::new, "synthesis_station_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
