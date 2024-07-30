package net.potionstudios.biomeswevegone;

import net.minecraft.core.Registry;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public interface RegistrationHandlerA {

	/**
	 * Gets the name of the current platform
	 * @return The name of the current platform.
	 */
	String getPlatformName();

	static <T> T load(Class<T> clazz) {

		final T loadedService = ServiceLoader.load(clazz)
				.findFirst()
				.orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
		BiomesWeveGone.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
		return loadedService;
	}

	<T> Supplier<T> register(Registry<T> registry, String name, Supplier<T> value);

}
