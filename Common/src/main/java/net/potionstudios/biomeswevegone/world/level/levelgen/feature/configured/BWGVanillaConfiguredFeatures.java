package net.potionstudios.biomeswevegone.world.level.levelgen.feature.configured;

import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;

import java.util.List;

public class BWGVanillaConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_DEFAULT = ConfiguredFeaturesUtil.createConfiguredFeature(
            "vanilla/flower_default",
            Feature.FLOWER,
            () -> VegetationFeatures.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(BWGBlocks.FLOWER_PATCH.get().defaultBlockState(), 2)
                    .add(BWGBlocks.CLOVER_PATCH.get().defaultBlockState(), 2)
                    .add(BWGBlocks.WHITE_ANEMONE.getBlockState(), 1)
                    .add(BWGBlocks.PINK_ANEMONE.getBlockState(), 1)
                    .add(BWGBlocks.ORANGE_DAISY.getBlockState(), 1)
                    .add(BWGBlocks.HORSEWEED.getBlockState(), 1)
                    .add(BWGBlocks.ROSE.getBlockState(), 1)
                    .build()), 64));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_PLAINS = ConfiguredFeaturesUtil.createConfiguredFeature(
            "vanilla/flower_plains",
            Feature.FLOWER,
            () -> new RandomPatchConfiguration(
                    64, 6, 2,
                    PlacementUtils.onlyWhenEmpty(
                            Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(
                                    new NoiseThresholdProvider(
                                            2345L,
                                            new NormalNoise.NoiseParameters(0, 1.0),
                                            0.005F,-0.8F,
                                            0.33333334F,
                                            Blocks.DANDELION.defaultBlockState(),
                                            List.of(
                                                    BWGBlocks.GREEN_TULIP.getBlockState(),
                                                    BWGBlocks.CYAN_TULIP.getBlockState(),
                                                    BWGBlocks.MAGENTA_TULIP.getBlockState(),
                                                    BWGBlocks.PURPLE_TULIP.getBlockState(),
                                                    BWGBlocks.YELLOW_TULIP.getBlockState()
                                            ),
                                            List.of(
                                                    BWGBlocks.ROSE.getBlockState(),
                                                    BWGBlocks.ORANGE_DAISY.getBlockState(),
                                                    BWGBlocks.FLOWER_PATCH.get().defaultBlockState(),
                                                    BWGBlocks.CLOVER_PATCH.get().defaultBlockState()
                                            )
                                    )
                            )
                    )
            )
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> FOREST_FLOWERS = ConfiguredFeaturesUtil.createConfiguredFeature(
            "vanilla/forest_flowers",
            Feature.SIMPLE_RANDOM_SELECTOR,
            () -> new SimpleRandomFeatureConfiguration(
                    HolderSet.direct(
                            PlacementUtils.inlinePlaced(
                                    Feature.RANDOM_PATCH,
                                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BWGBlocks.FLOWER_PATCH.get())))
                            ),
                            PlacementUtils.inlinePlaced(
                                    Feature.RANDOM_PATCH,
                                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BWGBlocks.BLUE_ROSE_BUSH.get())))
                            ),
                            PlacementUtils.inlinePlaced(
                                    Feature.RANDOM_PATCH,
                                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BWGBlocks.CLOVER_PATCH.get())))
                            ),
                            PlacementUtils.inlinePlaced(
                                    Feature.RANDOM_PATCH,
                                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BWGBlocks.BLUEBERRY_BUSH.get())))
                            ))
            )
    );

    public static void init() {
        BiomesWeveGone.LOGGER.info("Creating and Registering Vanilla Configured Features");
    }
}
