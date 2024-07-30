package net.potionstudios.biomeswevegone.forge;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.potionstudios.biomeswevegone.BiomesWeveGone;

import java.util.function.Supplier;

/**
 * @author Joseph T. McQuigg
 * This is the forge implementation of the RegistrationHandler interface.
 * This class is responsible for registering all the items, blocks, entities, etc.
 * @see net.potionstudios.biomeswevegone.RegistrationHandler
 * @see ForgeRegistries
 */
public class RegistrationHandlerImpl {

    private static final DeferredRegister<Codec<? extends SurfaceRules.RuleSource>> MATERIAL_RULES = DeferredRegister.create(Registries.MATERIAL_RULE, BiomesWeveGone.MOD_ID);

    public static Supplier<Codec<? extends SurfaceRules.RuleSource>> registerMaterialRule(String id, Supplier<Codec<? extends SurfaceRules.RuleSource>> codec) {
        return MATERIAL_RULES.register(id, codec);
    }

    private static final DeferredRegister<BlockPredicateType<?>> BLOCK_PREDICATE_TYPE = DeferredRegister.create(Registries.BLOCK_PREDICATE_TYPE, BiomesWeveGone.MOD_ID);

    public static <P extends BlockPredicate> Supplier<BlockPredicateType<P>> registerBlockPredicate(String id, Supplier<Codec<P>> codec) {
        return BLOCK_PREDICATE_TYPE.register(id, () -> codec::get);
    }

    public static void init(IEventBus bus) {
        MATERIAL_RULES.register(bus);
        BLOCK_PREDICATE_TYPE.register(bus);
    }

}
