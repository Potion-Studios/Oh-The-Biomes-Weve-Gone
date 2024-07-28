package net.potionstudios.biomeswevegone.world.level.levelgen.structure.sharpenedrock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;

public record SharpenedRockConfig(IntProvider radius, FloatProvider pitch, FloatProvider yaw) {

        public static final Codec<SharpenedRockConfig> CODEC = RecordCodecBuilder.create(builder ->
                builder.group(
                        IntProvider.CODEC.fieldOf("radius").forGetter(sharpenedRockConfig -> sharpenedRockConfig.radius),
                        FloatProvider.CODEC.fieldOf("pitch").forGetter(sharpenedRockConfig -> sharpenedRockConfig.pitch),
                        FloatProvider.CODEC.fieldOf("yaw").forGetter(sharpenedRockConfig -> sharpenedRockConfig.yaw)
                ).apply(builder, SharpenedRockConfig::new)
        );
    }