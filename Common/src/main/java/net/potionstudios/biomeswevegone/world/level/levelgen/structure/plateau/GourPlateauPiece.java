package net.potionstudios.biomeswevegone.world.level.levelgen.structure.plateau;

import corgitaco.corgilib.math.blendingfunction.BlendingFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.BWGStructurePieceTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class GourPlateauPiece extends StructurePiece {

    private final BlockPos origin;
    private final int radius;
    private final int topY;


    protected GourPlateauPiece(BlockPos origin, int radius, int topY, BoundingBox boundingBox) {
        super(BWGStructurePieceTypes.GOUR_PLATEAU_PIECE.get(), 0, boundingBox);
        this.origin = origin;
        this.radius = radius;
        this.topY = topY;
    }

    public GourPlateauPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(BWGStructurePieceTypes.GOUR_PLATEAU_PIECE.get(), tag);
        this.origin = NbtUtils.readBlockPos(tag.getCompound("origin"));
        this.radius = tag.getInt("radius");
        this.topY = tag.getInt("topY");
    }

    @Override
    protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext context, CompoundTag tag) {
        tag.put("origin", NbtUtils.writeBlockPos(this.origin));
        tag.putInt("radius", this.radius);
        tag.putInt("topY", this.topY);
    }

    @Override
    public void postProcess(WorldGenLevel worldGenLevel, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        XoroshiroRandomSource randomSource = new XoroshiroRandomSource(worldGenLevel.getSeed() + origin.asLong());
        ImprovedNoise noiseSampler = new ImprovedNoise(randomSource);

        SimplexNoise simplexNoise = new SimplexNoise(randomSource);

        double minRadius = radius * 0.175;
        int plateauThickness = randomSource.nextIntBetweenInclusive(0, 2);
        int plateauSurfacePull = randomSource.nextIntBetweenInclusive(1, 3);
        int[] topYs = new int[16 * 16];
        int[] bottomYs = new int[16 * 16];

        Arrays.fill(topYs, Integer.MIN_VALUE);
        Arrays.fill(bottomYs, Integer.MAX_VALUE);

        BlockStateProvider noiseBasedStateProvider = new NoiseThresholdProvider(
                worldGenLevel.getSeed() + origin.asLong(),
                new NormalNoise.NoiseParameters(0, 1.0),
                0.05F,-0.1F,
                0.5F, Blocks.GRANITE.defaultBlockState(), List.of(Blocks.PACKED_MUD.defaultBlockState()), List.of(Blocks.COARSE_DIRT.defaultBlockState()));

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int blockX = chunkPos.getBlockX(x);
                int blockZ = chunkPos.getBlockZ(z);

                int range = topY - origin.getY();
                mutable.set(blockX, 0, blockZ);


                double noise = noiseSampler.noise(blockX * 0.05, 0, blockZ * 0.05) + 1; // 0-2 range, no negatives

                int minY = Math.min(worldGenLevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, blockX, blockZ) - 1, origin.getY());

                int idx = x + z * 16;

                buildColumn(worldGenLevel, minRadius, minY, topY - minY, mutable, blockX, blockZ, noise, topYs, bottomYs, idx, randomSource, noiseBasedStateProvider);
                thickenPlateau(worldGenLevel, minRadius, mutable, blockX, range, blockZ, noise, plateauThickness, topYs, bottomYs, idx, randomSource, noiseBasedStateProvider);
                pullUpPlateau(worldGenLevel, minRadius, range, mutable, blockX, blockZ, noise, plateauThickness, plateauSurfacePull, topYs, bottomYs, idx, randomSource, noiseBasedStateProvider);
                buildPlateauTerrain(worldGenLevel, minRadius, range, mutable, blockX, blockZ, noise, simplexNoise, plateauThickness, plateauSurfacePull, topYs, bottomYs, idx, randomSource, noiseBasedStateProvider);
            }
        }


        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int blockX = chunkPos.getBlockX(x);
                int blockZ = chunkPos.getBlockZ(z);
                int idx = x + z * 16;

                int topY = topYs[idx];
                if (topY != Integer.MIN_VALUE) {
                    mutable.set(blockX, topY, blockZ);

                    if (!worldGenLevel.getBlockState(mutable.move(Direction.DOWN)).isAir()) {
                        mutable.move(Direction.UP);
                        worldGenLevel.setBlock(mutable, Blocks.GRASS_BLOCK.defaultBlockState(), 2);
                    }

                    mutable.move(Direction.DOWN);
                    if (!worldGenLevel.getBlockState(mutable.move(Direction.DOWN)).isAir()) {
                        mutable.move(Direction.UP);
                        worldGenLevel.setBlock(mutable, Blocks.DIRT.defaultBlockState(), 2);
                    }
                }
            }
        }

    }

    private void buildPlateauTerrain(WorldGenLevel worldGenLevel, double minRadius, int range, BlockPos.MutableBlockPos mutable, int blockX, int blockZ, double noise, SimplexNoise simplexNoise, int plateauThickness, int plateauSurfacePull, int[] topYs, int[] bottomYs, int idx, RandomSource randomSource, BlockStateProvider stateProvider) {
        double factor = Mth.clamp((double) (range - 1 - plateauThickness - plateauSurfacePull) / range, 0, 1);

        double radiusFactor = Mth.clampedLerp(-0.5, 1, factor);
        mutable.set(blockX, origin.getY() + plateauThickness + range + plateauSurfacePull - 1, blockZ);

        double delta = noise * 0.5; // 0-1 range
        double amplifiedDelta = Mth.clampedLerp(0.2, 1, BlendingFunction.EaseOutQuint.INSTANCE.apply(delta));
        int localRadius = (int) (BlendingFunction.EaseInCirc.INSTANCE.apply(radiusFactor, minRadius, radius) * amplifiedDelta);

        BlockPos offsetOrigin = origin.offset(0, plateauThickness + range + plateauSurfacePull, 0);
        if (offsetOrigin.closerThan(mutable, localRadius)) {
            double simplexNoiseValue = simplexNoise.getValue(blockX * 0.007, blockZ * 0.007) + 1;
            double maxNoiseY = (simplexNoiseValue) * 2;

            double noiseFactor = Mth.clamp(mutable.distSqr(offsetOrigin) / Mth.square(localRadius), 0, 1);
            double apply = BlendingFunction.EaseOutCubic.INSTANCE.apply(1 - noiseFactor, 0, maxNoiseY);


            for (int noiseY = 0; noiseY <= apply; noiseY++) {
                mutable.move(Direction.UP);
                worldGenLevel.setBlock(mutable, stateProvider.getState(randomSource, mutable), 2);
                topYs[idx] = Math.max(mutable.getY(), topYs[idx]);
                bottomYs[idx] = Math.max(mutable.getY(), bottomYs[idx]);
            }
        }
    }

    private void pullUpPlateau(WorldGenLevel worldGenLevel, double minRadius, int range, BlockPos.MutableBlockPos mutable, int blockX, int blockZ, double noise, int plateauThickness, int plateauSurfacePull, int[] topYs, int[] bottomYs, int idx, RandomSource randomSource, BlockStateProvider stateProvider) {
        for (int y = 0; y < plateauSurfacePull; y++) { // Pull up the plateau
            double factor = Mth.clamp((double) (range - 2 - y) / range, 0, 1);

            double radiusFactor = Mth.clampedLerp(-0.5, 1, factor);
            mutable.set(blockX, origin.getY() + y + range + plateauThickness, blockZ);

            double delta = noise * 0.5; // 0-1 range
            double amplifiedDelta = Mth.clampedLerp(0.2, 1, BlendingFunction.EaseOutQuint.INSTANCE.apply(delta));
            int localRadius = (int) (BlendingFunction.EaseInCirc.INSTANCE.apply(radiusFactor, minRadius, radius) * amplifiedDelta);


            if (origin.offset(0, y + range + plateauThickness, 0).closerThan(mutable, localRadius)) {
                worldGenLevel.setBlock(mutable, stateProvider.getState(randomSource, mutable), 2);
                topYs[idx] = Math.max(mutable.getY(), topYs[idx]);
                bottomYs[idx] = Math.max(mutable.getY(), bottomYs[idx]);
                mutable.setY(origin.getY());
            }
        }
    }

    private void thickenPlateau(WorldGenLevel worldGenLevel, double minRadius, BlockPos.MutableBlockPos mutable, int blockX, int range, int blockZ, double noise, int plateauThickness, int[] topYs, int[] bottomYs, int idx, RandomSource randomSource, BlockStateProvider stateProvider) {
        double factor = Mth.clamp((double) (range - 1) / range, 0, 1);
        double radiusFactor = Mth.clampedLerp(-0.5, 1, factor);
        double delta = noise * 0.5; // 0-1 range
        double amplifiedDelta = Mth.clampedLerp(0.2, 1, BlendingFunction.EaseOutQuint.INSTANCE.apply(delta));
        int localRadius = (int) (BlendingFunction.EaseInCirc.INSTANCE.apply(radiusFactor, minRadius, radius) * amplifiedDelta);

        for (int y = 0; y < plateauThickness; y++) {
            mutable.set(blockX, origin.getY() + y + range, blockZ);

            if (origin.offset(0, y + range, 0).closerThan(mutable, localRadius)) {
                worldGenLevel.setBlock(mutable, stateProvider.getState(randomSource, mutable), 2);
                topYs[idx] = Math.max(mutable.getY(), topYs[idx]);
                bottomYs[idx] = Math.max(mutable.getY(), bottomYs[idx]);
                mutable.setY(origin.getY());
            }
        }
    }

    private void buildColumn(WorldGenLevel worldGenLevel, double minRadius, int minY, int range, BlockPos.MutableBlockPos mutable, int blockX, int blockZ, double noise, int[] topYs, int[] bottomYs, int idx, RandomSource randomSource, BlockStateProvider stateProvider) {

        for (int y = 0; y < range; y++) {
            double factor = Mth.clamp((double) y / range, 0, 1);
            double radiusFactor = Mth.clampedLerp(-0.5, 1, factor);
            mutable.set(blockX, minY + y, blockZ);

            double delta = noise * 0.5; // 0-1 range
            double amplifiedDelta = Mth.clampedLerp(0.2, 1, BlendingFunction.EaseOutQuint.INSTANCE.apply(delta));
            int localRadius = (int) (BlendingFunction.EaseInCirc.INSTANCE.apply(radiusFactor, minRadius, radius) * amplifiedDelta);


            if (origin.atY(minY).offset(0, y, 0).closerThan(mutable, localRadius)) {
                worldGenLevel.setBlock(mutable, stateProvider.getState(randomSource, mutable), 2);

                topYs[idx] = Math.max(mutable.getY(), topYs[idx]);
                bottomYs[idx] = Math.max(mutable.getY(), bottomYs[idx]);
                mutable.setY(minY);
            }
        }
    }
}
