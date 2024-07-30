package net.potionstudios.biomeswevegone;

import com.mojang.serialization.Codec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;

import java.util.function.Supplier;

/**
 * This class handles the registration of all content using arch's expect platform
 * @author Joseph T. McQuigg
 * @see ExpectPlatform
 */
public class RegistrationHandler {

    /**
     * Registers a custom rule source with the specified id and codec
     * @param id The id/name of the rule source
     * @param codec The codec of the rule source
     * @return Supplier of the Codec
     */
    @ExpectPlatform
    public static Supplier<Codec<? extends SurfaceRules.RuleSource>> registerMaterialRule(String id, Supplier<Codec<? extends SurfaceRules.RuleSource>> codec) {
        throw new AssertionError("Failed to register Biomes We've Gone Material Rule");
    }

    /**
     * Registers a custom block predicate type with the specified id and codec
     * @param id The id/name of the block predicate type
     * @param codec The codec of the block predicate type
     * @return Supplier of the BlockStateProviderType
     */
    @ExpectPlatform
    public static <P extends BlockPredicate> Supplier<BlockPredicateType<P>> registerBlockPredicate(String id, Supplier<Codec<P>> codec) {
        throw new AssertionError("Failed to register Biomes We've Gone Block Predicate");
    }

    @ExpectPlatform
    public static Supplier<Holder.Reference<SoundEvent>> registerSoundEventHolder(String name) {
        throw new AssertionError("Failed to register Biomes We've Gone Sound");
    }
}
