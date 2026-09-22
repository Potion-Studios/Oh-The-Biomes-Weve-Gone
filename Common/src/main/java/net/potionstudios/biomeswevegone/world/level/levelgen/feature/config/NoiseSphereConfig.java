package net.potionstudios.biomeswevegone.world.level.levelgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.potionstudios.biomeswevegone.world.level.levelgen.CheckedBlockPlacement;

public record NoiseSphereConfig(IntProvider radius,
                                FloatProvider noiseFrequency,
                                CheckedBlockPlacement checkedBlockPlacement) implements FeatureConfiguration {

    public static final Codec<NoiseSphereConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    IntProviders.POSITIVE_CODEC.fieldOf("radius").forGetter(NoiseSphereConfig::radius),
                    FloatProviders.CODEC.fieldOf("noise_frequency").forGetter(NoiseSphereConfig::noiseFrequency),
                    CheckedBlockPlacement.CODEC.fieldOf("block_placement").forGetter(NoiseSphereConfig::checkedBlockPlacement)
            ).apply(instance, NoiseSphereConfig::new)
    );
}