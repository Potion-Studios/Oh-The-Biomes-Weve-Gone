package net.potionstudios.biomeswevegone.world.level.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.entities.BWGBlockEntityType;
import net.potionstudios.biomeswevegone.world.level.block.entities.FrostedAmberBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FrostedAmberBlock extends BaseEntityBlock {
    public static final BooleanProperty CONVERTING = BooleanProperty.create("converting");
    public static final MapCodec<FrostedAmberBlock> CODEC = simpleCodec(FrostedAmberBlock::new);
    public FrostedAmberBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CONVERTING, false));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(CONVERTING)) {
            level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
        }
        super.animateTick(state, level, pos, random);
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof FrostedAmberBlockEntity frostedAmberBlockEntity) {
                if (frostedAmberBlockEntity.getEmptyBottles() > 0)
                    Containers.dropItemStack(
                            level, pos.getX(), pos.getY(), pos.getZ(),
                            new ItemStack(Items.GLASS_BOTTLE, frostedAmberBlockEntity.getEmptyBottles())
                    );
                if (frostedAmberBlockEntity.getFullBottles() > 0)
                    Containers.dropItemStack(
                            level, pos.getX(), pos.getY(), pos.getZ(),
                            new ItemStack(BWGItems.FROST_AMBER_BOTTLE.get(), frostedAmberBlockEntity.getFullBottles())
                    );
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            if (level.getBlockEntity(pos) instanceof FrostedAmberBlockEntity blockEntity) {
                if (player.isShiftKeyDown()) {
                    int fullBottles = blockEntity.getFullBottles();
                    if (fullBottles > 0) {
                        blockEntity.removeFullBottles(fullBottles);
                        player.addItem(new ItemStack(BWGItems.FROST_AMBER_BOTTLE.get(), fullBottles));
                        return InteractionResult.SUCCESS;
                    }
                } else {
                    int emptyBottles = blockEntity.getEmptyBottles();
                    if (emptyBottles > 0) {
                        level.setBlockAndUpdate(pos, state.setValue(CONVERTING, false));
                        player.addItem(new ItemStack(Items.GLASS_BOTTLE, emptyBottles));
                        blockEntity.removeEmptyBottles(emptyBottles);
                        return InteractionResult.SUCCESS;
                    }
                }

            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (!level.isClientSide() && player.getItemInHand(hand).is(Items.GLASS_BOTTLE)) {
            if (level.getBlockEntity(pos) instanceof FrostedAmberBlockEntity blockEntity) {
                if (blockEntity.isFull())
                    return ItemInteractionResult.FAIL;
                else {
                    player.getItemInHand(hand).shrink(1);
                    blockEntity.addBottle();
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(CONVERTING));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return BWGBlockEntityType.FROSTED_AMBER.get().create(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : createTickerHelper(blockEntityType, BWGBlockEntityType.FROSTED_AMBER.get(), FrostedAmberBlockEntity::serverTick);
    }
}
