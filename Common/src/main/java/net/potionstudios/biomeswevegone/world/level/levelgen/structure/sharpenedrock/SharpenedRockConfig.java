package net.potionstudios.biomeswevegone.world.level.levelgen.structure.sharpenedrock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;

public record SharpenedRockConfig(IntProvider radius, FloatProvider pitch, FloatProvider yaw) {

        public static final Codec<SharpenedRockConfig> CODEC = RecordCodecBuilder.create(builder ->
                builder.group(
                        IntProviders.CODEC.fieldOf("radius").forGetter(sharpenedRockConfig -> sharpenedRockConfig.radius),
                        FloatProviders.CODEC.fieldOf("pitch").forGetter(sharpenedRockConfig -> sharpenedRockConfig.pitch),
                        FloatProviders.CODEC.fieldOf("yaw").forGetter(sharpenedRockConfig -> sharpenedRockConfig.yaw)
                ).apply(builder, SharpenedRockConfig::new)
        );
    }