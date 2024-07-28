package net.potionstudios.biomeswevegone.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import corgitaco.corgilib.math.blendingfunction.BlendingFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;

public class SharpenedRockFeature extends Feature<NoneFeatureConfiguration> {
    public static final ImprovedNoise NOISE = new ImprovedNoise(new XoroshiroRandomSource(100L));


    public SharpenedRockFeature(Codec<NoneFeatureConfiguration> noneFeatureConfigurationCodec) {
        super(noneFeatureConfigurationCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {

        BlockPos origin = featurePlaceContext.origin();

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();


        WorldGenLevel level = featurePlaceContext.level();
        int radius = UniformInt.of(5, 12).sample(featurePlaceContext.random());

        for (int x = -radius; x <= radius; x ++) {
            for (int z = -radius; z <= radius; z ++) {

                mutableBlockPos.setWithOffset(origin, x, 0, z);

                double noise = NOISE.noise(mutableBlockPos.getX() * 0.1, 0, mutableBlockPos.getZ() * 0.1);

                double factor = origin.distSqr(mutableBlockPos) / Mth.square(radius);
                double amplifier = BlendingFunction.EaseInCirc.INSTANCE.apply(1 - factor, 0, 75);

                BlockPos.MutableBlockPos mutable1 = new BlockPos.MutableBlockPos();
                int minY = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, mutableBlockPos.getX(), mutableBlockPos.getZ()) - 1;
                for (int y = minY; y < minY + amplifier + Math.max(0, (noise * (amplifier * 2))); y++) {
                    if (mutableBlockPos.closerThan(origin, radius)) {
                        mutable1.set(origin.getX() + x, y, origin.getZ() + z);

                        BlockState state = BWGBlocks.PACKED_BOREALIS_ICE.get().defaultBlockState();
                        level.setBlock(mutable1, state, 2);
                    }
                }
            }
        }
        return true;
    }
}