package net.potionstudios.biomeswevegone.world.entity.ai.memory;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.potionstudios.biomeswevegone.PlatformHandler;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class BWGMemoryModuleType<U> {

    public static final Supplier<MemoryModuleType<List<BlockPos>>> PUMPKIN_STEMS = register("pumpkin_stems", Codec.list(BlockPos.CODEC));

    private static <U> Supplier<MemoryModuleType<U>> register(String name, Codec<U> codec) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.MEMORY_MODULE_TYPE, name, () -> new MemoryModuleType<>(Optional.of(codec)));
    }
}
