package net.potionstudios.biomeswevegone.fabric;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.*;
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
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.level.block.custom.BWGFarmLandBlock;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * @author Joseph T. McQuigg
 * This is the fabric implementation of the RegistrationHandler interface.
 * This class is responsible for registering all the items, blocks, entities, etc.
 * @see net.potionstudios.biomeswevegone.RegistrationHandler
 * @see BuiltInRegistries
 */
public class RegistrationHandlerImpl {

    public static <E extends Entity> Supplier<EntityType<E>> createEntity(String id, EntityType.EntityFactory<E> factory, MobCategory category, float width, float height) {
        EntityType<E> entity = FabricEntityTypeBuilder.create(category, factory).dimensions(EntityDimensions.scalable(width, height)).build();
        Registry.register(BuiltInRegistries.ENTITY_TYPE, BiomesWeveGone.id(id), entity);
        return () -> entity;
    }

    public static <E extends Entity> Supplier<EntityType<E>> createEntity(String id, EntityType.EntityFactory<E> factory, MobCategory category, float width, float height, int trackingRange) {
        EntityType<E> entity = FabricEntityTypeBuilder.create(category, factory).dimensions(EntityDimensions.scalable(width, height)).trackRangeChunks(trackingRange).build();
        Registry.register(BuiltInRegistries.ENTITY_TYPE, BiomesWeveGone.id(id), entity);
        return () -> entity;
    }

    public static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String key, Supplier<BlockEntityType.Builder<T>> builder) {
        ResourceLocation resourceLocation = BiomesWeveGone.id(key);
        BlockEntityType<T> blockEntity = builder.get().build(Util.fetchChoiceType(References.BLOCK_ENTITY, resourceLocation.toString()));
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, resourceLocation, blockEntity);
        return () -> blockEntity;
    }


    public static WoodType createWoodType(String id, @NotNull BlockSetType setType) {
        return new WoodTypeBuilder().register(BiomesWeveGone.id(id), setType);
    }

    public static Supplier<Codec<? extends SurfaceRules.RuleSource>> registerMaterialRule(String id, Supplier<Codec<? extends SurfaceRules.RuleSource>> codec) {
        Codec<? extends SurfaceRules.RuleSource> codec1 = Registry.register(BuiltInRegistries.MATERIAL_RULE, BiomesWeveGone.id(id), codec.get());
        return () -> codec1;
    }


    public static <P extends BlockStateProvider> Supplier<BlockStateProviderType<P>> registerStateProvider(String id, Supplier<Codec<P>> codec) {
        BlockStateProviderType<P> blockStateProvider = Registry.register(BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE, BiomesWeveGone.id(id), new BlockStateProviderType<>(codec.get()));
        return () -> blockStateProvider;
    }

    public static <P extends BlockPredicate> Supplier<BlockPredicateType<P>> registerBlockPredicate(String id, Supplier<Codec<P>> codec) {
        BlockPredicateType<P> blockPredicateType = Registry.register(BuiltInRegistries.BLOCK_PREDICATE_TYPE, BiomesWeveGone.id(id), codec::get);
        return () -> blockPredicateType;
    }


    public static <P extends TreeDecorator> Supplier<TreeDecoratorType<P>> registerTreeDecoratorType(String id, Supplier<Codec<P>> codec) {
        TreeDecoratorType<P> treeDecoratorType = Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, BiomesWeveGone.id(id), new TreeDecoratorType<>(codec.get()));
        return () -> treeDecoratorType;
    }

    public static Supplier<Holder.Reference<SoundEvent>> registerSoundEventHolder(String name) {
        Holder.Reference<SoundEvent> event = Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, BiomesWeveGone.id(name), SoundEvent.createVariableRangeEvent(BiomesWeveGone.id(name)));
        return () -> event;
    }

    public static Supplier<BWGFarmLandBlock> bwgFarmLandBlock(Supplier<Block> dirt) {
        return () -> new BWGFarmLandBlock(dirt);
    }

}
