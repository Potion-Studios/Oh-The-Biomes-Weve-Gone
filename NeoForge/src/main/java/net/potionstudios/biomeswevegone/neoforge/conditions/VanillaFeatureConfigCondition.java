package net.potionstudios.biomeswevegone.neoforge.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.potionstudios.biomeswevegone.config.ConfigUtils;
import net.potionstudios.biomeswevegone.config.configs.BWGWorldGenConfig;
import org.jetbrains.annotations.NotNull;

public record VanillaFeatureConfigCondition(ResourceKey<PlacedFeature> feature) implements ICondition {
	public static final MapCodec<VanillaFeatureConfigCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ResourceKey.codec(Registries.PLACED_FEATURE).fieldOf("feature").forGetter(VanillaFeatureConfigCondition::feature)
	).apply(instance, VanillaFeatureConfigCondition::new));

	@Override
	public boolean test(@NotNull IContext iContext) {
		BWGWorldGenConfig config = BWGWorldGenConfig.INSTANCE;
		return config.vanilla_additions.value() && config.individual_vanilla_additions.getOrDefault(feature.location(), ConfigUtils.CommentValue.of("", true)).value();
	}

	@Override
	public @NotNull MapCodec<? extends ICondition> codec() {
		return CODEC;
	}
}
