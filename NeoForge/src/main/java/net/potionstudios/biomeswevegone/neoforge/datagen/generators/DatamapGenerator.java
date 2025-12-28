package net.potionstudios.biomeswevegone.neoforge.datagen.generators;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.BiomeVillagerType;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.entity.npc.BWGVillagerTypes;
import net.potionstudios.biomeswevegone.world.level.block.BlockFeatures;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DatamapGenerator extends DataMapProvider {
    /**
     * Create a new provider.
     *
     * @param packOutput     the output location
     * @param lookupProvider a {@linkplain CompletableFuture} supplying the registries
     */
    public DatamapGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.@NotNull Provider arg) {
        Builder<FurnaceFuel, Item> fuelBuilder = builder(NeoForgeDataMaps.FURNACE_FUELS);
        BlockFeatures.registerFurnaceFuels((block, burnTime) -> fuelBuilder.add(id(block.asItem()), new FurnaceFuel(burnTime), false));
        fuelBuilder.conditions(new ModLoadedCondition(BiomesWeveGone.MOD_ID));

        Builder<Compostable, Item> compostableBuilder = builder(NeoForgeDataMaps.COMPOSTABLES);
        BlockFeatures.registerCompostables((item, chance) -> compostableBuilder.add(id(item.asItem()), new Compostable(chance, false), false));
        compostableBuilder.conditions(new ModLoadedCondition(BiomesWeveGone.MOD_ID));

        Builder<BiomeVillagerType, Biome> biomeVillagerTypeBuilder = builder(NeoForgeDataMaps.VILLAGER_TYPES);
        BWGVillagerTypes.setVillagerBiomes(((biomeResourceKey, villagerType) -> biomeVillagerTypeBuilder.add(biomeResourceKey, new BiomeVillagerType(villagerType), false)));
        biomeVillagerTypeBuilder.conditions(new ModLoadedCondition(BiomesWeveGone.MOD_ID));
    }

    private Identifier id(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
