package net.potionstudios.biomeswevegone.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.RegistrationHandlerA;

import java.util.function.Supplier;

public class BWGStateProviders {

    public static final Supplier<BlockStateProviderType<BetweenNoiseThresholdProvider>> BETWEEN_NOISE_THRESHOLD_PROVIDER = register("between_noise_threshold_provider", BetweenNoiseThresholdProvider.CODEC);

    private static <P extends BlockStateProvider> Supplier<BlockStateProviderType<P>> register(String id, Codec<P> codec) {
        return RegistrationHandlerA.REGISTRATION.register(BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE, id, () -> new BlockStateProviderType<>(codec));
    }

    public static void stateProviders() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone State Providers");
    }
}
