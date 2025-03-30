package net.potionstudios.biomeswevegone.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;
import net.potionstudios.biomeswevegone.world.level.block.entities.PumpkinBurrowBlockEntity;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class BWGDataComponents {

    public static final Supplier<DataComponentType<PumpkinBurrowBlockEntity.Occupant>> PUMPKIN_WARDEN = register(
            "pumpkin_warden",
            builder -> builder.persistent(PumpkinBurrowBlockEntity.Occupant.CODEC)
                    .networkSynchronized(PumpkinBurrowBlockEntity.Occupant.STREAM_CODEC)
                    .cacheEncoding()
    );

    private static <T> Supplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.DATA_COMPONENT_TYPE, name, () -> builder.apply(DataComponentType.builder()).build());
    }

    public static void dataComponents() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone data components");
    }
}
