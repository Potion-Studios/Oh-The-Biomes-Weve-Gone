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

/**
 * Used to register BWG Biome Modifiers for Vanilla Biomes
 * @author Joseph T. McQuigg
 */
public class BWGBiomeModifiers {
	public static final Map<ResourceLocation, BWGBiomeModifier> BIOME_MODIFIERS_FACTORIES = new Reference2ObjectOpenHashMap<>();

	@SafeVarargs
	private static void registerModifierVegetalDecoration(String id, ResourceKey<PlacedFeature> feature, ResourceKey<Biome>... biomes) {
		BIOME_MODIFIERS_FACTORIES.put(BiomesWeveGone.id(id), new BWGBiomeModifier(feature, GenerationStep.Decoration.VEGETAL_DECORATION, biomes));
	}

	/**
	 * Represents a BWG Biome Modifier
	 * @param feature The feature to add
	 * @param step The generation step to add the feature to
	 * @param biomes The biomes to add the feature to
	 */
	public record BWGBiomeModifier(ResourceKey<PlacedFeature> feature, GenerationStep.Decoration step, ResourceKey<Biome>... biomes) {
		@SafeVarargs public BWGBiomeModifier {}
	}

	public static void init() {
		BiomesWeveGone.LOGGER.info("Creating and Registering BWG Biome Modifiers for Vanilla Biomes");
		registerModifierVegetalDecoration("vanilla/flower_plains", BWGVanillaPlacedFeatures.FLOWER_PLAINS, Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS);
		registerModifierVegetalDecoration("vanilla/forest_flowers", BWGVanillaPlacedFeatures.FOREST_FLOWERS, Biomes.FOREST, Biomes.DARK_FOREST);
		registerModifierVegetalDecoration("vanilla/beach/palm_trees", BWGOverworldTreePlacedFeatures.PALM_TREES, Biomes.BEACH);
	}
}


