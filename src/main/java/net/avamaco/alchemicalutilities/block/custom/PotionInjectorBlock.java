package net.avamaco.alchemicalutilities.block.custom;

import net.avamaco.alchemicalutilities.block.entity.custom.AlchemicalStationBlockEntity;
import net.avamaco.alchemicalutilities.block.entity.custom.PotionInjectorBlockEntity;
import net.avamaco.alchemicalutilities.item.custom.PotionPhialItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class PotionInjectorBlock extends BaseEntityBlock {
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    public static final DirectionProperty FACING = DirectionalBlock.FACING;

    public PotionInjectorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TRIGGERED, Boolean.valueOf(false)));
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, TRIGGERED);
    }

    // block entity stuff

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PotionInjectorBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof PotionInjectorBlockEntity) {
                ItemStack usedItem = pPlayer.getItemInHand(pHand);
                if (usedItem.getItem() instanceof PotionPhialItem && ((PotionInjectorBlockEntity) entity).canAdd(usedItem)) {
                    ((PotionInjectorBlockEntity) entity).addPhial(usedItem);
                    //usedItem.shrink(1);
                    return InteractionResult.sidedSuccess(pLevel.isClientSide());
                }
                else {
                    pPlayer.sendMessage(new TextComponent(((PotionInjectorBlockEntity) entity).getMessage()), pPlayer.getUUID());
                }
            }
            else {
                throw new IllegalStateException("Our block entity is missing!");
            }
        }
        return InteractionResult.FAIL;
    }

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        boolean flag = pLevel.hasNeighborSignal(pPos) || pLevel.hasNeighborSignal(pPos.above());
        boolean flag1 = pState.getValue(TRIGGERED);
        if (flag && !flag1) {
            //pLevel.scheduleTick(pPos, this, 4);
            this.injectPotion(pLevel, pPos);
            pLevel.setBlock(pPos, pState.setValue(TRIGGERED, Boolean.valueOf(true)), 4);
        } else if (!flag && flag1) {
            pLevel.setBlock(pPos, pState.setValue(TRIGGERED, Boolean.valueOf(false)), 4);
        }

    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        this.injectPotion(pLevel, pPos);
    }

    private void injectPotion(Level pLevel, BlockPos pPos) {
        AABB range = getAABB(pLevel, pPos);
        List<? extends LivingEntity> list = pLevel.getEntitiesOfClass(LivingEntity.class, range);
        if (!list.isEmpty()) {
            LivingEntity entity = list.get(pLevel.random.nextInt(list.size()));
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof PotionInjectorBlockEntity) {
                PotionInjectorBlockEntity injectorBlock = (PotionInjectorBlockEntity) blockEntity;
                if (injectorBlock.isCharged() && injectorBlock.getHeldPhial() instanceof PotionPhialItem) {
                    PotionPhialItem phial = (PotionPhialItem) injectorBlock.getHeldPhial();
                    entity.hurt(DamageSource.OUT_OF_WORLD, 0.01F);
                    phial.UseOnEntity(entity, entity);
                    injectorBlock.reduceCharge();
                }
            }
            else {
                throw new IllegalStateException("Our block entity is missing!");
            }
        }
    }

    private AABB getAABB(Level level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        Direction facing = Direction.UP;
        if (blockState.getOptionalValue(BlockStateProperties.FACING).isPresent())
            facing = blockState.getOptionalValue(BlockStateProperties.FACING).get();
        AABB box = new AABB(BlockPos.of(BlockPos.offset(pos.asLong(), facing)));
        return box;
    }
}
