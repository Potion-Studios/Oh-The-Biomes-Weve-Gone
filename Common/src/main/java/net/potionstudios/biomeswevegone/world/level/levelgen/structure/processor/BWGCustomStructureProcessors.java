package net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.processors.FruitBlockProcessor;

import java.util.function.Supplier;

public class BWGCustomStructureProcessors {

    public static final Supplier<StructureProcessorType<FruitBlockProcessor>> FRUIT_BLOCK_PROCESSOR = register("fruit_block_processor", () -> FruitBlockProcessor.CODEC);

    /**
     * Utility method for registering custom structure processor types.
     */
    private static <P extends StructureProcessor> Supplier<StructureProcessorType<P>> register(String id, Supplier<Codec<P>> codec) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.STRUCTURE_PROCESSOR, id, () -> codec::get);
    }

    public static void processors() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Custom Structure Processors");
    }
}
