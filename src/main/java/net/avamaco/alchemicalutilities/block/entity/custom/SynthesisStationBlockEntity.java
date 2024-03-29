package net.avamaco.alchemicalutilities.block.entity.custom;

import net.avamaco.alchemicalutilities.block.entity.ModBlockEntities;
import net.avamaco.alchemicalutilities.item.ModItems;
import net.avamaco.alchemicalutilities.recipe.AlchemicalStationRecipe;
import net.avamaco.alchemicalutilities.recipe.SynthesisStationRecipe;
import net.avamaco.alchemicalutilities.screen.AlchemicalStationMenu;
import net.avamaco.alchemicalutilities.screen.SynthesisStationMenu;
import net.avamaco.alchemicalutilities.util.InventoryUtil;
import net.avamaco.alchemicalutilities.util.PhialsUtil;
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

    IItemHandler topBottomItemHandler = new IItemHandler() {
        @Override
        public int getSlots() {
            return 6;
        }

        @NotNull
        @Override
        public ItemStack getStackInSlot(int slot) {
            return itemHandler.getStackInSlot(slot);
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (slot == 0 && stack.getItem() == Items.BLAZE_POWDER) return itemHandler.insertItem(slot, stack, simulate);
            if (slot == 4 && (stack.getItem() == ModItems.GLASS_PHIAL.get() || InventoryUtil.isPhial(stack))) return itemHandler.insertItem(slot, stack, simulate);
            return stack;
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot != 5) return ItemStack.EMPTY;
            return itemHandler.extractItem(slot, amount, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return itemHandler.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot == 0 && stack.getItem() == Items.BLAZE_POWDER) return itemHandler.isItemValid(slot, stack);
            if (slot == 4 && (stack.getItem() == ModItems.GLASS_PHIAL.get() || InventoryUtil.isPhial(stack))) return itemHandler.isItemValid(slot, stack);
            return false;
        }
    };

    IItemHandler sideItemHandler = new IItemHandler() {
        @Override
        public int getSlots() {
            return 6;
        }

        @NotNull
        @Override
        public ItemStack getStackInSlot(int slot) {
            return itemHandler.getStackInSlot(slot);
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (slot != 1 && slot != 2 && slot != 3) return stack;
            return itemHandler.insertItem(slot, stack, simulate);
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot != 5) return ItemStack.EMPTY;
            return itemHandler.extractItem(slot, amount, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return itemHandler.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot != 1 && slot != 2 && slot != 3) return false;
            return itemHandler.isItemValid(slot, stack);
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> topBottomLazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> sideLazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 60;
    private int fuel = 0;
    private int maxFuel = 20;

    public SynthesisStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SYNTHESIS_STATION_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0: return SynthesisStationBlockEntity.this.progress;
                    case 1: return SynthesisStationBlockEntity.this.maxProgress;
                    case 2: return SynthesisStationBlockEntity.this.fuel;
                    case 3: return SynthesisStationBlockEntity.this.maxFuel;
                    default: return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0: SynthesisStationBlockEntity.this.progress = pValue; break;
                    case 1: SynthesisStationBlockEntity.this.maxProgress = pValue; break;
                    case 2: SynthesisStationBlockEntity.this.fuel = pValue; break;
                    case 3: SynthesisStationBlockEntity.this.maxFuel = pValue; break;
                }
            }

            @Override
            public int getCount() {
                return 4;
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
            if (side == Direction.UP || side == Direction.DOWN)
                return topBottomLazyItemHandler.cast();
            else if (side != null)
                return sideLazyItemHandler.cast();

            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        topBottomLazyItemHandler = LazyOptional.of(() -> topBottomItemHandler);
        sideLazyItemHandler = LazyOptional.of(() -> sideItemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        topBottomLazyItemHandler.invalidate();
        sideLazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("synthesis_station_progress", progress);
        pTag.putInt("synthesis_station_fuel", fuel);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("synthesis_station_progress");
        fuel = pTag.getInt("synthesis_station_fuel");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, SynthesisStationBlockEntity pBlockEntity) {
        ItemStack fuelStack = pBlockEntity.itemHandler.getStackInSlot(0);
        if (pBlockEntity.fuel <= 0 && fuelStack.is(Items.BLAZE_POWDER)) {
            pBlockEntity.fuel = pBlockEntity.maxFuel;
            fuelStack.shrink(1);
            setChanged(pLevel, pPos, pState);
        }
        if(hasRecipe(pBlockEntity) && pBlockEntity.fuel > 0) {
            pBlockEntity.progress++;
            setChanged(pLevel, pPos, pState);
            if (pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
                pBlockEntity.fuel--;
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

    // Comparator power is equal to the minimum of ingredient numbers. Doesn't include fuel and phials.
    public int getComparatorPower() {
        int result = itemHandler.getStackInSlot(1).getCount();
        result = Math.min(result, itemHandler.getStackInSlot(2).getCount());
        result = Math.min(result, itemHandler.getStackInSlot(3).getCount());
        // clamp value between 0 and 15
        result = Math.max(0, Math.min(result, 15));
        return result;
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
