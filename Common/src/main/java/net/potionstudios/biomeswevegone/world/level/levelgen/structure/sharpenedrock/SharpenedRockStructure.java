package net.potionstudios.biomeswevegone.world.level.levelgen.structure.sharpenedrock;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import corgitaco.corgilib.math.blendingfunction.BlendingFunction;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.BWGStructureTypes;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Vector3d;

import java.util.Optional;
import java.util.function.BiPredicate;

public class SharpenedRockStructure extends Structure {
    public static final ImprovedNoise NOISE = new ImprovedNoise(new XoroshiroRandomSource(100L));


    public static final MapCodec<SharpenedRockStructure> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    settingsCodec(builder),
                    SharpenedRockConfig.CODEC.fieldOf("config").forGetter(sharpenedRockStructure -> sharpenedRockStructure.config)
            ).apply(builder, SharpenedRockStructure::new)
    );
    private final SharpenedRockConfig config;

    public SharpenedRockStructure(StructureSettings structureSettings, SharpenedRockConfig config) {
        super(structureSettings);
        this.config = config;
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(@NotNull GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.OCEAN_FLOOR_WG, (piecesBuilder) -> {

            WorldgenRandom random = context.random();
            RandomState randomState = context.randomState();
            ChunkGenerator chunkGenerator = context.chunkGenerator();

            int radius = this.config.radius().sample(random);


            ChunkPos chunkPos = context.chunkPos();
            int blockX = chunkPos.getBlockX(random.nextInt(16));
            int blockZ = chunkPos.getBlockZ(random.nextInt(16));

            BlockPos origin = new BlockPos(blockX, chunkGenerator.getBaseHeight(blockX, blockZ, Heightmap.Types.OCEAN_FLOOR_WG, context.heightAccessor(), randomState) - ((int) (radius * 0.7)), blockZ);
            RandomSource randomSource = RandomSource.create(context.seed() + origin.asLong());
            RandomSource angleRandom = RandomSource.create(context.seed() + (origin.getX() << 2L) + (origin.getZ() << 2L));

            float pitch = this.config.pitch().sample(random);
            float yaw = this.config.yaw().sample(angleRandom);


            Long2ObjectOpenHashMap<Pair<Pair<MutableBoolean, MutableBoolean>, BoundingBox>> chunks = new Long2ObjectOpenHashMap<>();

            Quaterniond quaternion = new Quaterniond();
            quaternion.rotateY(Math.toRadians(yaw));
            quaternion.rotateX(Math.toRadians(pitch));


            int[] heightCache = createHeightCache(radius, origin);

            generateFromCache(radius, heightCache, origin, quaternion, false, (rotatedPos, unRotatedPos) -> {
                Pair<Pair<MutableBoolean, MutableBoolean>, BoundingBox> entry = chunks.computeIfAbsent(ChunkPos.asLong(rotatedPos), aLong -> Pair.of(Pair.of(new MutableBoolean(true), new MutableBoolean(false)), new BoundingBox(rotatedPos)));
                entry.right().encapsulate(rotatedPos);
                entry.left().left().setTrue();
                return true;
            });

            generateFromCache(radius, heightCache, origin, quaternion, true, (rotatedPos, unRotatedPos) -> {
                Pair<Pair<MutableBoolean, MutableBoolean>, BoundingBox> entry = chunks.computeIfAbsent(ChunkPos.asLong(rotatedPos), aLong -> Pair.of(Pair.of(new MutableBoolean(false), new MutableBoolean(true)), new BoundingBox(rotatedPos)));
                entry.right().encapsulate(rotatedPos);
                entry.left().right().setTrue();
                return true;
            });

            chunks.long2ObjectEntrySet().fastForEach(boundingBoxEntry ->
                    piecesBuilder.addPiece(new SharpenedRockPiece(origin, radius, boundingBoxEntry.getValue().left().right().isTrue(), boundingBoxEntry.getValue().left().left().isTrue(), pitch, yaw, heightCache, 0, boundingBoxEntry.getValue().right()))
            );
        });
    }


    public static int[] createHeightCache(int radius, BlockPos origin) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        int diameter = radius * 2;
        int[] heightCache = new int[diameter * diameter];

        for (double x = -radius; x <= radius; x += 0.5F) {
            for (double z = -radius; z <= radius; z += 0.5F) {

                mutableBlockPos.setWithOffset(origin, (int) x, 0, (int) z);

                int idx = ((int) x + radius) + ((int) z + radius) * radius;

                if (mutableBlockPos.closerThan(origin, radius)) {
                    double height = getHeight(radius, origin, mutableBlockPos);
                    heightCache[idx] = (int) height;
                }
            }
        }

        return heightCache;
    }

    public static void generateFromCache(int radius, int[] heights, BlockPos origin, Quaterniond quaternion, boolean reverse, BiPredicate<BlockPos, BlockPos> action) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        if (reverse) {
            quaternion.rotateX(Math.toRadians(180F));
            quaternion.rotateY(Math.toRadians(180F));
        }
        Vector3d vector3d = new Vector3d(0, 0, 0);
        for (double x = -radius; x <= radius; x += 0.5) {
            for (double z = -radius; z <= radius; z += 0.5) {


                BlockPos.MutableBlockPos mutable1 = new BlockPos.MutableBlockPos();
                int height = heights[(((int) x) + radius) + (((int) z) + radius) * radius];
                for (int y = 0; y < height; y++) {
                    mutableBlockPos.setWithOffset(origin, (int) x, 0, (int) z);
                    if (mutableBlockPos.closerThan(origin, radius)) {
                        vector3d.set(x, y, z);
                        Vector3d transformed = quaternion.transform(vector3d);
                        mutable1.setWithOffset(origin, (int) Math.round(transformed.x()), (int) Math.round(transformed.y()), (int) Math.round(transformed.z()));
                        if (!action.test(mutable1, mutableBlockPos.setY(y))) {
                            break;
                        }
                    }
                }
            }
        }
        // Back to normal
        if (reverse) {
            quaternion.rotateY(Math.toRadians(180F));
            quaternion.rotateX(Math.toRadians(180F));
        }
    }

    private static double getHeight(int radius, BlockPos origin, BlockPos.MutableBlockPos mutableBlockPos) {
        double noise = NOISE.noise(mutableBlockPos.getX() * 0.1, 0, mutableBlockPos.getZ() * 0.1);

        double factor = (origin.distSqr(mutableBlockPos) / Mth.square(radius));
        double amplifier = BlendingFunction.EaseInCirc.INSTANCE.apply(1 - factor, 15, 100);
        double noiseAmp = noise * (amplifier * 2);
        return amplifier + noiseAmp;
    }

    @Override
    public @NotNull StructureType<?> type() {
        return BWGStructureTypes.SHARPENED_ROCK.get();
    }
}
