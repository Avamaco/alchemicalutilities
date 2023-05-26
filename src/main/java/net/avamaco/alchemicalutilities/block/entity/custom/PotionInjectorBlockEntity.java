package net.avamaco.alchemicalutilities.block.entity.custom;

import net.avamaco.alchemicalutilities.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PotionInjectorBlockEntity extends BlockEntity {

    private Item heldPhial = null;
    private int charge = 0;
    private final int maxCharge = 128;

    public PotionInjectorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.POTION_INJECTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    private void setHeldPhial(Item heldPhial) {
        this.heldPhial = heldPhial;
    }

    public Item getHeldPhial() {
        return heldPhial;
    }

    private void addCharge(int amount) {
        charge += amount;
    }

    public void reduceCharge(int amount) {
        charge -= amount;
    }

    public boolean isFull() {
        return charge >= maxCharge;
    }

    public boolean isCharged() {
        return heldPhial != null && charge > 0;
    }

    public boolean canAdd(Item phial) {
        if (charge <= 0)
            return true;
        if (isFull())
            return false;
        return (heldPhial == phial);
    }

    public void addPhial(Item phial) {
        setHeldPhial(phial);
        addCharge(2);
    }

    public String getMessage() {
        return "My charge is: " + charge;
    }
}
