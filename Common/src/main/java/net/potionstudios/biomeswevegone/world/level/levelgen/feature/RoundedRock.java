package net.potionstudios.biomeswevegone.world.level.levelgen.feature;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import corgitaco.corgilib.math.blendingfunction.BlendingFunction;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.config.RoundedRockConfig;

public class RoundedRock extends Feature<RoundedRockConfig> {
    public RoundedRock(Codec<RoundedRockConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<RoundedRockConfig> context) {
        RandomSource random = context.random();
        ImprovedNoise improvedNoise = new ImprovedNoise(random);
        RoundedRockConfig config = context.config();

        BlockPos origin = context.origin();

        int rawRadius = config.radius().sample(random);

        int height = config.height().sample(random);
        float frequency = config.noiseFrequency().sample(random);

        BlendingFunction blendingFunction = config.blendFunction().getRandomValue(random).orElseThrow();

        LongSet cached = new LongOpenHashSet();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int y = -5; y < height; y++) {
            double yDelta = 1 - ((double) y) / ((double) height);

            if (yDelta >= 0 && yDelta <= 1.0) {
                yDelta = blendingFunction.apply(yDelta);
            }
            double radius = rawRadius * yDelta;
            for (double x = -radius; x <= radius; x++) {
                for (double z = -radius; z <= radius; z++) {
                    mutableBlockPos.setWithOffset(origin, (int) x, y, (int) z);

                    if (mutableBlockPos.closerThan(origin.atY(mutableBlockPos.getY()), radius)) {
                        double normalizedNoise = (improvedNoise.noise(mutableBlockPos.getX() * frequency, mutableBlockPos.getY() * frequency, mutableBlockPos.getZ() * frequency) + 1) * 0.5F;

                        double localRadius = Mth.clampedLerp(radius * 0.5, radius, normalizedNoise);

                        if (mutableBlockPos.closerThan(origin.atY(mutableBlockPos.getY()), localRadius)) {
                            cached.add(mutableBlockPos.asLong());
                        }
                    }
                }
            }
        }

        for (Pair<BlockPredicate, BlockStateProvider> blockPlacement : config.checkedBlockPlacement().blockPlacement()) {
            cached.forEach(pos -> {
                mutableBlockPos.set(pos);
                if (blockPlacement.getFirst().test(context.level(), mutableBlockPos)) {
                    context.level().setBlock(mutableBlockPos, blockPlacement.getSecond().getState(random, mutableBlockPos), 2);
                }
            });
        }

        return true;
    }

    public static double easeInCirc(double x) {
        return 1.0 - Math.sqrt(1.0 - Math.pow(x, 1.1));
    }
}
