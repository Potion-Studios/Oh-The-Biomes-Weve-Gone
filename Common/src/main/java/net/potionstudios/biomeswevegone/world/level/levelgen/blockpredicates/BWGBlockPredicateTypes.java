package net.potionstudios.biomeswevegone.world.level.levelgen.blockpredicates;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;

import java.util.function.Supplier;

public class BWGBlockPredicateTypes {

    public static final Supplier<BlockPredicateType<RandomChancePredicate>> RANDOM_CHANCE = register("random_chance", () -> RandomChancePredicate.CODEC);

    private static <B extends BlockPredicate> Supplier<BlockPredicateType<B>> register(String id, Supplier<MapCodec<B>> codec) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.BLOCK_PREDICATE_TYPE, id, () -> codec::get);
    }

    public static void blockPredicateTypes() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Block Predicate");
    }
}
