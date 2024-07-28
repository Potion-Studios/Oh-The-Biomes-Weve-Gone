package net.potionstudios.biomeswevegone.world.level.levelgen.feature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.potionstudios.biomeswevegone.RegistrationHandler;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.config.NoiseSphereConfig;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.config.RoundedRockConfig;

import java.util.function.Supplier;

public class BWGFeatures {

    public static final Supplier<SharpenedRockFeature> SHARPENED_ROCK = RegistrationHandler.registerFeature("sharpened_rock", () -> new SharpenedRockFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<RoundedRock> ROUNDED_ROCK = RegistrationHandler.registerFeature("rounded_rock", () -> new RoundedRock(RoundedRockConfig.CODEC));
//    public static final Supplier<CanyonFeature> CANYON = RegistrationHandler.registerFeature("canyon", () -> new CanyonFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<PillarFeature> PILLAR = RegistrationHandler.registerFeature("pillar", () -> new PillarFeature(PillarFeature.Config.CODEC));
    public static final Supplier<VineProcessorFeature> VINE_PROCESSOR = RegistrationHandler.registerFeature("vine_processor", () -> new VineProcessorFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<LushBlocksProcessorFeature> LUSH_BLOCKS_PROCESSOR = RegistrationHandler.registerFeature("lush_blocks_processor", () -> new LushBlocksProcessorFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<CragLakeFeature> CRAG_LAKE = RegistrationHandler.registerFeature("crag_lake", () -> new CragLakeFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<NoiseSphere> NOISE_SPHERE = RegistrationHandler.registerFeature("noise_sphere", () -> new NoiseSphere(NoiseSphereConfig.CODEC));
    public static final Supplier<LargePumpkinFeature> LARGE_PUMPKIN = RegistrationHandler.registerFeature("large_pumpkin", () -> new LargePumpkinFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<MediumPumpkinFeature> MEDIUM_PUMPKIN = RegistrationHandler.registerFeature("medium_pumpkin", () -> new MediumPumpkinFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<ConfigurableFreezeTopLayer> CONFIGURABLE_FREEZE_TOP_LAYER = RegistrationHandler.registerFeature("configurable_freeze_top_layer", () -> new ConfigurableFreezeTopLayer(ConfigurableFreezeTopLayer.Config.CODEC));


    public static <C extends FeatureConfiguration, F extends Feature<C>> Supplier<F> create(String id, Supplier<F> supplier) {
        return RegistrationHandler.registerFeature(id, supplier);
    }

    public static void init() {
    }
}
