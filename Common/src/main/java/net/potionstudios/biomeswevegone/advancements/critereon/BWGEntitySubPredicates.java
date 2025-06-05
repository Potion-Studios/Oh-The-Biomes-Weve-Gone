package net.potionstudios.biomeswevegone.advancements.critereon;

import net.minecraft.advancements.critereon.EntitySubPredicates;
import net.minecraft.core.registries.BuiltInRegistries;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;
import net.potionstudios.biomeswevegone.world.entity.oddion.Oddion;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;

import java.util.Optional;

public class BWGEntitySubPredicates {

    public static final EntitySubPredicates.EntityVariantPredicateType<Oddion.Variant> ODDION = register(
            "oddion",
            EntitySubPredicates.EntityVariantPredicateType.create(
                    Oddion.Variant.CODEC, entity -> entity instanceof Oddion oddion ? Optional.of(oddion.getVariant()) : Optional.empty()
            )
    );

    public static final EntitySubPredicates.EntityVariantPredicateType<PumpkinWarden.Variant> PUMPKIN_WARDEN = register(
            "pumpkin_warden",
            EntitySubPredicates.EntityVariantPredicateType.create(
                    PumpkinWarden.Variant.CODEC, entity -> entity instanceof PumpkinWarden pumpkinWarden ? Optional.of(pumpkinWarden.getVariant()) : Optional.empty()
            )
    );

    private static <V> EntitySubPredicates.EntityVariantPredicateType<V> register(String name, EntitySubPredicates.EntityVariantPredicateType<V> predicateType) {
        PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE, name, () -> predicateType.codec);
        return predicateType;
    }

    public static void entitySubPredicates() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Entity SubPredicates");
    }
}
