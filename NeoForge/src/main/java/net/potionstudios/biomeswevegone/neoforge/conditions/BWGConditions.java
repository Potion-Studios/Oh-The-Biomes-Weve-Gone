package net.potionstudios.biomeswevegone.neoforge.conditions;

import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.potionstudios.biomeswevegone.BiomesWeveGone;

import java.util.function.Supplier;

public class BWGConditions {
	public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS =
			DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, BiomesWeveGone.MOD_ID);

	public static final Supplier<MapCodec<VanillaFeatureConfigCondition>> VANILLA_FEATURE_CONFIG_CONDITION =
			CONDITION_CODECS.register("vanilla_feature_config", () -> VanillaFeatureConfigCondition.CODEC);

	public static void conditions(IEventBus bus) {
		CONDITION_CODECS.register(bus);
	}
}
