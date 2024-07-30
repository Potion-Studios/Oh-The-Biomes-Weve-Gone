package net.potionstudios.biomeswevegone;

import com.mojang.serialization.Codec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.potionstudios.biomeswevegone.world.level.block.custom.BWGFarmLandBlock;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * This class handles the registration of all content using arch's expect platform
 * @author Joseph T. McQuigg
 * @see ExpectPlatform
 */
public class RegistrationHandler {

    /**
     * Registers a wood type with the specified id and block set type
     * @see WoodType
     * @param id The id/name of the wood type
     * @param setType The block set type of the wood type
     * @return WoodType
     */
    @ExpectPlatform
    public static WoodType createWoodType(String id, @NotNull BlockSetType setType) {
        throw new AssertionError("Failed to register Biomes We've Gone Wood Types");
    }

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
     * Registers a custom block state provider with the specified id and codec
     * @param id The id/name of the block state provider
     * @param codec The codec of the block state provider
     * @return Supplier of the BlockStateProviderType
     */
    @ExpectPlatform
    public static <P extends BlockStateProvider> Supplier<BlockStateProviderType<P>> registerStateProvider(String id, Supplier<Codec<P>> codec) {
        throw new AssertionError("Failed to register Biomes We've Gone State Providers");
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

    /**
     * Registers a custom tree decorator type with the specified id and codec
     * @param id The id/name of the tree decorator type
     * @param codec The codec of the tree decorator type
     * @return Supplier of the TreeDecoratorType
     */
    @ExpectPlatform
    public static <P extends TreeDecorator> Supplier<TreeDecoratorType<P>> registerTreeDecoratorType(String id, Supplier<Codec<P>> codec) {
        throw new AssertionError("Failed to register Biomes We've Gone Tree Decorator");
    }

    @ExpectPlatform
    public static Supplier<Holder.Reference<SoundEvent>> registerSoundEventHolder(String name) {
        throw new AssertionError("Failed to register Biomes We've Gone Sound");
    }

    @ExpectPlatform
    public static Supplier<BWGFarmLandBlock> bwgFarmLandBlock(Supplier<Block> dirt) {
        throw new AssertionError("Failed to register Biomes We've Gone blocks");
    }
}
