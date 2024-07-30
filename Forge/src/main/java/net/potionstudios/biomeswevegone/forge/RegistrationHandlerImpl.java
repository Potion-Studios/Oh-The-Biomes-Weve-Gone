package net.potionstudios.biomeswevegone.forge;

import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
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
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.forge.world.level.block.BWGFarmLandBlock;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Joseph T. McQuigg
 * This is the forge implementation of the RegistrationHandler interface.
 * This class is responsible for registering all the items, blocks, entities, etc.
 * @see net.potionstudios.biomeswevegone.RegistrationHandler
 * @see ForgeRegistries
 */
public class RegistrationHandlerImpl {
    private static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BiomesWeveGone.MOD_ID);

    public static Supplier<SimpleParticleType> registerParticle(String id) {
        return PARTICLES.register(id, () -> new SimpleParticleType(false));
    }

    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BiomesWeveGone.MOD_ID);

    public static <E extends Entity> Supplier<EntityType<E>> createEntity(String id, EntityType.EntityFactory<E> factory, MobCategory category, float width, float height) {
        return ENTITY_TYPES.register(id, () -> EntityType.Builder.of(factory, category).sized(width, height).build(BiomesWeveGone.id(id).toString()));
    }

    public static <E extends Entity> Supplier<EntityType<E>> createEntity(String id, EntityType.EntityFactory<E> factory, MobCategory category, float width, float height, int trackingRange) {
        return ENTITY_TYPES.register(id, () -> EntityType.Builder.of(factory, category).sized(width, height).clientTrackingRange(trackingRange).build(BiomesWeveGone.id(id).toString()));
    }

    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BiomesWeveGone.MOD_ID);

    @SafeVarargs
    public static Supplier<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items) {
        return register(name, CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + BiomesWeveGone.MOD_ID + "." + name))
                .icon(icon)
                .displayItems((context, entries) -> {
                    for (ArrayList<Supplier<? extends Item>> item : items)
                        item.forEach((item1) -> entries.accept(item1.get()));
                })
                .withSearchBar()
                .build());
    }

    private static RegistryObject<CreativeModeTab> register(String name, CreativeModeTab tab) {
        return CREATIVE_TABS.register(name, () -> tab);
    }

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BiomesWeveGone.MOD_ID);

    public static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String key, Supplier<BlockEntityType.Builder<T>> builder) {
        return BLOCK_ENTITIES.register(key, () -> builder.get().build(Util.fetchChoiceType(References.BLOCK_ENTITY, key)));
    }

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BiomesWeveGone.MOD_ID);

    public static <I extends Item> Supplier<I> registerItem(String id, Supplier<I> item) {
        return ITEMS.register(id, item);
    }

    public static Supplier<SpawnEggItem> createSpawnEgg(String id, Supplier<EntityType<? extends Mob>> entity, int backgroundColor, int highlightColor) {
        return registerItem(id, () -> new ForgeSpawnEggItem(entity, backgroundColor, highlightColor, new Item.Properties()));
    }

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BiomesWeveGone.MOD_ID);

    public static void registerPottedPlants() {
        BLOCKS.getEntries().forEach(entry -> {
            if (entry.get() instanceof FlowerPotBlock)
                ((FlowerPotBlock) Blocks.FLOWER_POT)
                        .addPlant(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(((FlowerPotBlock) entry.get()).getContent())), entry);
        });
    }

    public static WoodType createWoodType(String id, @NotNull BlockSetType setType) {
        return WoodType.register(new WoodType(BiomesWeveGone.MOD_ID + ":" + id, setType));
    }

    private static final DeferredRegister<Codec<? extends SurfaceRules.RuleSource>> MATERIAL_RULES = DeferredRegister.create(Registries.MATERIAL_RULE, BiomesWeveGone.MOD_ID);

    public static Supplier<Codec<? extends SurfaceRules.RuleSource>> registerMaterialRule(String id, Supplier<Codec<? extends SurfaceRules.RuleSource>> codec) {
        return MATERIAL_RULES.register(id, codec);
    }

    private static final DeferredRegister<BlockStateProviderType<?>> STATE_PROVIDERS = DeferredRegister.create(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES, BiomesWeveGone.MOD_ID);

    public static <P extends BlockStateProvider> Supplier<BlockStateProviderType<P>> registerStateProvider(String id, Supplier<Codec<P>> codec) {
        return STATE_PROVIDERS.register(id, () -> new BlockStateProviderType<>(codec.get()));
    }

    private static final DeferredRegister<BlockPredicateType<?>> BLOCK_PREDICATE_TYPE = DeferredRegister.create(Registries.BLOCK_PREDICATE_TYPE, BiomesWeveGone.MOD_ID);

    public static <P extends BlockPredicate> Supplier<BlockPredicateType<P>> registerBlockPredicate(String id, Supplier<Codec<P>> codec) {
        return BLOCK_PREDICATE_TYPE.register(id, () -> codec::get);
    }

    private static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPE = DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, BiomesWeveGone.MOD_ID);

    public static <P extends TreeDecorator> Supplier<TreeDecoratorType<P>> registerTreeDecoratorType(String id, Supplier<Codec<P>> codec) {
        return TREE_DECORATOR_TYPE.register(id, () -> new TreeDecoratorType<>(codec.get()));
    }

    private static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BiomesWeveGone.MOD_ID);


    public static Supplier<Holder.Reference<SoundEvent>> registerSoundEventHolder(String name) {
        var one = SOUND_EVENT.register(name, () -> SoundEvent.createVariableRangeEvent(BiomesWeveGone.id(name)));
        return () -> (Holder.Reference<SoundEvent>) one.getHolder().get();
    }

    public static void init(IEventBus bus) {
        PARTICLES.register(bus);
        ENTITY_TYPES.register(bus);
        CREATIVE_TABS.register(bus);
        BLOCK_ENTITIES.register(bus);
        ITEMS.register(bus);
        BLOCKS.register(bus);
        MATERIAL_RULES.register(bus);
        STATE_PROVIDERS.register(bus);
        BLOCK_PREDICATE_TYPE.register(bus);
        TREE_DECORATOR_TYPE.register(bus);
        SOUND_EVENT.register(bus);
    }

    public static Supplier<BWGFarmLandBlock> bwgFarmLandBlock(Supplier<Block> dirt) {
        return () -> new BWGFarmLandBlock(dirt);
    }
}
