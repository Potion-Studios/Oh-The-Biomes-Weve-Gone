package net.potionstudios.biomeswevegone.world.level.levelgen.feature;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.config.NoiseSphereConfig;

public class NoiseSphere extends Feature<NoiseSphereConfig> { // TODO: Add Config file
    public NoiseSphere(Codec<NoiseSphereConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoiseSphereConfig> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        NoiseSphereConfig config = context.config();

        RandomSource random = context.random();
        int radius = config.radius().sample(random);
        float freq = config.noiseFrequency().sample(random);

        ImprovedNoise noise = new ImprovedNoise(random);

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        LongSet cache = new LongOpenHashSet();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    mutableBlockPos.setWithOffset(origin, x, y, z);
                    if (origin.closerThan(mutableBlockPos, radius)) {
                        double noiseDelta = (noise.noise(mutableBlockPos.getX() * freq, mutableBlockPos.getY() * freq, mutableBlockPos.getZ() * freq) + 1) * 0.5;

                        double localRadius = Mth.clampedLerp(radius * 0.5, radius, noiseDelta);

                        if (origin.closerThan(mutableBlockPos, localRadius)) {
                            cache.add(mutableBlockPos.asLong());
                        }
                    }
                }
            }
        }

        for (Pair<BlockPredicate, BlockStateProvider> blockPlacement : config.checkedBlockPlacement().blockPlacement()) {
            cache.forEach(pos -> {
                mutableBlockPos.set(pos);
                if (blockPlacement.getFirst().test(level, mutableBlockPos)) {
                    level.setBlock(mutableBlockPos, blockPlacement.getSecond().getState(random, mutableBlockPos), 2);
                }
            });
        }
        return true;
    }
}
