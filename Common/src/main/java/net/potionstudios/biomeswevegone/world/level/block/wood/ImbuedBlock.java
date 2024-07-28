package net.potionstudios.biomeswevegone.world.level.block.wood;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class ImbuedBlock extends BuddingAmethystBlock {

    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

    public ImbuedBlock(Properties properties) {
        super(properties.strength(1.5F).sound(SoundType.WOOD).requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any().setValue(PERSISTENT, false));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(PERSISTENT);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PERSISTENT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(PERSISTENT, true);
    }
}
