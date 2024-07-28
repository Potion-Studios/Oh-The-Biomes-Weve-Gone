package net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.processors.GreenAppleProcessor;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.processors.WindSweptProcessor;

public class BWGCustomStructureProcessors {

    public static final StructureProcessorType<WindSweptProcessor> WINDSWEPT_PROCESSOR = register("windswept_processor", WindSweptProcessor.CODEC);
    public static final StructureProcessorType<GreenAppleProcessor> GREEN_APPLE_PROCESSOR = register("green_apple_processor", GreenAppleProcessor.CODEC);

    /**
     * Utility method for registering custom structure processor types.
     */
    private static <P extends StructureProcessor> StructureProcessorType<P> register(String id, Codec<P> codec) {
        return Registry.register(BuiltInRegistries.STRUCTURE_PROCESSOR, BiomesWeveGone.id(id), () -> codec);
    }

    public static void init() {
        BiomesWeveGone.LOGGER.info("Initializing custom structure processors for Biomes We've Gone.");
    }
}
