package net.potionstudios.biomeswevegone.world.level.levelgen.surfacerules;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;

import java.util.function.Supplier;

public class BWGRuleSources {

	public static WeightedRuleSource weightedRuleSource(SimpleWeightedRandomList<SurfaceRules.RuleSource> ruleSource) {
		return new WeightedRuleSource(ruleSource);
	}

	private static void register(String id, Supplier<MapCodec<? extends SurfaceRules.RuleSource>> codec) {
		PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.MATERIAL_RULE, id, codec);
	}

	public static void ruleSources() {
		BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Custom Surface Rules");
		register("state_provider", WeightedRuleSource.CODEC::codec);
		register("between_repeating_noise_range", BetweenRepeatingNoiseRange.CODEC::codec);
		register("bands", BandsRuleSource.CODEC::codec);
	}
}
