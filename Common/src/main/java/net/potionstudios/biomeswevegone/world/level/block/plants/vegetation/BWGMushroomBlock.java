package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BWGMushroomBlock extends MushroomBlock {

    private final TagKey<Block> groundTag;

    private final VoxelShape SHAPE;

    public BWGMushroomBlock(Properties properties, TagKey<Block> groundTag, @Nullable ResourceKey<ConfiguredFeature<?, ?>> feature, VoxelShape shape) {
        super(feature, properties);
        this.groundTag = groundTag;
	    SHAPE = shape;
    }

    public BWGMushroomBlock(Properties properties, TagKey<Block> groundTag, VoxelShape shape) {
        this(properties, groundTag, null, shape);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.is(this.groundTag) && super.mayPlaceOn(state, level, pos);
    }
}
