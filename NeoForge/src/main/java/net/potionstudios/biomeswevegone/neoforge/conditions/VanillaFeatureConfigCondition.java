package net.potionstudios.biomeswevegone.neoforge.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.potionstudios.biomeswevegone.config.configs.BWGWorldGenConfig;
import org.jspecify.annotations.NonNull;

public record VanillaFeatureConfigCondition(ResourceKey<PlacedFeature> feature) implements ICondition {
	public static final MapCodec<VanillaFeatureConfigCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ResourceKey.codec(Registries.PLACED_FEATURE).fieldOf("feature").forGetter(VanillaFeatureConfigCondition::feature)
	).apply(instance, VanillaFeatureConfigCondition::new));

	@Override
	public boolean test(@NonNull IContext iContext) {
		BWGWorldGenConfig config = BWGWorldGenConfig.INSTANCE.get();
		return config.vanillaAdditions() && config.vanillaFeatures().getOrDefault(feature, true);
	}

	@Override
	public @NonNull MapCodec<? extends ICondition> codec() {
		return CODEC;
	}
}
