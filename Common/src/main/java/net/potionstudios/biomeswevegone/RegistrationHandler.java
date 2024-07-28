package net.potionstudios.biomeswevegone;

import com.mojang.serialization.Codec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.material.Fluid;
import net.potionstudios.biomeswevegone.world.level.block.custom.BWGFarmLandBlock;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
     * Registers a particle with the specified id
     * @see SimpleParticleType
     * @param id The id/name of the particle
     * @return Supplier of the SimpleParticleType
     */
    @ExpectPlatform
    public static Supplier<SimpleParticleType> registerParticle(String id) {
        throw new AssertionError("Failed to register Biomes We've Gone Particle type");
    }

    /**
     * Creates and Registers a creative tab
     * @see CreativeModeTab
     * @param name The name of the creative tab
     * @param icon The icon of the creative tab
     * @param items The items to be added to the creative tab
     * @return Supplier of the CreativeModeTab
     */
    @SafeVarargs
    @ExpectPlatform
    public static Supplier<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items) {
        throw new AssertionError("Failed to make Creative Tab");
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
     * Registers an item with the specified id
     * @see Item
     * @param id The id/name of the item
     * @param item The item to be registered
     * @return Supplier of the Item
     */
    @ExpectPlatform
    public static <I extends Item> Supplier<I> registerItem(String id, Supplier<I> item) {
        throw new AssertionError("Failed to register Biomes We've Gone Items");
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
     * Creates a mob bucket with the specified parameters
     * @see MobBucketItem
     * @param id The id/name of the mob bucket
     * @param entity The entity to be spawned from the mob bucket
     * @param fluid The fluid to be stored in the mob bucket
     * @param sound The sound to be played when the mob bucket is used
     * @return Supplier of the MobBucketItem
     */
    @ExpectPlatform
    public static Supplier<MobBucketItem> createMobBucket(String id, Supplier<EntityType<? extends Mob>> entity, Supplier<Fluid> fluid, Supplier<SoundEvent> sound) {
        throw new AssertionError("Failed to register Biomes We've Gone Mob Buckets");
    }

    /**
     * Registers a block with the specified id
     * @see Block
     * @param id The id/name of the block
     * @param block The block to be registered
     * @return Supplier of the Block
     */
    @ExpectPlatform
    public static <B extends Block> Supplier<B> registerBlock(String id, Supplier<B> block) {
        throw new AssertionError("Failed to register Biomes We've Gone blocks");
    }

    /**
     * Registers a potted block with the specified block
     * @see FlowerPotBlock
     * @param block The block to be potted
     * @return Supplier of the FlowerPotBlock
     */
    @ExpectPlatform
    public static Supplier<FlowerPotBlock> createPottedBlock(Supplier<? extends Block> block) {
        throw new AssertionError("Failed to make Potted Block");
    }

    /**
     * Registers a feature with the specified id and feature
     * @see Feature
     * @param id The id/name of the feature
     * @param feature The feature to be registered
     * @return Supplier of the Feature
     */
    @ExpectPlatform
    public static <C extends FeatureConfiguration, F extends Feature<C>> Supplier<F> registerFeature(String id, Supplier<? extends F> feature) {
        throw new AssertionError("Failed to register Biomes We've Gone Features");
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
     * Registers a custom structure piece type with the specified id and structure piece type
     * @param id The id/name of the structure piece type
     * @param structurePieceType The structure piece type to be registered
     * @return Supplier of the StructurePieceType
     */
    @ExpectPlatform
    public static <SPT extends StructurePieceType> Supplier<SPT> registerStructurePieceType(String id, Supplier<SPT> structurePieceType) {
        throw new AssertionError("Failed to register Biomes We've Gone Structure Piece Type");
    }

    /**
     * Registers a custom structure type with the specified id and structure type
     * @param id The id/name of the structure type
     * @param structureType The structure type to be registered
     * @return Supplier of the StructureType
     */
    @ExpectPlatform
    public static <S extends Structure, ST extends StructureType<S>> Supplier<ST> registerStructureType(String id, Supplier<ST> structureType) {
        throw new AssertionError("Failed to register Biomes We've Gone Structure Type");
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
     * Registers a custom poi type with the specified id and poi type
     * @param id The id/name of the poi type
     * @param poiType The poi type to be registered
     * @return Supplier of the PoiType
     */
    @ExpectPlatform
    public static <T extends PoiType> Supplier<T> registerPoiType(String id, Supplier<T> poiType) {
        throw new AssertionError("Failed to register Biomes We've Gone Poi Type");
    }

    /**
     * Registers a custom villager profession with the specified id and villager profession
     * @param id The id/name of the villager profession
     * @param villagerProfession The villager profession to be registered
     * @return Supplier of the VillagerProfession
     */
    @ExpectPlatform
    public static <VP extends VillagerProfession> Supplier<VP> registerVillagerProfession(String id, Supplier<VP> villagerProfession) {
        throw new AssertionError("Failed to register Biomes We've Gone Villager Profession");
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
    public static Supplier<SoundEvent> registerSoundEvent(String id, Supplier<SoundEvent> soundEvent) {
        throw new AssertionError("Failed to register Biomes We've Gone Sound");
    }

    @ExpectPlatform
    public static Supplier<Holder.Reference<SoundEvent>> registerSoundEventHolder(String name) {
        throw new AssertionError("Failed to register Biomes We've Gone Sound");
    }

    @ExpectPlatform
    public static Supplier<RecordItem> createRecordItem(int comparatorValue, Supplier<SoundEvent> sound, int lengthInSeconds) {
        throw new AssertionError("Failed to register Biomes We've Gone Record Item");
    }

    @ExpectPlatform
    public static Supplier<BWGFarmLandBlock> bwgFarmLandBlock(Supplier<Block> dirt) {
        throw new AssertionError("Failed to register Biomes We've Gone blocks");
    }
}
