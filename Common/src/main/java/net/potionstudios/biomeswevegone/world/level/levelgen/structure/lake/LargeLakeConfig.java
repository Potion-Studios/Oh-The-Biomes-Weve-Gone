package net.potionstudios.biomeswevegone.world.level.levelgen.structure.lake;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record LargeLakeConfig(HolderSet<PlacedFeature> features, IntProvider depth, IntProvider lakeRadius) {

    public static final Codec<LargeLakeConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(LargeLakeConfig::features),
                    IntProvider.POSITIVE_CODEC.fieldOf("depth").forGetter(LargeLakeConfig::depth),
                    IntProvider.POSITIVE_CODEC.fieldOf("lake_radius").forGetter(LargeLakeConfig::lakeRadius)
            ).apply(instance, LargeLakeConfig::new)
    );
}
