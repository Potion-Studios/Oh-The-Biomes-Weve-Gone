package net.potionstudios.biomeswevegone.world.level.levelgen.structure.canyon;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import corgitaco.corgilib.math.LongPair;
import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.BWGStructureTypes;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class CanyonStructure extends Structure {

    public static final MapCodec<CanyonStructure> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    settingsCodec(builder)
            ).apply(builder, CanyonStructure::new)
    );

    public CanyonStructure(StructureSettings settings) {
        super(settings);
    }

    private static final BlockPos MAX = new BlockPos((int) WorldBorder.MAX_SIZE, 0, (int) WorldBorder.MAX_SIZE);
    private static final BlockPos MIN = new BlockPos((int) -WorldBorder.MAX_SIZE, 0, (int) -WorldBorder.MAX_SIZE);


    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();

        int plateauRadius = 100;

        return onTopOfChunkCenter(context, Heightmap.Types.OCEAN_FLOOR_WG, (piecesBuilder) -> {

            BlockPos worldPosition = chunkPos.getMiddleBlockPosition(0);
            BoundingBox area = new BoundingBox(worldPosition.getX() - 256, 0, worldPosition.getZ() - 256, worldPosition.getX() + 256, 0, worldPosition.getZ() + 256);


            LongArrayList randomPositions = getRandomPositions(context.seed(), worldToCanyonQuadrant(plateauRadius) + 1, worldPosition);


            LongLinkedOpenHashSet riverPositions = getRiverPositions(context.seed(), randomPositions, area);

            LevelHeightAccessor levelHeightAccessor = context.heightAccessor();

            createArenaFloor(context, piecesBuilder, plateauRadius, worldPosition, 100, levelHeightAccessor, riverPositions);
        });
    }

    public static @NotNull LongLinkedOpenHashSet getRiverPositions(long worldSeed, LongArrayList randomPositions, BoundingBox area) {
        LongLinkedOpenHashSet riverPositions = new LongLinkedOpenHashSet();
        long[] longArray = randomPositions.toLongArray();

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for (long randomPosition : longArray) {
            mutableBlockPos.set(ChunkPos.getX(randomPosition), 0, ChunkPos.getZ(randomPosition));

            long firstClosest = findClosest(longArray, mutableBlockPos);
            long secondClosest = find2ndClosest(longArray, mutableBlockPos);

            long canyonPos = ChunkPos.asLong(worldToCanyonQuadrant(mutableBlockPos.getX()), worldToCanyonQuadrant(mutableBlockPos.getZ()));

            BlockPos firstClosestPos = new BlockPos(ChunkPos.getX(firstClosest), 0, ChunkPos.getZ(firstClosest));
            BlockPos secondClosestPos = new BlockPos(ChunkPos.getX(secondClosest), 0, ChunkPos.getZ(secondClosest));


            List<BlockPos> positions = List.of(mutableBlockPos, firstClosestPos);
            List<BlockPos> secondPositions = List.of(mutableBlockPos, secondClosestPos);
            riverPositions.addAll(getPositions(getNearest(MIN, positions), getNearest(MAX, positions), area, new WorldgenRandom(new XoroshiroRandomSource(worldSeed + canyonPos)), 10));
            riverPositions.addAll(getPositions(getNearest(MIN, secondPositions), getNearest(MAX, secondPositions), area, new WorldgenRandom(new XoroshiroRandomSource(worldSeed + canyonPos)), 10));
        }
        return riverPositions;
    }

    private static BlockPos getNearest(BlockPos anchor, List<BlockPos> positions) {
        BlockPos lowest = positions.getFirst();

        double closestDist = anchor.distSqr(lowest);

        for (int i = 1; i < positions.size(); i++) {
            BlockPos blockPos = positions.get(i);
            double distance = blockPos.distSqr(anchor);
            if (distance < closestDist) {
                lowest = blockPos;
                closestDist = distance;
            }
        }

        return lowest;
    }

    public static @NotNull LongArrayList getRandomPositions(long worldSeed, int canyonRange, BlockPos worldPosition) {
        LongArrayList randomPositions = new LongArrayList();

        for (int canyonOffsetX = -canyonRange; canyonOffsetX <= canyonRange; canyonOffsetX++) {
            for (int canyonOffsetZ = -canyonRange; canyonOffsetZ <= canyonRange; canyonOffsetZ++) {
                int canyonX = worldToCanyonQuadrant(worldPosition.getX()) + canyonOffsetX;
                int canyonZ = worldToCanyonQuadrant(worldPosition.getZ()) + canyonOffsetZ;
                long canyonPos = ChunkPos.asLong(canyonX, canyonZ);


                WorldgenRandom canyonQuadrantRandom = new WorldgenRandom(new XoroshiroRandomSource(worldSeed + canyonPos));

                int canyonPointCount = canyonQuadrantRandom.nextIntBetweenInclusive(2, 6);

                int size = canyonQuadrantToWorld(1);
                int worldX = canyonQuadrantToWorld(canyonX);
                int worldZ = canyonQuadrantToWorld(canyonZ);

                for (int i = 0; i <= canyonPointCount; i++) {
                    long point = ChunkPos.asLong(worldX + canyonQuadrantRandom.nextIntBetweenInclusive(0, size), worldZ + canyonQuadrantRandom.nextIntBetweenInclusive(0, size));

                    randomPositions.add(point);
                }
            }
        }
        return randomPositions;
    }

    public static int worldToCanyonQuadrant(int coord) {
        return coord >> 7;
    }

    public static int canyonQuadrantToWorld(int coord) {
        return coord << 7;
    }


    public static LongArrayList getPositions(BlockPos startPos, BlockPos endPos, BoundingBox area, WorldgenRandom random, int step) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos().set(startPos);

        double chance = 0.01 * step;
        LongArrayList positions = new LongArrayList();


        int cooldown = 0;
        while (!endPos.closerThan(mutableBlockPos, step)) {
            double angleBetween = angleBetween(mutableBlockPos, endPos);


            if (random.nextDouble() < chance && cooldown >= 5) {
                angleBetween = Mth.randomBetween(random, -Mth.HALF_PI, Mth.HALF_PI);

                chance = 0.01 * step;
                cooldown = 0;
            } else {
                chance *= 1.5;
            }

            cooldown++;


            double xMove = Math.sin(angleBetween) * step;
            double zMove = Math.cos(angleBetween) * step;

            mutableBlockPos.move((int) Math.round(xMove), 0, (int) Math.round(zMove));


            if (area.isInside(mutableBlockPos)) {
                positions.add(ChunkPos.asLong(mutableBlockPos.getX(), mutableBlockPos.getZ()));
            }
        }
        return positions;
    }


    public static long findClosest(long[] points, BlockPos pos) {
        long closest = points[0];

        pos = pos.atY(0);

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for (long point : points) {
            mutableBlockPos.set(ChunkPos.getX(closest), 0, ChunkPos.getZ(closest));
            double distance = pos.distSqr(mutableBlockPos);

            mutableBlockPos.set(ChunkPos.getX(point), 0, ChunkPos.getZ(point));

            if (pos.equals(mutableBlockPos) || pos.closerThan(mutableBlockPos, Math.sqrt(distance))) {
                closest = point;
            }

        }


        return closest;
    }

    public static long find2ndClosest(long[] points, BlockPos pos) {
        Long closest = null;
        Long secondClosest = null;

        pos = pos.atY(0);

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for (long point : points) {
            if (closest != null) {
                mutableBlockPos.set(ChunkPos.getX(closest), 0, ChunkPos.getZ(closest));
                double distance = pos.distSqr(mutableBlockPos);

                mutableBlockPos.set(ChunkPos.getX(point), 0, ChunkPos.getZ(point));

                if (pos.equals(mutableBlockPos) || pos.closerThan(mutableBlockPos, Math.sqrt(distance))) {
                    secondClosest = closest;
                    closest = point;
                }
            } else {
                closest = point;
                secondClosest = point;
            }
        }


        return secondClosest.longValue();
    }

    public static LongPair find2Closest(long[] points, BlockPos pos) {
        Long closest = null;
        Long secondClosest = null;

        pos = pos.atY(0);

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        double closestDistance = Double.MAX_VALUE;
        double secondClosestDistance = Double.MAX_VALUE;

        for (long point : points) {
            mutableBlockPos.set(ChunkPos.getX(point), 0, ChunkPos.getZ(point));
            double distance = pos.distSqr(mutableBlockPos);

            if (distance < closestDistance) {
                secondClosest = closest;
                secondClosestDistance = closestDistance;
                closest = point;
                closestDistance = distance;
            } else if (distance < secondClosestDistance) {
                secondClosest = point;
                secondClosestDistance = distance;
            }
        }

        return new LongPair(closest != null ? closest : 0, secondClosest != null ? secondClosest : 0);
    }

    public static IntIntPair find2ClosestIndexes(long[] points, BlockPos pos) {
        int closest = -1;
        int secondClosest = -1;

        pos = pos.atY(0);

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for (int i = 0; i < points.length; i++) {
            long point = points[i];
            mutableBlockPos.set(ChunkPos.getX(closest), 0, ChunkPos.getZ(closest));
            double distance = pos.distSqr(mutableBlockPos);

            mutableBlockPos.set(ChunkPos.getX(point), 0, ChunkPos.getZ(point));

            if (pos.equals(mutableBlockPos) || pos.closerThan(mutableBlockPos, Math.sqrt(distance))) {
                secondClosest = closest;
                closest = i;
            }
        }


        return new IntIntImmutablePair(closest, secondClosest);
    }


    public static double angleBetween(Vec3i p1, Vec3i p2) {
        double deltaX = p2.getX() - p1.getX();
        double deltaY = p2.getZ() - p1.getZ();
        return Math.atan2(deltaX, deltaY);
    }


    private static void createArenaFloor(GenerationContext context, StructurePiecesBuilder piecesBuilder, int arenaFloorRadius, BlockPos arenaOrigin, int topY, LevelHeightAccessor levelHeightAccessor, LongCollection positions) {
        int range = SectionPos.blockToSectionCoord(arenaFloorRadius) + 1;
        for (int chunkX = -(range); chunkX <= range; chunkX++) {
            for (int chunkZ = -(range); chunkZ <= range; chunkZ++) {
                BlockPos chunkWorldPos = new BlockPos(SectionPos.sectionToBlockCoord(context.chunkPos().x + chunkX), arenaOrigin.getY(), SectionPos.sectionToBlockCoord(context.chunkPos().z + chunkZ));
                BoundingBox boundingBox = new BoundingBox(
                        chunkWorldPos.getX(), levelHeightAccessor.getMinBuildHeight(), chunkWorldPos.getZ(),
                        chunkWorldPos.getX() + 15, levelHeightAccessor.getMaxBuildHeight(), chunkWorldPos.getZ() + 15
                );
                piecesBuilder.addPiece(new CanyonPiece(arenaOrigin, arenaFloorRadius, topY, positions, boundingBox));
            }
        }
    }

    @Override
    public @NotNull StructureType<?> type() {
        return BWGStructureTypes.CANYON.get();
    }
}
