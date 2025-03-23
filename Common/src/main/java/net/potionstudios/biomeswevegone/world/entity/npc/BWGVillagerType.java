package net.potionstudios.biomeswevegone.world.entity.npc;

import net.minecraft.world.entity.npc.VillagerType;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;

public class BWGVillagerType {

	public static void setVillagerBWGBiomes() {
		VillagerType.BY_BIOME.put(BWGBiomes.MOJAVE_DESERT, VillagerType.DESERT);
		VillagerType.BY_BIOME.put(BWGBiomes.WINDSWEPT_DESERT, VillagerType.DESERT);
		VillagerType.BY_BIOME.put(BWGBiomes.TROPICAL_RAINFOREST, VillagerType.JUNGLE);
		VillagerType.BY_BIOME.put(BWGBiomes.WEEPING_WITCH_FOREST, VillagerType.TAIGA);
		VillagerType.BY_BIOME.put(BWGBiomes.WHITE_MANGROVE_MARSHES, VillagerType.SWAMP);
		VillagerType.BY_BIOME.put(BWGBiomes.BAYOU, VillagerType.SWAMP);
		VillagerType.BY_BIOME.put(BWGBiomes.CYPRESS_SWAMPLANDS, VillagerType.SWAMP);
	}
}
