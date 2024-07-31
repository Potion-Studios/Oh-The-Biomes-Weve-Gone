package net.potionstudios.biomeswevegone.world.level.levelgen.blockpredicates;

import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;

import java.util.function.Supplier;

public class BWGBlockPredicateTypes {

    public static final Supplier<BlockPredicateType<RandomChancePredicate>> RANDOM_CHANCE = PlatformHandler.PLATFORM_HANDLER.registerBlockPredicate("random_chance", () -> RandomChancePredicate.CODEC);

    public static void blockPredicateTypes() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Block Predicate");
    }
}
