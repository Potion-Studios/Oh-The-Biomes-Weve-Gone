package net.potionstudios.biomeswevegone.world.level.levelgen.blockpredicates;

import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.potionstudios.biomeswevegone.RegistrationHandler;

import java.util.function.Supplier;

public class BWGBlockPredicateTypes {


    public static final Supplier<BlockPredicateType<RandomChancePredicate>> RANDOM_CHANCE = RegistrationHandler.registerBlockPredicate("random_chance", () -> RandomChancePredicate.CODEC);

    public static void init() {
    }
}
