package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

public class BWGMushroomBlock extends MushroomBlock {

    private final TagKey<Block> groundTag;

    public BWGMushroomBlock(Properties properties, TagKey<Block> groundTag, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        super(properties, feature);
        this.groundTag = groundTag;
    }

    public BWGMushroomBlock(Properties properties, TagKey<Block> groundTag) {
        super(properties, null);
        this.groundTag = groundTag;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.is(this.groundTag) && super.mayPlaceOn(state, level, pos);
    }
}
