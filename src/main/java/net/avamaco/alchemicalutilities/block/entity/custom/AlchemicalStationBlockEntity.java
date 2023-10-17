package net.avamaco.alchemicalutilities.block.entity.custom;

import net.avamaco.alchemicalutilities.block.entity.ModBlockEntities;
import net.avamaco.alchemicalutilities.item.ModItems;
import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.avamaco.alchemicalutilities.recipe.AlchemicalStationRecipe;
import net.avamaco.alchemicalutilities.screen.AlchemicalStationMenu;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AlchemicalStationBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    IItemHandler topBottomItemHandler = new IItemHandler() {
        @Override
        public int getSlots() {
            return 4;
        }

        @NotNull
        @Override
        public ItemStack getStackInSlot(int slot) {
            return itemHandler.getStackInSlot(slot);
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (slot != 1) return stack;
            return itemHandler.insertItem(slot, stack, simulate);
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot < 2) return ItemStack.EMPTY;
            return itemHandler.extractItem(slot, amount, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return itemHandler.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot != 1) return false;
            return itemHandler.isItemValid(slot, stack);
        }
    };

    IItemHandler sideItemHandler = new IItemHandler() {
        @Override
        public int getSlots() {
            return 4;
        }

        @NotNull
        @Override
        public ItemStack getStackInSlot(int slot) {
            return itemHandler.getStackInSlot(slot);
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (slot != 0) return stack;
            return itemHandler.insertItem(slot, stack, simulate);
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot < 2) return ItemStack.EMPTY;
            return itemHandler.extractItem(slot, amount, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return itemHandler.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot != 0) return false;
            return itemHandler.isItemValid(slot, stack);
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> topBottomLazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> sideLazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;

    public AlchemicalStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALCHEMICAL_STATION_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0: return AlchemicalStationBlockEntity.this.progress;
                    case 1: return AlchemicalStationBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0: AlchemicalStationBlockEntity.this.progress = pValue; break;
                    case 1: AlchemicalStationBlockEntity.this.maxProgress = pValue; break;
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
        return new TextComponent("Alchemical Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new AlchemicalStationMenu(pContainerId, pPlayerInventory, this, this.data);
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
        pTag.putInt("alchemical_station_progress", progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("alchemical_station_progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
             inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, AlchemicalStationBlockEntity pBlockEntity) {
        if(hasRecipe(pBlockEntity)) {
            pBlockEntity.progress++;
            setChanged(pLevel, pPos, pState);
            if (pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        }
        else if (hasGrenadeRecipe(pBlockEntity)) {
            pBlockEntity.progress++;
            setChanged(pLevel, pPos, pState);
            if (pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftGrenade(pBlockEntity);
            }
        }
        else if (hasInfusionRecipe(pBlockEntity)) {
            pBlockEntity.progress++;
            setChanged(pLevel, pPos, pState);
            if (pBlockEntity.progress > pBlockEntity.maxProgress) {
                infuseArmor(pBlockEntity);
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
    private static void craftItem(AlchemicalStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<AlchemicalStationRecipe> match = level.getRecipeManager()
                .getRecipeFor(AlchemicalStationRecipe.Type.INSTANCE, inventory, level);

        if (match.isPresent()) {
            entity.itemHandler.extractItem(0, 1, false);
            entity.itemHandler.extractItem(1, 1, false);
            entity.itemHandler.setStackInSlot(2, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(2).getCount() + 1));
            entity.itemHandler.setStackInSlot(3, new ItemStack(Items.GLASS_BOTTLE,
                    entity.itemHandler.getStackInSlot(3).getCount() + 1));
        }

        entity.resetProgress();
    }

    private static boolean hasRecipe(AlchemicalStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<AlchemicalStationRecipe> match = level.getRecipeManager()
                .getRecipeFor(AlchemicalStationRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        if (inventory.getContainerSize() < 4)
            throw new RuntimeException("Inventory has too few slots!!!");
        return (inventory.getItem(2).getItem() == output.getItem() || inventory.getItem(2).isEmpty())
                && (inventory.getItem(3).getItem() == Items.GLASS_BOTTLE || inventory.getItem(3).isEmpty());
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        if (inventory.getContainerSize() < 4)
            throw new RuntimeException("Inventory has too few slots!!!");
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount()
                && inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }

    // grenade crafting (kinda hardcoded)

    private static void craftGrenade(AlchemicalStationBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        if (hasGrenadeRecipe(entity)) {
            ItemStack result = new ItemStack(ModItems.PHIAL_GRENADE.get(), entity.itemHandler.getStackInSlot(2).getCount() + 1);
            ItemStack toCharge = inventory.getItem(1).copy();
            toCharge.setCount(1);
            PhialsUtil.addChargedPhial(result, toCharge);
            entity.itemHandler.extractItem(0, 1, false);
            entity.itemHandler.extractItem(1, 1, false);
            entity.itemHandler.setStackInSlot(2, result);
            entity.itemHandler.setStackInSlot(3, new ItemStack(ModItems.GLASS_PHIAL.get(),
                    entity.itemHandler.getStackInSlot(3).getCount() + 1));
        }

        entity.resetProgress();
    }

    private static boolean hasGrenadeRecipe(AlchemicalStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        return inventory.getItem(0).getItem() == ModItems.PHIAL_GRENADE.get()
                && inventory.getItem(1).getItem() instanceof PotionPhialItem
                && canInsertAmountIntoOutputSlot(inventory)
                && canInsertGrenadeIntoOutputSlot(inventory);
    }

    private static boolean canInsertGrenadeIntoOutputSlot(SimpleContainer inventory) {
        if (inventory.getContainerSize() < 4)
            throw new RuntimeException("Inventory has too few slos!!!");
        return ((inventory.getItem(2).getItem() == ModItems.PHIAL_GRENADE.get()
                    && PhialsUtil.getChargedPhial(inventory.getItem(2)).getItem() == inventory.getItem(1).getItem())
                    || inventory.getItem(2).isEmpty())
                && (inventory.getItem(3).getItem() == ModItems.GLASS_PHIAL.get() || inventory.getItem(3).isEmpty());
    }

    // armor infusion (also hardcoded)

    private static void infuseArmor(AlchemicalStationBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        if (hasInfusionRecipe(entity)) {
            ItemStack result = entity.itemHandler.getStackInSlot(0).copy();
            ItemStack toCharge = inventory.getItem(1).copy();
            toCharge.setCount(1);
            PhialsUtil.addChargedPhial(result, toCharge);
            CompoundTag compoundtag = result.getOrCreateTag();
            if (entity.hasLevel()) compoundtag.putLong("TimeUsed", entity.getLevel().getGameTime());
            entity.itemHandler.extractItem(0, 1, false);
            entity.itemHandler.extractItem(1, 1, false);
            entity.itemHandler.setStackInSlot(2, result);
            entity.itemHandler.setStackInSlot(3, new ItemStack(ModItems.GLASS_PHIAL.get(),
                    entity.itemHandler.getStackInSlot(3).getCount() + 1));
        }

        entity.resetProgress();
    }

    private static boolean hasInfusionRecipe(AlchemicalStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        return inventory.getItem(0).getItem() == ModItems.IRIDESCENT_CHESTPLATE.get()
                && inventory.getItem(1).getItem() instanceof PotionPhialItem
                && canInsertAmountIntoOutputSlot(inventory)
                && inventory.getItem(2).isEmpty();
    }
}
