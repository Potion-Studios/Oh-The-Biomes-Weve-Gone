package net.potionstudios.biomeswevegone.fabric;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.potionstudios.biomeswevegone.BiomesWeveGone;

import java.util.function.Supplier;

/**
 * @author Joseph T. McQuigg
 * This is the fabric implementation of the RegistrationHandler interface.
 * This class is responsible for registering all the items, blocks, entities, etc.
 * @see net.potionstudios.biomeswevegone.RegistrationHandler
 * @see BuiltInRegistries
 */
public class RegistrationHandlerImpl {

    public static Supplier<Codec<? extends SurfaceRules.RuleSource>> registerMaterialRule(String id, Supplier<Codec<? extends SurfaceRules.RuleSource>> codec) {
        Codec<? extends SurfaceRules.RuleSource> codec1 = Registry.register(BuiltInRegistries.MATERIAL_RULE, BiomesWeveGone.id(id), codec.get());
        return () -> codec1;
    }


    public static <P extends BlockPredicate> Supplier<BlockPredicateType<P>> registerBlockPredicate(String id, Supplier<Codec<P>> codec) {
        BlockPredicateType<P> blockPredicateType = Registry.register(BuiltInRegistries.BLOCK_PREDICATE_TYPE, BiomesWeveGone.id(id), codec::get);
        return () -> blockPredicateType;
    }


}
