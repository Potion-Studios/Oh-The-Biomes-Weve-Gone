package net.potionstudios.biomeswevegone;

import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.material.Fluid;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public interface RegistrationHandlerA {

	RegistrationHandlerA REGISTRATION = load(RegistrationHandlerA.class);

	/**
	 * Gets the name of the current platform
	 * @return The name of the current platform.
	 */
	String getPlatformName();

	default Supplier<MobBucketItem> createMobBucket(Supplier<EntityType<? extends Mob>> entity, Supplier<Fluid> fluid, Supplier<SoundEvent> sound) {
		return () -> new MobBucketItem(entity.get(), fluid.get(), sound.get(), new Item.Properties().stacksTo(1));
	}

	default Supplier<RecordItem> createRecordItem(int comparatorValue, Supplier<SoundEvent> sound, int lengthInSeconds) {
		return () -> new RecordItem(comparatorValue, sound.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), lengthInSeconds);
	}

	private static <T> T load(Class<T> clazz) {

		final T loadedService = ServiceLoader.load(clazz)
				.findFirst()
				.orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
		BiomesWeveGone.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
		return loadedService;
	}

	<T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value);
}
