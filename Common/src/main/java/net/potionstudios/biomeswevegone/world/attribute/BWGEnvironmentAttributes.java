package net.potionstudios.biomeswevegone.world.attribute;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.attribute.AttributeTypes;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.entity.schedule.Activity;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;

import java.util.function.Supplier;

public interface BWGEnvironmentAttributes {

    Supplier<EnvironmentAttribute<Activity>> PUMPKIN_WARDEN_ACTIVITY = register("gameplay/pumpkin_warden_activity", EnvironmentAttribute.builder(AttributeTypes.ACTIVITY).defaultValue(Activity.IDLE));

    private static <Value> Supplier<EnvironmentAttribute<Value>> register(String id, EnvironmentAttribute.Builder<Value> builder) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, id, builder::build);
    }

    static void environmentAttributes() {
        BiomesWeveGone.LOGGER.info("Registering Biomes We've Gone Environment Attributes");
    }
}
