package net.potionstudios.biomeswevegone.world.level.levelgen.surfacerules;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.RegistrationHandlerA;

import java.util.function.Supplier;

public class BWGRuleSources {

	public static WeightedRuleSource weightedRuleSource(SimpleWeightedRandomList<SurfaceRules.RuleSource> ruleSource) {
		return new WeightedRuleSource(ruleSource);
	}

	private static void register(String id, Supplier<Codec<? extends SurfaceRules.RuleSource>> codec) {
		RegistrationHandlerA.REGISTRATION.register(BuiltInRegistries.MATERIAL_RULE, id, codec);
	}

	public static void ruleSources() {
		BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Custom Surface Rules");
		register("state_provider", WeightedRuleSource.CODEC::codec);
		register("between_repeating_noise_range", BetweenRepeatingNoiseRange.CODEC::codec);
		register("bands", BandsRuleSource.CODEC::codec);
	}
}
