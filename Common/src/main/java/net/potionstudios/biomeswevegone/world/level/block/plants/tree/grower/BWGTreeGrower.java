package net.potionstudios.biomeswevegone.world.level.block.plants.tree.grower;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BWGTreeGrower extends AbstractTreeGrower {


    private final SimpleWeightedRandomList<ResourceKey<ConfiguredFeature<?, ?>>> keys;

    public BWGTreeGrower(SimpleWeightedRandomList<ResourceKey<ConfiguredFeature<?, ?>>> keys) {
        this.keys = keys;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean hasFlowers) {
        return this.keys.getRandomValue(random).orElse(null);
    }
}