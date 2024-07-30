package net.potionstudios.biomeswevegone.fabric;

import com.google.auto.service.AutoService;
import net.minecraft.core.Registry;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.RegistrationHandlerA;

import java.util.function.Supplier;

@AutoService(RegistrationHandlerA.class)
public class FabricRegistrationHandler implements RegistrationHandlerA {
	@Override
	public String getPlatformName() {
		return "Fabric";
	}

	@Override
	public <T> Supplier<T> register(Registry<T> registry, String name, Supplier<T> value) {
		T value1 = Registry.register(registry, BiomesWeveGone.id(name), value.get());
		return () -> value1;
	}
}
