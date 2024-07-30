package net.potionstudios.biomeswevegone.forge;

import com.google.auto.service.AutoService;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.RegistrationHandlerA;

import java.util.Map;
import java.util.function.Supplier;

@AutoService(RegistrationHandlerA.class)
public class ForgeRegistrationHandler implements RegistrationHandlerA {
	@Override
	public String getPlatformName() {
		return "Forge";
	}

	public static final Map<ResourceKey<?>, DeferredRegister> CACHED = new Reference2ObjectOpenHashMap<>();

	@Override
	public <T> Supplier<T> register(Registry<T> registry, String name, Supplier<T> value) {
		return CACHED.computeIfAbsent(registry.key(), key -> DeferredRegister.create(registry.key().location(), BiomesWeveGone.MOD_ID)).register(name, value);
	}

	public static void register(IEventBus bus) {
		CACHED.values().forEach(bus::register);
	}
}
