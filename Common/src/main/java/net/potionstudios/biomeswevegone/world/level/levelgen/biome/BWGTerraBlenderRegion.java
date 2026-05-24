package net.potionstudios.biomeswevegone.world.level.levelgen.biome;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.config.configs.BWGWorldGenConfig;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.selector.BWGBiomeSelectors;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.selector.TerraBlenderBiomeSelectors;
import org.apache.commons.lang3.mutable.MutableInt;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.TerrablenderOverworldBiomeBuilder;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BWGTerraBlenderRegion extends Region {

    public static final BWGTerraBlenderRegion REGION_1 = new BWGTerraBlenderRegion(
            BWGWorldGenConfig.INSTANCE.region_1_weight,
            BWGBiomeSelectors.OCEANS_BWG,
            BWGBiomeSelectors.MIDDLE_BIOMES_BWG,
            BWGBiomeSelectors.MIDDLE_BIOMES_VARIANT_BWG,
            BWGBiomeSelectors.PLATEAU_BIOMES_BWG,
            BWGBiomeSelectors.PLATEAU_BIOMES_VARIANT_BWG,
            BWGBiomeSelectors.SHATTERED_BIOMES_BWG,
            BWGBiomeSelectors.BEACH_BIOMES_BWG,
            BWGBiomeSelectors.PEAK_BIOMES_BWG,
            BWGBiomeSelectors.PEAK_BIOMES_VARIANT_BWG,
            BWGBiomeSelectors.SLOPE_BIOMES_BWG,
            TerraBlenderBiomeSelectors.SLOPE_BIOMES_VARIANT_TERRABLENDER,
            Util.make(new IdentityHashMap<>(), map -> {
                map.put(Biomes.MANGROVE_SWAMP, BWGBiomes.CYPRESS_SWAMPLANDS);
                map.put(Biomes.SWAMP, BWGBiomes.BAYOU);
            }),
            Map.of());

    public static final BWGTerraBlenderRegion REGION_2 = new BWGTerraBlenderRegion(
            BWGWorldGenConfig.INSTANCE.region_2_weight,
            BWGBiomeSelectors.OCEANS_2_BWG,
            BWGBiomeSelectors.MIDDLE_BIOMES_2_BWG,
            TerraBlenderBiomeSelectors.MIDDLE_BIOMES_VARIANT_TERRABLENDER,
            BWGBiomeSelectors.PLATEAU_BIOMES_2_BWG,
            TerraBlenderBiomeSelectors.PLATEAU_BIOMES_VARIANT_TERRABLENDER,
            TerraBlenderBiomeSelectors.SHATTERED_BIOMES_TERRABLENDER,
            TerraBlenderBiomeSelectors.BEACH_BIOMES_TERRABLENDER,
            TerraBlenderBiomeSelectors.PEAK_BIOMES_TERRABLENDER,
            TerraBlenderBiomeSelectors.PEAK_BIOMES_VARIANT_TERRABLENDER,
            TerraBlenderBiomeSelectors.SLOPE_BIOMES_TERRABLENDER,
            TerraBlenderBiomeSelectors.SLOPE_BIOMES_VARIANT_TERRABLENDER,
            Util.make(new IdentityHashMap<>(), map -> {
                map.put(Biomes.MANGROVE_SWAMP, BWGBiomes.WHITE_MANGROVE_MARSHES);
                map.put(Biomes.SWAMP, BWGBiomes.CYPRESS_WETLANDS);
            }),
            Map.of());

    public static final BWGTerraBlenderRegion REGION_3 = new BWGTerraBlenderRegion(
            BWGWorldGenConfig.INSTANCE.region_3_weight,
            TerraBlenderBiomeSelectors.OCEANS_TERRABLENDER,
            BWGBiomeSelectors.MIDDLE_BIOMES_3_BWG,
            TerraBlenderBiomeSelectors.MIDDLE_BIOMES_VARIANT_TERRABLENDER,
            BWGBiomeSelectors.PLATEAU_BIOMES_3_BWG,
            TerraBlenderBiomeSelectors.PLATEAU_BIOMES_VARIANT_TERRABLENDER,
            TerraBlenderBiomeSelectors.SHATTERED_BIOMES_TERRABLENDER,
            TerraBlenderBiomeSelectors.BEACH_BIOMES_TERRABLENDER,
            TerraBlenderBiomeSelectors.PEAK_BIOMES_TERRABLENDER,
            TerraBlenderBiomeSelectors.PEAK_BIOMES_VARIANT_TERRABLENDER,
            TerraBlenderBiomeSelectors.SLOPE_BIOMES_TERRABLENDER,
            TerraBlenderBiomeSelectors.SLOPE_BIOMES_VARIANT_TERRABLENDER,
            Util.make(new IdentityHashMap<>(), map -> {
                map.put(Biomes.MANGROVE_SWAMP, BWGBiomes.PALE_BOG);
                map.put(Biomes.SWAMP, BWGBiomes.CYPRESS_SWAMPLANDS);
            }),
            Map.of());

    private static int count = 0;

    private final Set<ResourceKey<Biome>> bwgKeys = new ObjectOpenHashSet<>();
    private final Map<ResourceKey<Biome>, ResourceKey<Biome>> swapper;
    private final Map<ResourceKey<Biome>, ResourceKey<Biome>> globalSwapper;
    private final TerrablenderOverworldBiomeBuilder terrablenderOverworldBiomeBuilder;


    public BWGTerraBlenderRegion(int overworldWeight,
                                 ResourceKey<Biome>[][] oceans, ResourceKey<Biome>[][] middleBiomes,
                                 ResourceKey<Biome>[][] middleBiomesVariant, ResourceKey<Biome>[][] plateauBiomes,
                                 ResourceKey<Biome>[][] plateauBiomesVariant, ResourceKey<Biome>[][] shatteredBiomes,
                                 ResourceKey<Biome>[][] beachBiomes, ResourceKey<Biome>[][] peakBiomes,
                                 ResourceKey<Biome>[][] peakBiomesVariant, ResourceKey<Biome>[][] slopeBiomes, ResourceKey<Biome>[][] slopeBiomesVariant,
                                 Map<ResourceKey<Biome>, ResourceKey<Biome>> swapper, Map<ResourceKey<Biome>, ResourceKey<Biome>> globalSwapper) {
        super(BiomesWeveGone.id("region_" + count++), RegionType.OVERWORLD, overworldWeight);
        this.swapper = swapper;
        this.globalSwapper = globalSwapper;
        Predicate<ResourceKey<Biome>> noVoidBiomes = biomeResourceKey -> biomeResourceKey != Biomes.THE_VOID;
        oceans = BWGRegionUtils.filter("oceans", this.getName(), count, oceans, noVoidBiomes, true);
        middleBiomes = BWGRegionUtils.filter("middle_biomes", this.getName(), count, middleBiomes, noVoidBiomes, true);
        middleBiomesVariant = BWGRegionUtils.filter("middle_biomes_variant", this.getName(), count, middleBiomesVariant, noVoidBiomes, false);
        plateauBiomes = BWGRegionUtils.filter("plateau_biomes", this.getName(), count, plateauBiomes, noVoidBiomes, true);
        plateauBiomesVariant = BWGRegionUtils.filter("plateau_biomes_variant", this.getName(), count, plateauBiomesVariant, noVoidBiomes, false);
        shatteredBiomes = BWGRegionUtils.filter("shattered_biomes", this.getName(), count, shatteredBiomes, noVoidBiomes, false);
        beachBiomes = BWGRegionUtils.filter("beach_biomes", this.getName(), count, beachBiomes, noVoidBiomes, true);
        peakBiomes = BWGRegionUtils.filter("peak_biomes", this.getName(), count, peakBiomes, noVoidBiomes, true);
        peakBiomesVariant = BWGRegionUtils.filter("peak_biomes_variant", this.getName(), count, peakBiomesVariant, noVoidBiomes, false);
        slopeBiomes = BWGRegionUtils.filter("slope_biomes", this.getName(), count, slopeBiomes, noVoidBiomes, true);
        slopeBiomesVariant = BWGRegionUtils.filter("slope_biomes_variant", this.getName(), count, slopeBiomesVariant, noVoidBiomes, false);

        this.terrablenderOverworldBiomeBuilder = new TerrablenderOverworldBiomeBuilder(
                oceans, middleBiomes, middleBiomesVariant,
                plateauBiomes, plateauBiomesVariant, shatteredBiomes,
                beachBiomes, peakBiomes, peakBiomesVariant, slopeBiomes, slopeBiomesVariant
        );

        BWGRegionUtils.dumpArrays((biomeResourceKey -> {
            if (biomeResourceKey != null) {
                bwgKeys.add(biomeResourceKey);
                if (swapper.containsValue(biomeResourceKey)) {
                    throw new IllegalArgumentException("Swapper cannot contain elements found in the temperature arrays.");
                }
            }
        }), oceans, middleBiomes, middleBiomesVariant, plateauBiomes, plateauBiomesVariant, shatteredBiomes, beachBiomes, peakBiomes);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        MutableInt totalPairs = new MutableInt();
        MutableInt bygMapperAccepted = new MutableInt(0);
        this.terrablenderOverworldBiomeBuilder.addBiomes((parameterPointResourceKeyPair -> {
            Climate.ParameterPoint parameterPoint = parameterPointResourceKeyPair.getFirst();
            ResourceKey<Biome> biomeKey = parameterPointResourceKeyPair.getSecond();
            if (!registry.containsKey(biomeKey)) {
                throw new IllegalArgumentException(String.format("\"%s\" is not a valid biome in the world registry!", biomeKey.location()));
            }
            if (BWGWorldGenConfig.INSTANCE.biomes.getOrDefault(biomeKey.location(), true)) {
                totalPairs.increment();
                boolean mapped = false;
                boolean alreadyMappedOutsideSwapper = false;
                if (this.bwgKeys.contains(biomeKey)) {
                    mapper.accept(new Pair<>(parameterPoint, this.globalSwapper.getOrDefault(biomeKey, biomeKey)));
                    bygMapperAccepted.increment();
                    alreadyMappedOutsideSwapper = true;
                    mapped = true;
                }

                if (this.swapper.containsKey(biomeKey)) {
                    if (alreadyMappedOutsideSwapper) {
                        throw new UnsupportedOperationException(String.format("Attempting to assign a biome resource key in both the swapper and biome selectors. We're crashing your game to let you know that \"%s\" was put in the biome selectors but will always be swapped by \"%s\" due to the swapper. In region \"%s\".", biomeKey.location(), this.swapper.get(biomeKey).location(), this.getName().toString()));
                    }
                    ResourceKey<Biome> replacement = this.swapper.get(biomeKey);
                    ResourceKey<Biome> biomeResourceKey = BWGWorldGenConfig.INSTANCE.biomes.getOrDefault(replacement.location(), true) ? replacement : Region.DEFERRED_PLACEHOLDER;
                    mapper.accept(new Pair<>(parameterPoint, this.globalSwapper.getOrDefault(biomeResourceKey, biomeResourceKey)));
                    bygMapperAccepted.increment();
                    mapped = true;
                }

                if (!mapped) {
                    mapper.accept(new Pair<>(parameterPoint, this.globalSwapper.getOrDefault(biomeKey, biomeKey)));
                    bygMapperAccepted.increment();
                }
            } else {
                mapper.accept(new Pair<>(parameterPoint, Region.DEFERRED_PLACEHOLDER));
            }
        }));
        int totalPairsValue = totalPairs.intValue();
        int mapperAcceptValue = bygMapperAccepted.intValue();
        boolean sanityCheck = totalPairsValue != mapperAcceptValue;
        if (sanityCheck) {
            throw new UnsupportedOperationException(String.format("Not all biome parameter points were accepted for BWG TerraBlender biome region: %s. %s/%s were accepted.", this.getName().toString(), totalPairsValue, mapperAcceptValue));
        }
    }


    public static void registerTerraBlenderRegions() {
        Regions.register(REGION_1);
        Regions.register(REGION_2);
        Regions.register(REGION_3);
    }
}