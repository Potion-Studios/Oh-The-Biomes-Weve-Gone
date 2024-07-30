package net.potionstudios.biomeswevegone.fabric;

import com.google.auto.service.AutoService;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.RegistrationHandlerA;

import java.util.ArrayList;
import java.util.function.Supplier;

@AutoService(RegistrationHandlerA.class)
public class FabricRegistrationHandler implements RegistrationHandlerA {
	@Override
	public String getPlatformName() {
		return "Fabric";
	}

	@Override
	public Supplier<SimpleParticleType> registerCreateParticle(String name) {
		SimpleParticleType particleMaker = Registry.register(BuiltInRegistries.PARTICLE_TYPE, BiomesWeveGone.id(name), FabricParticleTypes.simple());
		return () -> particleMaker;
	}

	@SafeVarargs
	@Override
	public final Supplier<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items) {
		CreativeModeTab tab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, BiomesWeveGone.id(name), FabricItemGroup.builder()
				.title(Component.translatable("itemGroup." + BiomesWeveGone.MOD_ID + "." + name))
				.icon(icon)
				.displayItems((entry, context) -> {
					for (ArrayList<Supplier<? extends Item>> item : items)
						item.forEach((item1) -> context.accept(item1.get()));
				})
				.build());
		return () -> tab;
	}

	@Override
	public <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value) {
		T value1 = Registry.register(registry, BiomesWeveGone.id(name), value.get());
		return () -> value1;
	}
}
