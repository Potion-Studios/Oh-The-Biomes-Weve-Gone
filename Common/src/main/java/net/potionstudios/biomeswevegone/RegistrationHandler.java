package net.potionstudios.biomeswevegone;

import com.mojang.serialization.Codec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
     * Registers an entity with the specified parameters
     * @see EntityType
     * @param id The id/name of the entity
     * @param factory The factory for the entity
     * @param category The category of the entity
     * @param width The width of the entity
     * @param height The height of the entity
     * @return Supplier of the EntityType
     */
    @ExpectPlatform
    public static <E extends Entity> Supplier<EntityType<E>> createEntity(String id, EntityType.EntityFactory<E> factory, MobCategory category, float width, float height) {
        throw new AssertionError("Failed to register Biomes We've Gone Entities");
    }

    /**
     * Registers an entity with the specified parameters
     * @see EntityType
     * @param id The id/name of the entity
     * @param factory The factory for the entity
     * @param category The category of the entity
     * @param width The width of the entity
     * @param height The height of the entity
     * @return Supplier of the EntityType
     */
    @ExpectPlatform
    public static <E extends Entity> Supplier<EntityType<E>> createEntity(String id, EntityType.EntityFactory<E> factory, MobCategory category, float width, float height, int trackingRange) {
        throw new AssertionError("Failed to register Biomes We've Gone Entities");
    }

    /**
     * Registers a block entity with the specified parameters
     * @see BlockEntityType
     * @param key The id/name of the block entity
     * @param builder The builder for the block entity
     * @return Supplier of the BlockEntityType
     */
    @ExpectPlatform
    public static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String key, Supplier<BlockEntityType.Builder<T>> builder) {
        throw new AssertionError();
    }

    /**
     * Creates a spawn egg with the specified parameters
     * @see SpawnEggItem
     * @param id The id/name of the spawn egg
     * @param entity The entity to be spawned from the spawn egg
     * @param backgroundColor The background color of the spawn egg
     * @param highlightColor The highlight color of the spawn egg
     * @return Supplier of the SpawnEggItem
     */
    @ExpectPlatform
    public static Supplier<SpawnEggItem> createSpawnEgg(String id, Supplier<EntityType<? extends Mob>> entity, int backgroundColor, int highlightColor) {
        throw new AssertionError("Failed to register Biomes We've Gone Spawn Eggs");
    }

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
