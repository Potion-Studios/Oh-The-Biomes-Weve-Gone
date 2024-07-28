//package net.potionstudios.biomeswevegone.world.level.levelgen.customterrain;
//
//import com.google.common.collect.ImmutableList;
//import corgitaco.corgilib.math.blendingfunction.BlendingFunction;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.core.Holder;
//import net.minecraft.util.Mth;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.level.ChunkPos;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.chunk.ChunkAccess;
//import net.minecraft.world.level.levelgen.Heightmap;
//import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
//import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
//import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
//import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
//import net.potionstudios.biomeswevegone.world.level.levelgen.util.BlendUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Function;
//
//public class TropicalIslandExtension {
//
//
//    public static void runTropicalIslandExtension(Function<BlockPos, Holder<Biome>> biomeGetter, ChunkAccess chunk, long seed) {
//        ChunkPos pos = chunk.getPos();
//
//        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
//
//        ImprovedNoise islandTerrainNoise = new ImprovedNoise(new XoroshiroRandomSource(seed));
//        ImprovedNoise islandHeightNoiseSampler = new ImprovedNoise(new XoroshiroRandomSource(seed + 2948548));
//        ImprovedNoise islandDepthNoiseSampler = new ImprovedNoise(new XoroshiroRandomSource(seed + 2484585));
//
//
//        List<BlockState> sandStates = ImmutableList.of(
//                BWGBlocks.PURPLE_SAND_SET.getSand().defaultBlockState(),
//                BWGBlocks.PINK_SAND_SET.getSand().defaultBlockState(),
//                BWGBlocks.BLUE_SAND_SET.getSand().defaultBlockState(),
//                BWGBlocks.WHITE_SAND_SET.getSand().defaultBlockState(),
//                BWGBlocks.BLACK_SAND_SET.getSand().defaultBlockState()
//        );
//
//        for (int x = 0; x < 16; x++) {
//            for (int z = 0; z < 16; z++) {
//                int worldX = pos.getBlockX(x);
//                int worldZ = pos.getBlockZ(z);
//                int landHeight = (chunk.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, worldX, worldZ));
//                int oceanHeight = (chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, worldX, worldZ));
//                if (chunk.getMinBuildHeight() >= landHeight) {
//                    continue;
//                }
//
//                mutable.set(worldX, landHeight, worldZ);
//
//                Holder<Biome> currentBiome = biomeGetter.apply(mutable);
//                if (!currentBiome.is(BWGBiomes.TROPICAL_ISLAND)) {
//                    continue;
//                }
//
//
//                double islandHeightNoise = (islandTerrainNoise.noise(mutable.getX() * 0.01, 0, mutable.getZ() * 0.01) + 1) * 0.5;
//
//                double scaledIslandHeight = BlendingFunction.EaseInOutCirc.INSTANCE.apply(islandHeightNoise, -80, ((islandHeightNoiseSampler.noise(mutable.getX() * 0.005, 0, mutable.getZ() * 0.005) + 1) * 0.5) * 100);
//
//                if (scaledIslandHeight < 0) {
//                    continue;
//                }
//
//                int blendRadius = Math.min(16, (int) Math.ceil(scaledIslandHeight));
//                double blendDelta = BlendUtil.blendBiomeEdge(currentBiome, biomeGetter, mutable, blendRadius, 1);
//
//                int topY = (int) Math.ceil((landHeight + scaledIslandHeight) * blendDelta);
//
//                for (int y = landHeight; y <= topY; y++) {
//                    mutable.setY(y);
//                    BlockState state = Blocks.STONE.defaultBlockState();
//
//                    if (y == topY) {
//                        if (chunk.getBlockState(mutable.move(Direction.UP)).isAir()) {
//                            state = Blocks.GRASS_BLOCK.defaultBlockState();
//                        }
//                        mutable.move(Direction.DOWN);
//
//                    } else if (topY - 3 <= y) {
//                        state = Blocks.DIRT.defaultBlockState();
//                    }
//
//                    if (y > oceanHeight - 5 && topY > oceanHeight - 5 && topY < oceanHeight + 5 && y < oceanHeight + 5) {
//                        state =  sandStates.get((int) Mth.clampedLerp(0, sandStates.size() - 1, (islandDepthNoiseSampler.noise(mutable.getX() * 0.1F, 0, mutable.getZ() * 0.1F) + 1) * 0.5));
//                    }
//
//                    chunk.setBlockState(mutable, state, false);
//                }
//            }
//        }
//    }
//}
