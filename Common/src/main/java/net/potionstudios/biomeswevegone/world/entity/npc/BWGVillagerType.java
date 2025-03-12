package net.potionstudios.biomeswevegone.world.entity.npc;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;

import java.util.function.BiConsumer;

public class BWGVillagerType {

	public static void setVillagerBiomes(BiConsumer<ResourceKey<Biome>, VillagerType> consumer) {
		consumer.accept(BWGBiomes.MOJAVE_DESERT, VillagerType.DESERT);
		consumer.accept(BWGBiomes.WINDSWEPT_DESERT, VillagerType.DESERT);
		consumer.accept(BWGBiomes.TROPICAL_RAINFOREST, VillagerType.JUNGLE);
		consumer.accept(BWGBiomes.WEEPING_WITCH_FOREST, VillagerType.TAIGA);
		consumer.accept(BWGBiomes.WHITE_MANGROVE_MARSHES, VillagerType.SWAMP);
		consumer.accept(BWGBiomes.BAYOU, VillagerType.SWAMP);
		consumer.accept(BWGBiomes.CYPRESS_SWAMPLANDS, VillagerType.SWAMP);
		consumer.accept(BWGBiomes.PALE_BOG, VillagerType.SWAMP);
	}
}
