package net.potionstudios.biomeswevegone.world.level.levelgen.biome.modifiers;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.tags.BWGBiomeTags;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.placed.BWGOverworldTreePlacedFeatures;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.placed.BWGVanillaPlacedFeatures;

import java.util.Map;

public class BWGBiomeModifiers {
	public static final Map<ResourceLocation, BWGBiomeModifier> BIOME_MODIFIERS_FACTORIES = new Reference2ObjectOpenHashMap<>();

	private static void registerModifier(String id, ResourceKey<PlacedFeature> feature, TagKey<Biome> biomes, GenerationStep.Decoration step) {
		BWGBiomeModifier modifier = new BWGBiomeModifier(feature, biomes, step);
		BIOME_MODIFIERS_FACTORIES.put(BiomesWeveGone.id(id), modifier);
	}

	public record BWGBiomeModifier(ResourceKey<PlacedFeature> feature, TagKey<Biome> biomes, GenerationStep.Decoration step) { }

	public static void init() {
		BiomesWeveGone.LOGGER.info("Creating and Registering BWG Biome Modifiers for Vanilla Biomes");
		registerModifier("vanilla/flower_plains", BWGVanillaPlacedFeatures.FLOWER_PLAINS, BiomeTags.HAS_VILLAGE_PLAINS, GenerationStep.Decoration.VEGETAL_DECORATION);
		registerModifier("vanilla/forest_flowers", BWGVanillaPlacedFeatures.FOREST_FLOWERS, BiomeTags.IS_FOREST, GenerationStep.Decoration.VEGETAL_DECORATION);
		registerModifier("vanilla/beach/palm_trees", BWGOverworldTreePlacedFeatures.PALM_TREES, BWGBiomeTags.VanillaOnlyTags.BEACH, GenerationStep.Decoration.VEGETAL_DECORATION);
	}
}


