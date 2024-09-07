package net.potionstudios.biomeswevegone.world.level.levelgen.structure.arch;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import corgitaco.corgilib.math.blendingfunction.BlendingFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.biomeswevegone.util.UnsafeBoundingBox;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.BWGStructureTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

public class ArchStructure extends Structure {

    public static final BoundingBox INFINITE = BoundingBox.infinite();

    public static final MapCodec<ArchStructure> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    settingsCodec(builder),
                    ArchConfig.CODEC.fieldOf("config").forGetter(archStructure -> archStructure.config)
            ).apply(builder, ArchStructure::new)
    );
    private final ArchConfig config;

    public ArchStructure(StructureSettings settings, ArchConfig config) {
        super(settings);
        this.config = config;
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(@NotNull GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.OCEAN_FLOOR, structurePiecesBuilder -> {
            ChunkPos chunkPos = context.chunkPos();
            WorldgenRandom random = context.random();

            int blockX = chunkPos.getBlockX(random.nextInt(16));
            int blockZ = chunkPos.getBlockZ(random.nextInt(16));
            ChunkGenerator chunkGenerator = context.chunkGenerator();
            int blockY = chunkGenerator.getBaseHeight(blockX, blockZ, Heightmap.Types.OCEAN_FLOOR_WG, context.heightAccessor(), context.randomState());
            int yOffset = this.config.height().sample(random);


            BlockPos structureCenter = new BlockPos(blockX, blockY, blockZ);
            Vec3 originVec3 = Vec3.atCenterOf(structureCenter);

            BlockPos offset = new BlockPos((this.config.length().sample(random) / 2) * (random.nextBoolean() ? 1 : -1), 0, (this.config.length().sample(random) / 2) * (random.nextBoolean() ? 1 : -1));

            BlockPos first = structureCenter.offset(offset);
            first = first.atY(chunkGenerator.getBaseHeight(first.getX(), first.getZ(), Heightmap.Types.OCEAN_FLOOR_WG, context.heightAccessor(), context.randomState()) - 10);
            Vec3 firstVec3 = Vec3.atCenterOf(first);

            BlockPos second = structureCenter.subtract(offset);
            second = second.atY(chunkGenerator.getBaseHeight(second.getX(), second.getZ(), Heightmap.Types.OCEAN_FLOOR_WG, context.heightAccessor(), context.randomState()) - 10);
            Vec3 secondVec3 = Vec3.atCenterOf(second);

            Long2ObjectOpenHashMap<UnsafeBoundingBox> generatingChunks = new Long2ObjectOpenHashMap<>();

            Consumer<BlockPos> stepAction = position -> generatingChunks.computeIfAbsent(ChunkPos.asLong(position), key -> new UnsafeBoundingBox()).encapsulate(position);
            this.config.archGeneratorConfig().generate(context.seed() + structureCenter.asLong(), yOffset, firstVec3, originVec3, secondVec3, INFINITE, stepAction);


            BlockPos finalFirst = first;
            BlockPos finalSecond = second;
            generatingChunks.long2ObjectEntrySet().fastForEach(unsafeBoundingBoxEntry ->
                    structurePiecesBuilder.addPiece(new ArchPiece(unsafeBoundingBoxEntry.getValue().toBoundingBox(), structureCenter, finalFirst, finalSecond, yOffset, this.config.archGeneratorConfig(), this.config.checkedBlockPlacement()))
            );
        });
    }

    public static void between(Vec3 center, Vec3 second, double xzStepDistance, int yOffset, double step3dDistance, BlendingFunction blendingFunction, Consumer<BlockPos> stepAction) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        Vec3 xzDifference = center.subtract(second);

        double xzDistance = xzDifference.horizontalDistance();

        double xzTotalSteps = xzDistance / xzStepDistance;
        for (int step = 0; step < xzTotalSteps; step++) {

            double factor = step / xzTotalSteps;
            Vec3 stepVec3 = center.add(xzDifference.normalize().scale(step * xzStepDistance));
            double yPos = blendingFunction.apply(1 - factor, second.y, center.y + yOffset);
            Vec3 stepVec3YOffset = new Vec3(stepVec3.x, yPos, stepVec3.z);

            int nextStep = step + 1;
            double nextFactor = nextStep / xzTotalSteps;
            Vec3 nextStepVec3 = center.add(xzDifference.normalize().scale(nextStep * xzStepDistance));
            double nextYPos = blendingFunction.apply(1 - nextFactor, second.y, center.y + yOffset);
            Vec3 nextStepVec3YOffset = new Vec3(nextStepVec3.x, nextYPos, nextStepVec3.z);

            between3D(step3dDistance, nextStepVec3YOffset, stepVec3YOffset, mutableBlockPos, stepAction);
        }
    }

    private static void between3D(double step3dDistance, Vec3 nextStepVec3YOffset, Vec3 stepVec3YOffset, BlockPos.MutableBlockPos mutableBlockPos, Consumer<BlockPos> stepAction) {
        Vec3 difference3D = nextStepVec3YOffset.subtract(stepVec3YOffset);
        double distance3d = difference3D.length();

        double totalSteps3D = distance3d / step3dDistance;
        for (int step3D = 0; step3D <= totalSteps3D; step3D++) {
            Vec3 step3DVec3 = stepVec3YOffset.add(difference3D.normalize().scale(step3D * step3dDistance));

            mutableBlockPos.set(step3DVec3.x, step3DVec3.y, step3DVec3.z);
            stepAction.accept(mutableBlockPos.immutable());
        }
    }

    public static void generate(long seed, double thickness, double frequency, BlockPos stepOrigin, BoundingBox effectedArea, Consumer<BlockPos> action) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        ImprovedNoise noise = new ImprovedNoise(new XoroshiroRandomSource(seed));

        if (!effectedArea.equals(INFINITE) && !effectedArea.inflatedBy((int)thickness + 1).isInside(stepOrigin)) {
            return;
        }

        for (double x = -thickness; x <= thickness; x++) {
            for (double y = -thickness; y <= thickness; y++) {
                for (double z = -thickness; z <= thickness; z++) {
                    mutableBlockPos.setWithOffset(stepOrigin, (int) x, (int) y, (int) z);
                    if (!effectedArea.equals(INFINITE) && !effectedArea.isInside(mutableBlockPos)) {
                        continue;
                    }

                    if (stepOrigin.distSqr(mutableBlockPos) < Mth.square(thickness)) {
                        double noiseSample = (noise.noise(mutableBlockPos.getX() * frequency, mutableBlockPos.getY() * frequency, mutableBlockPos.getZ() * frequency) + 1) * 0.5;

                        double localRadius = Mth.clampedLerp(thickness * 0.5, thickness, noiseSample);

                        if (stepOrigin.distSqr(mutableBlockPos) < Mth.square(localRadius)) {
                            action.accept(mutableBlockPos);
                        }
                    }
                }
            }
        }
    }

    @Override
    public @NotNull StructureType<?> type() {
        return BWGStructureTypes.ARCH.get();
    }
}
