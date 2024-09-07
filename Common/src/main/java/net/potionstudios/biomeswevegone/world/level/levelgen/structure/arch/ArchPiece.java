package net.potionstudios.biomeswevegone.world.level.levelgen.structure.arch;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.resources.RegistryOps;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.biomeswevegone.world.level.levelgen.CheckedBlockPlacement;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.BWGStructurePieceTypes;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ArchPiece extends StructurePiece {
    private final BlockPos origin;
    private final BlockPos first;
    private final BlockPos second;
    private final int yOffset;
    private final ArchConfig.ArchGeneratorConfig generatorConfig;
    private final CheckedBlockPlacement blockPlacement;

    protected ArchPiece(BoundingBox boundingBox, BlockPos origin, BlockPos first, BlockPos second, int yOffset, ArchConfig.ArchGeneratorConfig generatorConfig, CheckedBlockPlacement blockPlacement) {
        super(BWGStructurePieceTypes.ARCH_PIECE.get(), 0, boundingBox);
        this.origin = origin;
        this.first = first;
        this.second = second;
        this.yOffset = yOffset;
        this.generatorConfig = generatorConfig;
        this.blockPlacement = blockPlacement;
    }

    public ArchPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(BWGStructurePieceTypes.ARCH_PIECE.get(), tag);
        this.origin = NbtUtils.readBlockPos(tag, "origin").orElseThrow();
        this.first = NbtUtils.readBlockPos(tag, "first").orElseThrow();
        this.second = NbtUtils.readBlockPos(tag, "second").orElseThrow();
        this.yOffset = tag.getInt("yOffset");

        RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, context.registryAccess());
        this.generatorConfig = ArchConfig.ArchGeneratorConfig.CODEC.decode(ops, tag.getCompound("generatorConfig")).result().orElseThrow().getFirst();
        this.blockPlacement = CheckedBlockPlacement.CODEC.decode(ops, tag.get("blockPlacements")).result().orElseThrow().getFirst();
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        tag.put("origin", NbtUtils.writeBlockPos(this.origin));
        tag.put("first", NbtUtils.writeBlockPos(this.first));
        tag.put("second", NbtUtils.writeBlockPos(this.second));
        tag.putInt("yOffset", this.yOffset);

        RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, context.registryAccess());
        tag.put("generatorConfig", ArchConfig.ArchGeneratorConfig.CODEC.encodeStart(ops, this.generatorConfig).result().orElseThrow());
        tag.put("blockPlacements", CheckedBlockPlacement.CODEC.encodeStart(ops, this.blockPlacement).result().orElseThrow());

    }

    @Override
    public void postProcess(WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, ChunkPos chunkPos, @NotNull BlockPos pos) {
        ChunkAccess chunk = level.getChunk(chunkPos.x, chunkPos.z); // Minimize getChunk calls

        LongSet cache = new LongOpenHashSet();
        Consumer<BlockPos> blockPosConsumer = blockPos -> {
            if (this.boundingBox.isInside(blockPos)) {
                cache.add(blockPos.asLong());
            }
        };

        this.generatorConfig.generate(this.origin.asLong() + level.getSeed(), this.yOffset, Vec3.atCenterOf(this.first), Vec3.atCenterOf(this.origin), Vec3.atCenterOf(this.second), this.boundingBox, blockPosConsumer);

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for (Pair<BlockPredicate, BlockStateProvider> blockPlacement : this.blockPlacement.blockPlacement()) {
            cache.forEach(packedPos -> {
                mutableBlockPos.set(packedPos);
                if (blockPlacement.getFirst().test(level, mutableBlockPos)) {
                    chunk.setBlockState(mutableBlockPos, blockPlacement.getSecond().getState(random, mutableBlockPos), false);
                }
            });
        }
    }
}
