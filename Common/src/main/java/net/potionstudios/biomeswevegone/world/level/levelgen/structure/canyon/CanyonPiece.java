package net.potionstudios.biomeswevegone.world.level.levelgen.structure.canyon;

import corgitaco.corgilib.math.LongPair;
import corgitaco.corgilib.math.blendingfunction.BlendingFunction;
import it.unimi.dsi.fastutil.longs.LongCollection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.potionstudios.biomeswevegone.tags.BWGBiomeTags;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.BWGStructurePieceTypes;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;

import java.util.Arrays;
import java.util.function.Predicate;

public class CanyonPiece extends StructurePiece {

    private final BlockPos origin;
    private final int radius;
    private final int topY;
    private final long[] packedCanyonPositions;


    protected CanyonPiece(BlockPos origin, int radius, int topY, LongCollection packedCanyonPositions, BoundingBox boundingBox) {
        super(BWGStructurePieceTypes.CANYON_PIECE.get(), 0, boundingBox);
        this.origin = origin;
        this.radius = radius;
        this.topY = topY;
        this.packedCanyonPositions = packedCanyonPositions.toLongArray();
    }

    public CanyonPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(BWGStructurePieceTypes.CANYON_PIECE.get(), tag);
        this.origin = NbtUtils.readBlockPos(tag, "origin").orElseThrow();
        this.radius = tag.getInt("radius");
        this.topY = tag.getInt("topY");
        this.packedCanyonPositions = tag.getLongArray("canyon_positions");
    }

    @Override
    protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext context, CompoundTag tag) {
        tag.put("origin", NbtUtils.writeBlockPos(this.origin));
        tag.putInt("radius", this.radius);
        tag.putInt("topY", this.topY);
        tag.putLongArray("canyon_positions", this.packedCanyonPositions);
    }

    @Override
    public void postProcess(@NotNull WorldGenLevel worldGenLevel, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource unused, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos) {
        generate(worldGenLevel, generator, chunkPos, this.packedCanyonPositions);
    }

    public static void generate(WorldGenLevel worldGenLevel, ChunkGenerator generator, ChunkPos chunkPos, long[] packedCanyonPositions) {
        WorldgenRandom worldgenRandom = new WorldgenRandom(new XoroshiroRandomSource(worldGenLevel.getSeed()));
        WorldgenRandom originRandom = new WorldgenRandom(new XoroshiroRandomSource(worldGenLevel.getSeed()));
        ChunkAccess chunk = worldGenLevel.getChunk(chunkPos.x, chunkPos.z);
        ImprovedNoise noise = new ImprovedNoise(worldgenRandom);

        int distance = Mth.square(worldgenRandom.nextIntBetweenInclusive(15, 20));



        int biomeSampleDistance = 24;


        BlockState[] bandStates = new BlockState[]{
                Blocks.TERRACOTTA.defaultBlockState(),
                Blocks.WHITE_TERRACOTTA.defaultBlockState(),
                Blocks.LIGHT_GRAY_TERRACOTTA.defaultBlockState(),
                Blocks.WHITE_TERRACOTTA.defaultBlockState(),
                Blocks.ORANGE_TERRACOTTA.defaultBlockState()
        };

        BlockState[] bands = new BlockState[25];


        for (int i = 0; i < bands.length; ) {
            BlockState selectedState = bandStates[worldgenRandom.nextInt(bandStates.length)];

            int fillerSize = worldgenRandom.nextInt(1, 5);
            for (int filler = 0; filler < fillerSize; filler++) {
                if (i >= bands.length) {
                    break;
                }

                bands[i] = selectedState;
                i++;
            }
        }


        BlendingFunction[] functions = new BlendingFunction[]{
                BlendingFunction.EaseInOutCirc.INSTANCE,
                BlendingFunction.EaseInCirc.INSTANCE,
                BlendingFunction.EaseOutCubic.INSTANCE,
                BlendingFunction.EaseOutQuint.INSTANCE
        };

        double[] anchors = new double[16 * 16];
        double[] biomeAnchors = new double[16 * 16];

        Arrays.fill(biomeAnchors, -1.0);

        int[] heightmap = new int[16 * 16];
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int blockX = chunkPos.getBlockX(x);
                int blockZ = chunkPos.getBlockZ(z);
                mutable.set(blockX, 0, blockZ);
                LongPair nearest2Points = CanyonStructure.find2Closest(packedCanyonPositions, mutable);


//                if (blockX == ChunkPos.getX(nearest2Points.getVal1()) && blockZ == ChunkPos.getZ(nearest2Points.getVal1())) {
//                    chunk.setBlockState(mutable.setY(140), Blocks.DIAMOND_BLOCK.defaultBlockState(), false);
//                }
//
//                if (blockX == ChunkPos.getX(nearest2Points.getVal2()) && blockZ == ChunkPos.getZ(nearest2Points.getVal2())) {
//                    chunk.setBlockState(mutable.setY(145), Blocks.EMERALD_BLOCK.defaultBlockState(), false);
//                }

                int index = x + z * 16;
                anchors[index] = getDistanceToClosestPoint(nearest2Points.getVal1(), nearest2Points.getVal2(), blockX, blockZ);
                biomeAnchors[index] = nearestBiome(blockX, blockZ, biomeSampleDistance, worldGenLevel, biomeHolder -> biomeHolder.is(BWGBiomeTags.CANYON) || biomeHolder.is(BiomeTags.IS_RIVER));
                heightmap[index] = worldGenLevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, mutable.getX(), mutable.getZ()) - 1;

            }
        }

        int[] mins = new int[16 * 16];

        int[] maxes = new int[16 * 16];
        int[] offsets = new int[16 * 16];
        int[] distances = new int[16 * 16];


        Arrays.fill(mins, generator.getSeaLevel() - 2);
        Arrays.fill(maxes, generator.getSeaLevel() - 2 + worldgenRandom.nextIntBetweenInclusive(10, 20));
        Arrays.fill(offsets, distance / 2);

        for (int layer = 0; layer <= 10; layer++) {
            double layerDelta = (double) layer / 10;

            ImprovedNoise layerNoise = new ImprovedNoise(new WorldgenRandom(new XoroshiroRandomSource(worldGenLevel.getSeed() + layer)));
            WorldgenRandom layerRandom = new WorldgenRandom(new XoroshiroRandomSource(worldGenLevel.getSeed() + layer));

            BlendingFunction blendingFunction = functions[layerRandom.nextInt(functions.length)];
            int layerIncrement = layerRandom.nextIntBetweenInclusive(5, 15);
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    int blockX = chunkPos.getBlockX(x);
                    int blockZ = chunkPos.getBlockZ(z);

                    int index = x + z * 16;
                    double distSqr = anchors[index];

                    maxes[index] += (int) ((((layerNoise.noise(blockX * 0.05, 0, blockZ * 0.05) + 1.0)) * 0.5) * Mth.clampedLerp(10, 5, layerDelta));

                    int min = mins[index];
                    int max = maxes[index];

                    int minY = min + 1;

                    if (layer == 0) {
                        minY = heightmap[index];
                    }

                    double biomeAnchor = biomeAnchors[index];

                    if (biomeAnchor == 0) { // Nothing to do, let's skip
                        continue;
                    }


                    if (biomeAnchor > 0) {
                        double biomeDelta = biomeAnchor / Mth.square(biomeSampleDistance);
                        double clampedDelta = Mth.clamp(biomeDelta, 0, 1);
                        maxes[index] = (int) Mth.clampedLerp(minY, max, clampedDelta);
//                        chunk.setBlockState(new BlockPos(blockX, (int) Mth.clampedLerp(200, 180, clampedDelta), blockZ), Blocks.REDSTONE_BLOCK.defaultBlockState(), false);

                    }

                    max = maxes[index];
                    int offset = offsets[index];


                    buildLayer(blendingFunction, distSqr, distance, blockX, blockZ, minY, min, max, offset, pos1 -> {
                        BlockState state = bands[Math.floorMod(pos1.getY() + Math.round((layerNoise.noise(pos1.getX() * 0.05F, 0, pos1.getZ() * 0.05F) + 1) * 6), bands.length - 1)];
                        if (chunk.getBlockState(pos1).canBeReplaced()) {
                            chunk.setBlockState(pos1, state, false);
                        } else {
                            return true;
                        }
                        return false;
                    });

                    mins[index] = maxes[index];
                    maxes[index] += layerIncrement;


                    if (offset == 0) {
                        offsets[index] = distance;
                    } else {
                        double delta1 = (layerNoise.noise(blockX * 0.007F, 0, blockZ * 0.007F) + 1) * 0.5F;


                        double bendFreq = 0.01;
                        double lowBend = Mth.clampedLerp(10, 15, layerNoise.noise((blockX + 1000) * bendFreq, 0, (blockZ + 1000) * bendFreq) + 1 * 0.5);
                        double highBend = Mth.clampedLerp(15, 65, layerNoise.noise((blockX + 10000) * bendFreq, 0, (blockZ + 10000) * bendFreq) + 1 * 0.5);

                        int offsetIncrement = (int) Mth.square(Mth.clampedLerp(5, highBend, delta1));
                        offsets[index] += offsetIncrement;
                    }
                }
            }
        }
    }

    private static double nearestBiome(int blockX, int blockZ, int blockSearchRadius, WorldGenLevel level, Predicate<Holder<Biome>> biome) {
        int seaLevel = level.getSeaLevel();
        BlockPos origin = new BlockPos(blockX, seaLevel, blockZ);

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        int blockSearchDiameter = blockSearchRadius * 2;
        boolean[] sampled = new boolean[blockSearchDiameter * blockSearchDiameter];


        if (!biome.test(level.getBiome(mutable.set(blockX, seaLevel, blockZ)))) {
            return 0;
        }

        for (int step = 1; step < blockSearchRadius; step += 2) {
            int slices = 16 * step; // We would use 8 here but sampling like this lacks precision.

            double sliceSize = Mth.TWO_PI / slices;

            for (int i = 0; i <= slices; i++) {
                double angle = i * sliceSize;

                int offsetX = (int) Math.round((Math.sin(angle) * step));
                int offsetZ = (int) Math.round((Math.cos(angle) * step));

                int localIndex = ((blockSearchRadius + offsetX) + (offsetZ + blockSearchRadius)) * blockSearchRadius;
                if (!sampled[localIndex]) {
                    sampled[localIndex] = true; // Avoid sampling the same position twice.

                    int worldX = blockX + offsetX;
                    int worldZ = blockZ + offsetZ;

                    mutable.set(worldX, seaLevel, worldZ);

                    // Verify this structure is within the confines of its biome.
                    Holder<Biome> biome1 = level.getBiome(mutable);

                    if (!biome.test(biome1)) {
                        return mutable.distSqr(origin);
                    }
                }
            }
        }

        return -1;
    }


    private static double getDistanceToClosestPoint(Position nearest, Position secondNearest, Position worldPos) {
        return getDistanceToClosestPoint(nearest.x(), nearest.z(), secondNearest.x(), secondNearest.z(), worldPos.x(), worldPos.z());
    }

    private static double getDistanceToClosestPoint(Position nearest, Position secondNearest, double worldX, double worldZ) {
        return getDistanceToClosestPoint(nearest.x(), nearest.z(), secondNearest.x(), secondNearest.z(), worldX, worldZ);
    }

    private static double getDistanceToClosestPoint(long nearest, long secondNearest, int worldX, int worldZ) {
        return getDistanceToClosestPoint(ChunkPos.getX(nearest), ChunkPos.getZ(nearest), ChunkPos.getX(secondNearest), ChunkPos.getZ(secondNearest), worldX, worldZ);
    }

    private static double getDistanceToClosestPoint(double nearestX, double nearestZ, double secondNearestX, double secondNearestZ, double worldX, double worldZ) {
        return getClosestPoint(nearestX, nearestZ, secondNearestX, secondNearestZ, worldX, worldZ).distanceSquared(new Vector2d(worldX, worldZ));
    }

    private static Vector2d getClosestPoint(double nearestX, double nearestZ, double secondNearestX, double secondNearestZ, double worldX, double worldZ) {
        Vector2d point = new Vector2d(worldX, worldZ);
        Vector2d nearest = new Vector2d(nearestX, nearestZ);
        Vector2d secondNearest = new Vector2d(secondNearestX, secondNearestZ);

        Vector2d line = new Vector2d(secondNearest).sub(nearest);

        if (line.lengthSquared() == 0) { // Prevent NaN
            return nearest;
        }

        line.normalize();

        double dotProduct = new Vector2d(point).sub(nearest).dot(line);
        dotProduct = Math.max(0, Math.min(dotProduct, line.length()));
        return new Vector2d(nearest).add(new Vector2d(line).mul(dotProduct));
    }


    private static void buildLayer(BlendingFunction function, double distSqr, int distance, int blockX, int blockZ, int minY, int min, int max, int offset, Predicate<BlockPos> blockPosPredicate) {
        double apply = function.apply(Mth.clampedLerp(0, 1, (distSqr - offset) / (distance)), min, max);


        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int y = (int) Math.round(apply); y >= minY; y--) {

            mutableBlockPos.set(blockX, y, blockZ);
            if (blockPosPredicate.test(mutableBlockPos)) {
                break;
            }
        }
    }

    private static void setTopBlocks(WorldGenLevel worldGenLevel, ChunkPos chunkPos, int[] topYs, BlockPos.MutableBlockPos mutable) {
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
}
