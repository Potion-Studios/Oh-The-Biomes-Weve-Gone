package net.potionstudios.biomeswevegone.world.entity.ai.sensing;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;

import java.util.function.Supplier;

public class BWGSensorType {
    public static final Supplier<SensorType<PumpkinWardenSensor>> NEAREST_PUMPKIN_WARDENS = register("nearest_pumpkin_wardens", PumpkinWardenSensor::new);

    public static void sensorTypes() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Sensor Types");
    }

    private static <U extends Sensor<?>> Supplier<SensorType<U>> register(String name, Supplier<U> sensor) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.SENSOR_TYPE, name, () -> new SensorType<>(sensor));
    }
}
