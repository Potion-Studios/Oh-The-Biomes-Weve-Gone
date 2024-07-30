package net.potionstudios.biomeswevegone.forge;

import com.google.auto.service.AutoService;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
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

	@Override
	public Supplier<FlowerPotBlock> createPottedBlock(Supplier<? extends Block> block) {
		return () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, block, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT));
	}

	@Override
	public Supplier<MobBucketItem> createMobBucket(Supplier<EntityType<? extends Mob>> entity, Supplier<Fluid> fluid, Supplier<SoundEvent> sound) {
		return () -> new MobBucketItem(entity, fluid, sound, new Item.Properties().stacksTo(1));
	}

	@Override
	public Supplier<RecordItem> createRecordItem(int comparatorValue, Supplier<SoundEvent> sound, int lengthInSeconds) {
		return () -> new RecordItem(comparatorValue, sound, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), lengthInSeconds * 20);
	}

	public static final Map<ResourceKey<?>, DeferredRegister> CACHED = new Reference2ObjectOpenHashMap<>();

	@Override
	public <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value) {
		return CACHED.computeIfAbsent(registry.key(), key -> DeferredRegister.create(registry.key().location(), BiomesWeveGone.MOD_ID)).register(name, value);
	}

	public static void register(IEventBus bus) {
		CACHED.values().forEach(bus::register);
	}
}
