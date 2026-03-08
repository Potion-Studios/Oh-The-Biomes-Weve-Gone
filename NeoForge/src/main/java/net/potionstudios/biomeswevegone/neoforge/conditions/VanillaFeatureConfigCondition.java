package net.potionstudios.biomeswevegone.neoforge.conditions;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.potionstudios.biomeswevegone.config.configs.BWGWorldGenConfig;
import org.jspecify.annotations.NonNull;

public record VanillaFeatureConfigCondition() implements ICondition {
	public static final VanillaFeatureConfigCondition INSTANCE = new VanillaFeatureConfigCondition();

	public static final MapCodec<VanillaFeatureConfigCondition> CODEC = MapCodec.unit(new VanillaFeatureConfigCondition());

	@Override
	public boolean test(@NonNull IContext iContext) {
		return BWGWorldGenConfig.INSTANCE.get().vanillaAdditions();
	}

	@Override
	public @NonNull MapCodec<? extends ICondition> codec() {
		return CODEC;
	}
}
