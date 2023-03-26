package net.avamaco.alchemicalutilities.block.entity.custom;

import net.avamaco.alchemicalutilities.block.entity.ModBlockEntities;
import net.avamaco.alchemicalutilities.recipe.AlchemicalStationRecipe;
import net.avamaco.alchemicalutilities.recipe.SynthesisStationRecipe;
import net.avamaco.alchemicalutilities.screen.AlchemicalStationMenu;
import net.avamaco.alchemicalutilities.screen.SynthesisStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SynthesisStationBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 12;
    public SynthesisStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SYNTHESIS_STATION_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0: return SynthesisStationBlockEntity.this.progress;
                    case 1: return SynthesisStationBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0: SynthesisStationBlockEntity.this.progress = pValue; break;
                    case 1: SynthesisStationBlockEntity.this.maxProgress = pValue; break;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Synthesis Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SynthesisStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("synthesis_station_progress", progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("synthesis_station_progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, SynthesisStationBlockEntity pBlockEntity) {
        if(hasRecipe(pBlockEntity)) {
            pBlockEntity.progress++;
            setChanged(pLevel, pPos, pState);
            if (pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        }
        else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    // item crafting from recipes
    private static void craftItem(SynthesisStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<SynthesisStationRecipe> match = level.getRecipeManager()
                .getRecipeFor(SynthesisStationRecipe.Type.INSTANCE, inventory, level);

        if (match.isPresent()) {
            entity.itemHandler.extractItem(1, 1, false);
            entity.itemHandler.extractItem(2, 1, false);
            entity.itemHandler.extractItem(3, 1, false);
            entity.itemHandler.extractItem(4, 1, false);
            entity.itemHandler.setStackInSlot(5, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(5).getCount() + 1));
        }

        entity.resetProgress();
    }

    private static boolean hasRecipe(SynthesisStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<SynthesisStationRecipe> match = level.getRecipeManager()
                .getRecipeFor(SynthesisStationRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        if (inventory.getContainerSize() < 6)
            throw new RuntimeException("Inventory has too few slots!!!");
        return (inventory.getItem(5).getItem() == output.getItem() || inventory.getItem(5).isEmpty());
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        if (inventory.getContainerSize() < 6)
            throw new RuntimeException("Inventory has too few slots!!!");
        return inventory.getItem(5).getMaxStackSize() > inventory.getItem(5).getCount();
    }


}
