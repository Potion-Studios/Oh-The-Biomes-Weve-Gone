package net.potionstudios.biomeswevegone.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import corgitaco.corgilib.math.blendingfunction.BlendingFunction;
import corgitaco.corgilib.world.level.RandomTickScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.world.level.material.Fluids;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;

public class CragLakeFeature extends Feature<NoneFeatureConfiguration> {

    public static final Direction[] DIRECTIONS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};


    public CragLakeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {


        ImprovedNoise noiseSampler = new ImprovedNoise(new XoroshiroRandomSource(context.level().getLevel().getSeed()));
        ImprovedNoise lakeDepthSampler = new ImprovedNoise(new XoroshiroRandomSource(context.level().getLevel().getSeed() + 2439854945L));

        WorldGenLevel level = context.level();
        ChunkAccess chunk = level.getChunk(context.origin());


        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        ChunkPos pos = chunk.getPos();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = pos.getBlockX(x);
                int worldZ = pos.getBlockZ(z);


                int heightmap = chunk.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z);

                double noise = getNoise(noiseSampler, worldX, worldZ);
                double noiseWaterThreshold = 0.35;
                if (noise < noiseWaterThreshold) {

                    mutable.set(worldX, heightmap, worldZ);

                    if (chunk.getBlockState(mutable).canOcclude()) {

                        boolean mayPlace = true;
                        for (Direction direction : DIRECTIONS) {
                            mutable.set(worldX, heightmap, worldZ).move(direction);
                            BlockState offsetState = level.getBlockState(mutable);
                            if (!(offsetState.canOcclude() || offsetState.getFluidState().is(Fluids.WATER))) {
                                mayPlace = false;
                                break;
                            }
                        }



                        boolean hasWaterfall = false;
                        // Handle Waterfalls
                        if (!mayPlace) {
                            for (Direction direction : DIRECTIONS) {
                                mutable.set(worldX, heightmap, worldZ).move(direction);

                                int offsetHeight = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, mutable.getX(), mutable.getZ());

                                if (offsetHeight < heightmap) {
                                    double offsetNoise = getNoise(noiseSampler, mutable.getX(), mutable.getZ());
                                    if (offsetNoise < noiseWaterThreshold) {
                                        mayPlace = true;
                                        hasWaterfall = true;
                                    }
                                }
                            }
                        }
                        for (Direction direction : DIRECTIONS) {

                            mutable.set(worldX, 0, worldZ).move(direction);
                            int offsetHeight = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, mutable.getX(), mutable.getZ());

                            mutable.setY(offsetHeight);
                            if (!level.getBiome(mutable).is(BWGBiomes.CRAG_GARDENS)) {
                                mayPlace = false;
                                break;
                            }

                        }

                        if (mayPlace) {
                            if (!hasWaterfall) {
                                double depthDelta = noise / (noiseWaterThreshold * 0.5);

                                double clampedDelta = Mth.clamp(depthDelta, 0, 1);


                                double normalizedLakeDepth = (lakeDepthSampler.noise(mutable.getX() * 0.3, 0, mutable.getZ() * 0.3) + 1) * 0.5F;
                                double lakeDepth = BlendingFunction.EaseInOutCirc.INSTANCE.apply(clampedDelta, 1, normalizedLakeDepth * 5);
                                mutable.set(worldX, heightmap, worldZ);
                                chunk.setBlockState(mutable, Blocks.WATER.defaultBlockState(), false);
                                level.scheduleTick(mutable.immutable(), Fluids.WATER, 0);
                                chunk.markPosForPostprocessing(mutable.move(Direction.UP));
                                chunk.markPosForPostprocessing(mutable.move(Direction.UP));

                                for (int lakeY = 1; lakeY < lakeDepth; lakeY++) {
                                    mutable.set(worldX, heightmap - lakeY, worldZ);
                                    chunk.setBlockState(mutable, Blocks.WATER.defaultBlockState(), false);
                                    level.scheduleTick(mutable.immutable(), Fluids.WATER, 0);
                                }
                            } else {
                                mutable.set(worldX, heightmap, worldZ);
                                chunk.setBlockState(mutable, Blocks.WATER.defaultBlockState(), false);
                                level.scheduleTick(mutable.immutable(), Fluids.WATER, 0);
                                chunk.markPosForPostprocessing(mutable.move(Direction.UP));
                                chunk.markPosForPostprocessing(mutable.move(Direction.UP));
                            }
                        }
                    }
                }
            }
        }


        return true;
    }

    private static double getNoise(ImprovedNoise noiseSampler, int worldX, int worldZ) {
        double freq = 0.05;
        return (noiseSampler.noise(worldX * freq, 0, worldZ * freq) + 1) * 0.5;
    }
}
