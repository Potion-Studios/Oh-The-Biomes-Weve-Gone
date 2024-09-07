package net.potionstudios.biomeswevegone.world.level.block.plants.tree.grower;


import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BWGMegaTreeGrower extends TreeGrower {

    private final SimpleWeightedRandomList<ResourceKey<ConfiguredFeature<?, ?>>> keys;
    private final SimpleWeightedRandomList<ResourceKey<ConfiguredFeature<?, ?>>> megaKeys;

    public BWGMegaTreeGrower(String name, SimpleWeightedRandomList<ResourceKey<ConfiguredFeature<?, ?>>> keys, SimpleWeightedRandomList<ResourceKey<ConfiguredFeature<?, ?>>> megaKeys) {
        super(name, 0, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        this.keys = keys;
        this.megaKeys = megaKeys;
    }

    @Override
    public boolean growTree(@NotNull ServerLevel level, @NotNull ChunkGenerator chunkGenerator, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull RandomSource random) {
        return super.growTree(level, chunkGenerator, pos, state, random);
    }

    @Nullable
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(@NotNull RandomSource random) {
        return this.megaKeys.getRandomValue(random).orElse(null);
    }

    @Nullable
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean hasFlowers) {
        return this.keys.getRandomValue(random).orElse(null);
    }
}