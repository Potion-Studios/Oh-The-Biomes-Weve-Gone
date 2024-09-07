package net.potionstudios.biomeswevegone.world.level.levelgen.structure.sharpenedrock;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.BWGStructurePieceTypes;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;

public class SharpenedRockPiece extends StructurePiece {
    private final BlockPos origin;
    private final int radius;
    private final boolean hasFoundation;
    private final boolean hasSpike;
    private final double pitch;
    private final double yaw;
    private final int[] cache;

    public SharpenedRockPiece(BlockPos origin, int radius, boolean hasFoundation, boolean hasSpike, double pitch, double yaw, int[] cache, int genDepth, BoundingBox box) {
        super(BWGStructurePieceTypes.SHARPENED_ROCK_PIECE.get(), genDepth, box);
        this.origin = origin;
        this.radius = radius;
        this.hasFoundation = hasFoundation;
        this.hasSpike = hasSpike;
        this.pitch = pitch;
        this.yaw = yaw;
        this.cache = cache;
    }

    public SharpenedRockPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(BWGStructurePieceTypes.SHARPENED_ROCK_PIECE.get(), tag);
        this.origin = NbtUtils.readBlockPos(tag, "origin").orElseThrow();
        this.radius = tag.getInt("radius");
        this.hasFoundation = tag.getBoolean("foundation");
        this.hasSpike = tag.getBoolean("spike");
        this.pitch = tag.getDouble("pitch");
        this.yaw = tag.getDouble("yaw");
        this.cache = tag.getIntArray("height_cache");
    }

    @Override
    protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext structurePieceSerializationContext, CompoundTag compoundTag) {
        compoundTag.put("origin", NbtUtils.writeBlockPos(this.origin));
        compoundTag.putInt("radius", this.radius);
        compoundTag.putBoolean("foundation", this.hasFoundation);
        compoundTag.putBoolean("spike", this.hasSpike);
        compoundTag.putDouble("pitch", this.pitch);
        compoundTag.putDouble("yaw", this.yaw);
        compoundTag.putIntArray("height_cache", this.cache);
    }

    @Override
    public void postProcess(WorldGenLevel worldGenLevel, @NotNull StructureManager structureManager, @NotNull ChunkGenerator chunkGenerator, @NotNull RandomSource r, @NotNull BoundingBox boundingBox, @NotNull ChunkPos chunkPos, @NotNull BlockPos blockPos) {
        RandomSource randomSource = RandomSource.create(worldGenLevel.getLevel().getServer().getWorldData().worldGenOptions().seed() + origin.asLong());


        BlockStateProvider blocks = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.WHITE_TERRACOTTA.defaultBlockState(), 10).add(Blocks.LIGHT_GRAY_TERRACOTTA.defaultBlockState(), 1).build());
        LongSet placed = new LongOpenHashSet();

        Quaterniond quaternion = new Quaterniond();
        quaternion.rotateY(Math.toRadians(yaw));
        quaternion.rotateX(Math.toRadians(pitch));

        BlockState[] bandStates = new BlockState[] {
                Blocks.WHITE_TERRACOTTA.defaultBlockState(),
                Blocks.LIGHT_GRAY_TERRACOTTA.defaultBlockState(),
                Blocks.WHITE_TERRACOTTA.defaultBlockState(),
                Blocks.LIGHT_GRAY_TERRACOTTA.defaultBlockState(),
                Blocks.WHITE_TERRACOTTA.defaultBlockState()
        };

        BlockState[] bands = new BlockState[25];



        for (int i = 0; i < bands.length;) {
            BlockState selectedState = bandStates[randomSource.nextInt(bandStates.length)];

            int fillerSize = randomSource.nextInt(1, 5);
            for (int filler = 0; filler < fillerSize; filler++) {
                if (i >= bands.length) {
                    break;
                }

                bands[i] = selectedState;
                i++;
            }
        }


        if (hasSpike) {
            SharpenedRockStructure.generateFromCache(radius, this.cache, origin, quaternion, false, (rotatedPos, unRotatedPos) -> {
                if (this.boundingBox.isInside(rotatedPos) && placed.add(rotatedPos.asLong())) {
                    BlockState state = bands[unRotatedPos.getY() % (bands.length - 1)];
                    worldGenLevel.setBlock(rotatedPos, state, 2);
                }

                return true;
            });
        }

        if (hasFoundation) {
            SharpenedRockStructure.generateFromCache(radius, this.cache, origin, quaternion, true, (rotatedPos, unRotatedPos) -> {
                if (this.boundingBox.isInside(rotatedPos) && placed.add(rotatedPos.asLong())) {
                    BlockState state = bands[unRotatedPos.getY() % (bands.length - 1)];
                    worldGenLevel.setBlock(rotatedPos, state, 2);
                    return true;
                }
                return true;
            });
        }
    }
}