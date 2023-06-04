package net.avamaco.alchemicalutilities.block.entity.custom;

import net.avamaco.alchemicalutilities.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

public class PotionInjectorBlockEntity extends BlockEntity {

    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

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
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    public PotionInjectorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.POTION_INJECTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
    }



    public String getMessage() {
        return "My charge is: " + itemHandler.getStackInSlot(0).getCount();
    }

    public Item getHeldPhial() {
        return itemHandler.getStackInSlot(0).getItem();
    }

    public int getCharge() {
        return itemHandler.getStackInSlot(0).getCount();
    }

    public boolean isCharged() {
        return !itemHandler.getStackInSlot(0).isEmpty();
    }

    public void reduceCharge() {
        itemHandler.extractItem(0, 1, false);
    }

    public boolean canAdd(ItemStack usedItem) {
        if (itemHandler.getStackInSlot(0).isEmpty()) return true;
        return itemHandler.getStackInSlot(0).sameItem(usedItem) && itemHandler.getStackInSlot(0).getCount() < 64;
    }

    public void addPhial(ItemStack usedItem) {
        ItemStack added = usedItem.split(1);
        itemHandler.setStackInSlot(0, new ItemStack(added.getItem(), itemHandler.getStackInSlot(0).getCount() + 1));
    }
}
