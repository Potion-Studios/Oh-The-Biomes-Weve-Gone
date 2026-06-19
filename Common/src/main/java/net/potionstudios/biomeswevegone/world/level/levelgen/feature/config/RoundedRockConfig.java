package net.potionstudios.biomeswevegone.world.level.levelgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.potionstudios.biomeswevegone.math.BlendingFunction;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.potionstudios.biomeswevegone.world.level.levelgen.CheckedBlockPlacement;

public record RoundedRockConfig(
        IntProvider radius,
        IntProvider height,
        FloatProvider noiseFrequency,
        CheckedBlockPlacement checkedBlockPlacement,
        WeightedList<BlendingFunction> blendFunction) implements FeatureConfiguration {

    public static final Codec<RoundedRockConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    IntProvider.POSITIVE_CODEC.fieldOf("radius").forGetter(RoundedRockConfig::radius),
                    IntProvider.POSITIVE_CODEC.fieldOf("height").forGetter(RoundedRockConfig::height),
                    FloatProvider.CODEC.fieldOf("noise_frequency").forGetter(RoundedRockConfig::noiseFrequency),
                    CheckedBlockPlacement.CODEC.fieldOf("block_placement").forGetter(RoundedRockConfig::checkedBlockPlacement),
                    WeightedList.codec(BlendingFunction.CODEC).fieldOf("blending_function").forGetter(RoundedRockConfig::blendFunction)
            ).apply(instance, RoundedRockConfig::new)
    );
}