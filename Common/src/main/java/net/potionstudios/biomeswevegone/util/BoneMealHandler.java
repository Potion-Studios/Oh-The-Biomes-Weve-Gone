package net.potionstudios.biomeswevegone.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.potionstudios.biomeswevegone.tags.BWGBiomeTags;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.placed.BWGOverworldVegationPlacedFeatures;

import java.util.List;
import java.util.Optional;

public final class BoneMealHandler {

    public static boolean bwgBoneMealEventHandler(ServerLevel level, BlockPos blockPos, BlockState state) {
        if (state.is(Blocks.GRASS_BLOCK))
            if (level.getBiome(blockPos).is(BWGBiomes.PRAIRIE))
                return grassBoneMealHandler(level, blockPos.above(), BWGBlocks.PRAIRIE_GRASS.get(), BWGOverworldVegationPlacedFeatures.PRAIRIE_GRASS_BONEMEAL, false, Blocks.GRASS_BLOCK);
            else if (level.getBiome(blockPos).is(BWGBiomeTags.OVERWORLD))
                return grassBoneMealHandler(level, blockPos.above(), Blocks.SHORT_GRASS, VegetationPlacements.GRASS_BONEMEAL, true, Blocks.GRASS_BLOCK);
        return false;
    }

    public static boolean grassBoneMealHandler(ServerLevel level, BlockPos blockPos, Block grass, ResourceKey<PlacedFeature> placedFeatureResourceKey, boolean randomizeFlower, Block grassBlock) {
        BlockState blockState = grass.defaultBlockState();
        Optional<Holder.Reference<PlacedFeature>> optional = level.registryAccess()
                .lookupOrThrow(Registries.PLACED_FEATURE)
                .get(placedFeatureResourceKey);

        label48:
        for(int i = 0; i < 128; ++i) {
            BlockPos testPos = blockPos;
            RandomSource random = level.getRandom();
            for (int j = 0; j < i / 16; ++j) {
                testPos = testPos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!level.getBlockState(testPos.below()).is(grassBlock) || level.getBlockState(testPos).isCollisionShapeFullBlock(level, testPos))
                    continue label48;
            }

            BlockState blockState2 = level.getBlockState(testPos);
            if (blockState2.is(blockState.getBlock()) && random.nextInt(10) == 0)
                ((BonemealableBlock) blockState.getBlock()).performBonemeal(level, random, testPos, blockState2);

            if (blockState2.isAir() && !level.isOutsideBuildHeight(testPos)) {
                if (random.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = level.getBiome(testPos).value().getGenerationSettings().getBoneMealFeatures();
                    if (list.isEmpty()) continue;

                    ConfiguredFeature<?, ?> holder = randomizeFlower ? getRandElement(list, random) : list.getFirst();
                    holder.place(level, level.getChunkSource().getGenerator(), random, testPos);
                } else if (optional.isPresent()){
	                optional.get().value().place(level, level.getChunkSource().getGenerator(), random, testPos);
                }

            }
        }
        return true;
    }

    private static ConfiguredFeature<?, ?> getRandElement(List<ConfiguredFeature<?, ?>> list, RandomSource random) {
        return Util.getRandom(list, random);
    }
}
