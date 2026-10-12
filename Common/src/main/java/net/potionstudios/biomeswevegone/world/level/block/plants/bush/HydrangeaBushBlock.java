package net.potionstudios.biomeswevegone.world.level.block.plants.bush;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.potionstudios.biomeswevegone.tags.BWGBlockTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HydrangeaBushBlock extends BWGPlacementBushBlock implements BonemealableBlock {
    public static final EnumProperty<HydrangeaBushSection> SECTION = EnumProperty.create("section", HydrangeaBushSection.class);
    private static final MapCodec<HydrangeaBushBlock> CODEC = simpleCodec(HydrangeaBushBlock::new);

    public HydrangeaBushBlock(Properties properties) {
        super(properties, Block.box(0, 0, 0, 16, 16, 16), BWGBlockTags.HYDRANGEA_BUSH_PLACEABLE);
        this.registerDefaultState(this.getStateDefinition().any().setValue(SECTION, HydrangeaBushSection.SINGLE));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockState clicked = level.getBlockState(context.getClickedPos().below());
        if (clicked.is(this) && !clicked.getValue(SECTION).isTaller()) {
            addSegment(level, context.getClickedPos());
            return null;
        }
        return super.getStateForPlacement(context);
    }

    @Override
    protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        HydrangeaBushSection currentSection = state.getValue(SECTION);
        if (currentSection == HydrangeaBushSection.TALLER_MIDDLE || currentSection == HydrangeaBushSection.TALLER_TOP || currentSection == HydrangeaBushSection.TALL_TOP)
            return level.getBlockState(pos.below()).is(this);
        return super.canSurvive(state, level, pos);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return !state.getValue(SECTION).isTaller();
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        //level.setBlockAndUpdate(pos, BWGBlocks.HYDRANGEA_HEDGE.get().defaultBlockState());
    }

    @Override
    protected @NotNull MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(SECTION));
    }

    /**
     * Finds the lowest block of the hydrangea bush stack.
     */
    private BlockPos getBottomPos(LevelReader level, BlockPos pos) {
        BlockPos current = pos;
        while (level.getBlockState(current.below()).is(this)) {
            current = current.below();
        }
        return current;
    }

    /**
     * Determines the current height of the hydrangea stack (1, 2, or 3).
     */
    private int getStackHeight(LevelReader level, BlockPos bottomPos) {
        if (!level.getBlockState(bottomPos).is(this)) return 0;
        if (level.getBlockState(bottomPos.above()).is(this)) {
            if (level.getBlockState(bottomPos.above(2)).is(this)) {
                return 3;
            }
            return 2;
        }
        return 1;
    }

    /**
     * Attempts to add a segment to the hydrangea bush stack.
     * Handles transitioning from 1 -> 2 blocks, or 2 -> 3 blocks.
     *
     * @return true if the bush successfully grew, false if it's at max height or blocked.
     */
    public boolean addSegment(Level level, BlockPos pos) {
        BlockPos bottomPos = getBottomPos(level, pos);
        int currentHeight = getStackHeight(level, bottomPos);

        if (currentHeight == 1) {
            BlockPos topPos = bottomPos.above();
            if (level.getBlockState(topPos).isAir() || level.getBlockState(topPos).canBeReplaced()) {
                level.setBlock(bottomPos, this.defaultBlockState().setValue(SECTION, HydrangeaBushSection.TALL_BOTTOM), 3);
                level.setBlock(topPos, this.defaultBlockState().setValue(SECTION, HydrangeaBushSection.TALL_TOP), 3);
                return true;
            }
        } else if (currentHeight == 2) {
            BlockPos middlePos = bottomPos.above();
            BlockPos topPos = bottomPos.above(2);
            if (level.getBlockState(topPos).isAir() || level.getBlockState(topPos).canBeReplaced()) {
                level.setBlock(bottomPos, this.defaultBlockState().setValue(SECTION, HydrangeaBushSection.TALLER_BOTTOM), 3);
                level.setBlock(middlePos, this.defaultBlockState().setValue(SECTION, HydrangeaBushSection.TALLER_MIDDLE), 3);
                level.setBlock(topPos, this.defaultBlockState().setValue(SECTION, HydrangeaBushSection.TALLER_TOP), 3);
                return true;
            }
        }
        return false;
    }
}

