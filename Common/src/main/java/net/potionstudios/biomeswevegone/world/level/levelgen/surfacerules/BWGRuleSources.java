package net.potionstudios.biomeswevegone.world.level.levelgen.surfacerules;

import com.mojang.serialization.Codec;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.potionstudios.biomeswevegone.RegistrationHandler;

import java.util.function.Supplier;

public class BWGRuleSources {

	public static WeightedRuleSource weightedRuleSource(SimpleWeightedRandomList<SurfaceRules.RuleSource> ruleSource) {
		return new WeightedRuleSource(ruleSource);
	}

	public static void init() {
		register("state_provider", WeightedRuleSource.CODEC::codec);
		register("between_repeating_noise_range", BetweenRepeatingNoiseRange.CODEC::codec);
		register("bands", BandsRuleSource.CODEC::codec);
	}

	private static void register(String id, Supplier<Codec<? extends SurfaceRules.RuleSource>> codec) {
		RegistrationHandler.registerMaterialRule(id, codec);
	}
}
