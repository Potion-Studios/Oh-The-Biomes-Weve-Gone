package net.potionstudios.biomeswevegone;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.level.material.Fluid;
import net.potionstudios.biomeswevegone.world.level.block.custom.BWGFarmLandBlock;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.ServiceLoader;
import java.util.function.Supplier;

public interface RegistrationHandlerA {

	RegistrationHandlerA REGISTRATION = load(RegistrationHandlerA.class);

	/**
	 * Gets the name of the current platform
	 * @return The name of the current platform.
	 */
	String getPlatformName();

	<E extends Entity> Supplier<EntityType<E>> registerEntity(String id, EntityType.EntityFactory<E> factory, MobCategory category, float width, float height);

	<E extends Entity> Supplier<EntityType<E>> registerEntity(String id, EntityType.EntityFactory<E> factory, MobCategory category, float width, float height, int trackingRange);

	/**
	 * Registers a block entity with the specified parameters
	 * @see BlockEntityType
	 * @param key The id/name of the block entity
	 * @param builder The builder for the block entity
	 * @return Supplier of the BlockEntityType
	 */
	<T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String key, Supplier<BlockEntityType.Builder<T>> builder);

	default Supplier<SpawnEggItem> createSpawnEgg(Supplier<EntityType<? extends Mob>> entity, int backgroundColor, int highlightColor) {
		return () -> new SpawnEggItem(entity.get(), backgroundColor, highlightColor, new Item.Properties());
	}

	default Supplier<FlowerPotBlock> createPottedBlock(Supplier<? extends Block> block) {
		return () -> new FlowerPotBlock(block.get(), FlowerPotBlock.Properties.copy(Blocks.FLOWER_POT));
	}

	default Supplier<MobBucketItem> createMobBucket(Supplier<EntityType<? extends Mob>> entity, Supplier<Fluid> fluid, Supplier<SoundEvent> sound) {
		return () -> new MobBucketItem(entity.get(), fluid.get(), sound.get(), new Item.Properties().stacksTo(1));
	}

	default Supplier<RecordItem> createRecordItem(int comparatorValue, Supplier<SoundEvent> sound, int lengthInSeconds) {
		return () -> new RecordItem(comparatorValue, sound.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), lengthInSeconds);
	}

	default Supplier<BWGFarmLandBlock> bwgFarmLandBlock(Supplier<Block> dirt) {
		return () -> new BWGFarmLandBlock(dirt);
	}

	/**
	 * Registers a wood type with the specified id and block set type
	 * @see WoodType
	 * @param id The id/name of the wood type
	 * @param setType The block set type of the wood type
	 * @return WoodType
	 */
	WoodType createWoodType(String id, @NotNull BlockSetType setType);

	Supplier<SimpleParticleType> registerCreateParticle(String name);

	Supplier<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items);

	private static <T> T load(Class<T> clazz) {
		final T loadedService = ServiceLoader.load(clazz)
				.findFirst()
				.orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
		BiomesWeveGone.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
		return loadedService;
	}

	<T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value);

	<T> Supplier<Holder.Reference<T>> registerForHolder(Registry<T> registry, String name, Supplier<T> value);
}
