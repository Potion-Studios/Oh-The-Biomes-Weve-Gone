package net.potionstudios.biomeswevegone.world.entity.ai.memory;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class BWGMemoryModuleType {

    public static final Supplier<MemoryModuleType<List<LivingEntity>>> VISIBLE_PUMPKIN_WARDENS = register("visible_pumpkin_wardens", Optional.empty());
    public static final Supplier<MemoryModuleType<BlockPos>> HOPPER_BARREL_LOCATION = register("hopper_barrel_location", Optional.of(BlockPos.CODEC));

    private static <U> Supplier<MemoryModuleType<U>> register(String name, Optional<Codec<U>> codec) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.MEMORY_MODULE_TYPE, name, () -> new MemoryModuleType<>(codec));
    }

    public static void memoryModuleTypes() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Memory Module Types");
    }
}
