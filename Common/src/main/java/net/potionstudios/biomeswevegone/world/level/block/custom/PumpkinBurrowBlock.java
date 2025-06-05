package net.potionstudios.biomeswevegone.world.level.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.potionstudios.biomeswevegone.world.level.block.entities.BWGBlockEntityType;
import net.potionstudios.biomeswevegone.world.level.block.entities.PumpkinBurrowBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PumpkinBurrowBlock extends BaseEntityBlock {
    public static final BooleanProperty OCCUPIED = BooleanProperty.create("occupied");
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final MapCodec<PumpkinBurrowBlock> CODEC = simpleCodec(PumpkinBurrowBlock::new);

    public PumpkinBurrowBlock(Properties properties) {
        super(properties.lightLevel(state -> state.getValue(OCCUPIED) ? 10 : 0));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OCCUPIED, false));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(OCCUPIED).add(FACING));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return BWGBlockEntityType.PUMPKIN_BURROW.get().create(pos, state);
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected @NotNull BlockState rotate(@NotNull BlockState state, @NotNull Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected @NotNull BlockState mirror(@NotNull BlockState state, @NotNull Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        if (!level.isClientSide() && player.isCreative()
                && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)
                && level.getBlockEntity(pos) instanceof PumpkinBurrowBlockEntity pumpkinBurrow) {
            if (pumpkinBurrow.getBlockState().getValue(OCCUPIED)) {
                ItemStack itemStack = new ItemStack(this);
                itemStack.applyComponents(pumpkinBurrow.collectComponents());
                itemStack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(OCCUPIED, true));
                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return createTickerHelper(level, blockEntityType, BWGBlockEntityType.PUMPKIN_BURROW.get());
    }

    public static <T extends BlockEntity> BlockEntityTicker<T> createTickerHelper(Level level, BlockEntityType<T> serverType, BlockEntityType<? extends PumpkinBurrowBlockEntity> clientType) {
        return level.isClientSide() ? null : createTickerHelper(serverType, clientType, PumpkinBurrowBlockEntity::serverTick);
    }
}
