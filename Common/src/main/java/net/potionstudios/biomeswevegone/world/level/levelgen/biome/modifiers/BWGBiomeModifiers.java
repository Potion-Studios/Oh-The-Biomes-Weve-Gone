package net.potionstudios.biomeswevegone.world.level.levelgen.biome.modifiers;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.placed.BWGOverworldTreePlacedFeatures;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.placed.BWGVanillaPlacedFeatures;

import java.util.Map;

public class BWGBiomeModifiers {
	public static final Map<ResourceLocation, BWGBiomeModifier> BIOME_MODIFIERS_FACTORIES = new Reference2ObjectOpenHashMap<>();

	@SafeVarargs
	private static void registerModifier(String id, ResourceKey<PlacedFeature> feature, GenerationStep.Decoration step, ResourceKey<Biome>... biomes) {
		BWGBiomeModifier modifier = new BWGBiomeModifier(feature, step, biomes);
		BIOME_MODIFIERS_FACTORIES.put(BiomesWeveGone.id(id), modifier);
	}

	public record BWGBiomeModifier(ResourceKey<PlacedFeature> feature, GenerationStep.Decoration step, ResourceKey<Biome>... biomes) {
		@SafeVarargs
		public BWGBiomeModifier {}
	}

	public static void init() {
		BiomesWeveGone.LOGGER.info("Creating and Registering BWG Biome Modifiers for Vanilla Biomes");
		registerModifier("vanilla/flower_plains", BWGVanillaPlacedFeatures.FLOWER_PLAINS, GenerationStep.Decoration.VEGETAL_DECORATION, Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS);
		registerModifier("vanilla/forest_flowers", BWGVanillaPlacedFeatures.FOREST_FLOWERS, GenerationStep.Decoration.VEGETAL_DECORATION, Biomes.FOREST, Biomes.DARK_FOREST);
		registerModifier("vanilla/beach/palm_trees", BWGOverworldTreePlacedFeatures.PALM_TREES, GenerationStep.Decoration.VEGETAL_DECORATION, Biomes.BEACH);
	}
}


