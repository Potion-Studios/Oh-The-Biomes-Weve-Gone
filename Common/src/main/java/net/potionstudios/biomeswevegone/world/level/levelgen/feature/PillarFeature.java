package net.potionstudios.biomeswevegone.world.level.levelgen.feature;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.potionstudios.biomeswevegone.world.level.levelgen.CheckedBlockPlacement;

public class PillarFeature extends Feature<PillarFeature.Config> {
    public PillarFeature(Codec<PillarFeature.Config> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<PillarFeature.Config> context) {

        RandomSource random = context.random();
        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();

        Config config = context.config();
        int height = config.height.sample(random);

        int radius = config.radius.sample(random);
        double frequency = config.noisefreq.sample(random);

        double minRadiusScale = config.minRadiusScale.sample(random);

        DistanceTestType tester = config.distanceTestType.getRandomValue(random).orElseThrow();

        ImprovedNoise noise = new ImprovedNoise(random);

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        LongSet cache = new LongOpenHashSet();

        for (int xOffset = -radius; xOffset <= radius; xOffset++) {
            for (int zOffset = -radius; zOffset <= radius; zOffset++) {
                mutableBlockPos.setWithOffset(origin, xOffset, 0, zOffset);
                if (tester.distanceTester.withinDistance(origin, mutableBlockPos, radius)) {
                    int heightmap = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, mutableBlockPos.getX(), mutableBlockPos.getZ());

                    int radius1 = radius;
                    int maxY = origin.getY() + height;
                    for (int worldY = heightmap - 5; worldY <= maxY; worldY++) {
                        mutableBlockPos.set(origin.getX() + xOffset, worldY, origin.getZ() + zOffset);

                        double pillarNoise = (noise.noise(mutableBlockPos.getX() * frequency, mutableBlockPos.getY() * frequency, mutableBlockPos.getZ() * frequency) + 1) * 0.5; // 0-1 range
                        double localRadius = Mth.clampedLerp(radius1 * minRadiusScale, radius1, pillarNoise);

                        if (tester.distanceTester.withinDistance(origin.atY(worldY), mutableBlockPos, localRadius)) {
                            cache.add(mutableBlockPos.asLong());
                        }
                    }
                }
            }
        }

        for (Pair<BlockPredicate, BlockStateProvider> blockPlacement : config.checkedBlockPlacement.blockPlacement()) {
            cache.forEach(pos -> {
                mutableBlockPos.set(pos);

                if (blockPlacement.getFirst().test(level, mutableBlockPos)) {
                  level.setBlock(mutableBlockPos, blockPlacement.getSecond().getState(random, mutableBlockPos), 2);
                }
            });
        }
        UniformInt vineLengthSampler = UniformInt.of(1, 10);
        int vineLength = vineLengthSampler.sample(random);
        cache.forEach(pos -> {
            mutableBlockPos.set(pos);
            Direction.Plane horizontal = Direction.Plane.HORIZONTAL;



            for (Direction direction : horizontal) {

                if (random.nextDouble() < 0.1) {
                    mutableBlockPos.set(pos).move(direction);
                    for (int i = 0; i < vineLength; i++) {
                        if (level.getBlockState(mutableBlockPos).isAir() && !cache.contains(mutableBlockPos.asLong())) {

                            level.setBlock(mutableBlockPos, Blocks.VINE.defaultBlockState().setValue(VineBlock.getPropertyForFace(direction.getOpposite()), true), 2);
                        }
                        mutableBlockPos.move(Direction.DOWN);
                    }
                }
            }
        });

        return true;
    }

    public enum DistanceTestType {
        EUCLIDEAN(BlockPos::closerThan),
        MANHATTAN(((origin, from, radius) -> origin.distManhattan(from) < radius)),
        CHEBYSHEV((origin, from, radius) -> {
            int dx = Math.abs(origin.getX() - from.getX());
            int dy = Math.abs(origin.getY() - from.getY());
            int dz = Math.abs(origin.getZ() - from.getZ());

            return Math.max(Math.max(dx, dy), dz) < radius;
        });

        public static final Codec<DistanceTestType> CODEC = Codec.STRING.xmap(DistanceTestType::valueOf, DistanceTestType::name);


        private final DistanceTester distanceTester;

        DistanceTestType(DistanceTester distanceTester) {
            this.distanceTester = distanceTester;
        }

        public DistanceTester getDistanceTester() {
            return distanceTester;
        }
    }


    @FunctionalInterface
    public interface DistanceTester {

        boolean withinDistance(BlockPos origin, BlockPos from, double radius);
    }


    public record Config(
            CheckedBlockPlacement checkedBlockPlacement,
            IntProvider height,
            IntProvider radius,
            FloatProvider noisefreq,
            FloatProvider minRadiusScale,
            SimpleWeightedRandomList<DistanceTestType> distanceTestType
    ) implements FeatureConfiguration {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        CheckedBlockPlacement.CODEC.fieldOf("block_placement").forGetter(Config::checkedBlockPlacement),
                        IntProvider.CODEC.fieldOf("height").forGetter(Config::height),
                        IntProvider.CODEC.fieldOf("radius").forGetter(Config::radius),
                        FloatProvider.CODEC.fieldOf("noise_frequency").forGetter(Config::noisefreq),
                        FloatProvider.CODEC.fieldOf("min_radius_scale").forGetter(Config::minRadiusScale),
                        SimpleWeightedRandomList.wrappedCodec(DistanceTestType.CODEC).fieldOf("distance_test_type").forGetter(Config::distanceTestType)
                ).apply(instance, Config::new)
        );
    }
}
