package net.potionstudios.biomeswevegone.world.entity.npc;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.item.trading.VillagerTrades;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.config.configs.BWGTradesConfig;
import net.potionstudios.biomeswevegone.tags.BWGStructureTags;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.sand.BWGSandSet;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWood;
import net.potionstudios.biomeswevegone.world.level.saveddata.maps.BWGMapDecorationTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Villager trades are now data-driven registry entries ({@link VillagerTrade}) grouped into
 * {@link TradeSet}s, mirroring the vanilla {@link VillagerTrades} bootstrap pattern instead of the
 * old {@code VillagerTrades.ItemListing} lambda maps.
 * @see VillagerTrade
 * @see TradeSet
 * @author Joseph T. McQuigg
 */
public interface BWGVillagerTrades {

    Map<ResourceKey<VillagerTrade>, VillagerTradeFactory> VILLAGER_TRADE_FACTORIES = new HashMap<>();
    Map<ResourceKey<TradeSet>, TradeSetFactory> TRADE_SET_FACTORIES = new HashMap<>();

    Map<ResourceKey<VillagerProfession>, Int2ObjectMap<List<ResourceKey<VillagerTrade>>>> TRADES = new HashMap<>();
    Int2ObjectMap<List<ResourceKey<VillagerTrade>>> WANDERING_TRADER_TRADES = new Int2ObjectOpenHashMap<>();

    // Referenced by BWGVillagerProfessions.FORAGER's constructor, so this is built eagerly (rather than
    // from makeTrades()) to guarantee it is populated the moment the profession's VillagerProfession is built.
    Int2ObjectMap<ResourceKey<TradeSet>> FORAGER_TRADE_SETS = BWGTradesConfig.INSTANCE.villagerTrades.allowBWGForagerTrades.value() ? makeForagerTradeSets() : new Int2ObjectOpenHashMap<>();

    static void makeTrades() {
        TRADES.put(VillagerProfession.FISHERMAN, toIntMap(ImmutableMap.of(
                5, List.of(
                        villagerTypeItemForEmeralds("fisherman_skyris_boat_emerald", BWGVillagerTypes.SKYRIS, BWGWood.SKYRIS.boatItem(), 1, 12, 30),
                        villagerTypeItemForEmeralds("fisherman_salem_boat_emerald", BWGVillagerTypes.SALEM, BWGWood.WITCH_HAZEL.boatItem(), 1, 12, 30)
                )
        )));
        if (!BWGTradesConfig.INSTANCE.villagerTrades.enableBWGVanillaProfessionTradeAdditions.value()) return;
        TRADES.put(VillagerProfession.BUTCHER, toIntMap(ImmutableMap.of(
                2, List.of(
                        emeraldForItems("butcher_blueberries_emerald", BWGItems.BLUEBERRIES, 10, 12, 2)
                )
        )));
        TRADES.put(VillagerProfession.FARMER, toIntMap(ImmutableMap.of(
                1, List.of(
                        emeraldForItems("farmer_cattail_sprout_emerald", BWGItems.CATTAIL_SPROUT, 24, 12, 2)
                ),
                2, List.of(
                        emeraldForItems("farmer_baobab_fruit_emerald", BWGItems.BAOBAB_FRUIT, 10, 12, 2),
                        emeraldForItems("farmer_green_apple_emerald", BWGItems.GREEN_APPLE, 24, 12, 2),
                        emeraldForItems("farmer_aloe_vera_emerald", BWGBlocks.ALOE_VERA, 16, 12, 2)
                ),
                3, List.of(
                        emeraldForItems("farmer_yucca_fruit_emerald", BWGItems.YUCCA_FRUIT, 10, 12, 2)
                )
        )));
        TRADES.put(VillagerProfession.MASON, toIntMap(ImmutableMap.of(
                3, List.of(
                        emeraldForItems("mason_rocky_stone_emerald", BWGBlocks.ROCKY_STONE_SET::getBase, 12, 12, 20),
                        itemsForEmeralds("mason_emerald_rocky_stone", BWGBlocks.ROCKY_STONE_SET::getBase, 1, 1, 12, 10, 0.05F),
                        emeraldForItems("mason_mossy_stone_emerald", BWGBlocks.MOSSY_STONE_SET::getBase, 12, 12, 20),
                        itemsForEmeralds("mason_emerald_mossy_stone", BWGBlocks.MOSSY_STONE_SET::getBase, 1, 1, 12, 10, 0.05F)
                ),
                4, List.of(
                        emeraldForItems("mason_dacite_emerald", BWGBlocks.DACITE_SET::getBase, 12, 12, 30),
                        itemsForEmeralds("mason_emerald_dacite", BWGBlocks.DACITE_SET::getBase, 1, 1, 12, 15, 0.05F),
                        emeraldForItems("mason_white_dacite_emerald", BWGBlocks.WHITE_DACITE_SET::getBase, 12, 12, 30),
                        itemsForEmeralds("mason_emerald_white_dacite", BWGBlocks.WHITE_DACITE_SET::getBase, 1, 1, 12, 15, 0.05F),
                        emeraldForItems("mason_red_rock_emerald", BWGBlocks.RED_ROCK_SET::getBase, 12, 12, 30),
                        itemsForEmeralds("mason_emerald_red_rock", BWGBlocks.RED_ROCK_SET::getBase, 1, 1, 12, 15, 0.05F)
                )
        )));
        TRADES.put(VillagerProfession.CARTOGRAPHER, toIntMap(ImmutableMap.of(
                3, List.of(
                        treasureMapForEmeralds("cartographer_bog_trial_map_emerald", 12, BWGStructureTags.BOG_TRIALS, BWGMapDecorationTypes.BOG_TRIAL.get(), 12, 10)
                )
        )));
    }

    private static Int2ObjectMap<ResourceKey<TradeSet>> makeForagerTradeSets() {
        Int2ObjectMap<List<ResourceKey<VillagerTrade>>> tiers = toIntMap(ImmutableMap.of(
                1, List.of(
                        emeraldForItems("forager_red_mushroom_emerald", () -> Items.RED_MUSHROOM, 10, 12, 2),
                        emeraldForItems("forager_brown_mushroom_emerald", () -> Items.BROWN_MUSHROOM, 10, 12, 2),
                        emeraldForItems("forager_green_mushroom_emerald", BWGBlocks.GREEN_MUSHROOM, 10, 12, 2)
                ),
                2, List.of(
                        emeraldForItems("forager_wood_blewit_emerald", BWGBlocks.WOOD_BLEWIT, 8, 12, 3),
                        itemsForEmeralds("forager_emerald_white_puffball_cap", BWGItems.WHITE_PUFFBALL_CAP, 4, 5, 4, 2, 0.05F),
                        itemsForEmeralds("forager_emerald_wreath", BWGItems.WREATH, 1, 2, 8, 2, 0.05F)
                ),
                3, List.of(
                        emeraldForItems("forager_white_puffball_spores_emerald", BWGItems.WHITE_PUFFBALL_SPORES, 4, 5, 4),
                        itemsForEmeralds("forager_emerald_holly_wreath", BWGItems.HOLLY_WREATH, 2, 1, 8, 2, 0.05F),
                        itemsForEmeralds("forager_emerald_rosy_wreath", BWGItems.ROSY_WREATH, 2, 1, 8, 2, 0.05F),
                        itemsForEmeralds("forager_emerald_petal_wreath", BWGItems.PETAL_WREATH, 2, 1, 8, 2, 0.05F)
                ),
                4, List.of(
                        itemsForEmeralds("forager_emerald_witch_hazel_branch", BWGBlocks.WITCH_HAZEL_BRANCH, 4, 9, 4, 3, 0.05F),
                        itemsForEmeralds("forager_emerald_witch_hazel_blossom", BWGBlocks.WITCH_HAZEL_BLOSSOM, 10, 1, 10, 3, 0.05F)
                ),
                5, List.of(
                        itemsForEmeralds("forager_emerald_shelf_fungi", BWGBlocks.SHELF_FUNGI, 3, 9, 4, 4, 0.05F),
                        emeraldForItems("forager_sweet_berries_emerald", () -> Items.SWEET_BERRIES, 16, 4, 2),
                        emeraldForItems("forager_blueberries_emerald", BWGItems.BLUEBERRIES, 16, 4, 2)
                )
        ));
        Int2ObjectMap<ResourceKey<TradeSet>> tradeSets = new Int2ObjectOpenHashMap<>();
        tiers.forEach((level, trades) -> tradeSets.put((int) level, registerTradeSet("forager_level_" + level, trades)));
        return tradeSets;
    }

    static void makeWanderingTrades() {
        List<ResourceKey<VillagerTrade>> level1Items = new ArrayList<>();
        BWGWood.WOOD.stream().filter(item -> item.get() instanceof SaplingBlock).forEach(item ->
                level1Items.add(itemsForEmeralds(wanderingTradeId(item.get()), item, 5, 1, 8, 1, 0.05F)));
        BWGSandSet.getSandSets().forEach(bwgSandSet -> level1Items.add(
                itemsForEmeralds(wanderingTradeId(bwgSandSet.getSand()), bwgSandSet::getSand, 1, 8, 8, 1, 0.05F)));
        BWGBlocks.BLOCKS.stream().filter(block -> block.get() instanceof FlowerBlock).forEach(block ->
                level1Items.add(itemsForEmeralds(wanderingTradeId(block.get()), block, 1, 1, 13, 1, 0.05F)));
        level1Items.add(itemsForEmeralds(wanderingTradeId(BWGItems.TINY_LILY_PADS.get()), BWGItems.TINY_LILY_PADS, 1, 2, 5, 1, 0.05F));
        level1Items.add(itemsForEmeralds(wanderingTradeId(BWGItems.FLOWERING_TINY_LILY_PADS.get()), BWGItems.FLOWERING_TINY_LILY_PADS, 1, 2, 5, 1, 0.05F));
        level1Items.add(itemsForEmeralds(wanderingTradeId(BWGBlocks.WEEPING_MILKCAP.get()), BWGBlocks.WEEPING_MILKCAP, 1, 1, 12, 1, 0.05F));
        level1Items.add(itemsForEmeralds(wanderingTradeId(BWGBlocks.GREEN_MUSHROOM.get()), BWGBlocks.GREEN_MUSHROOM, 1, 1, 12, 1, 0.05F));
        level1Items.add(itemsForEmeralds(wanderingTradeId(BWGBlocks.WOOD_BLEWIT.get()), BWGBlocks.WOOD_BLEWIT, 1, 1, 12, 1, 0.05F));
        WANDERING_TRADER_TRADES.put(1, level1Items);
    }

    private static String wanderingTradeId(ItemLike item) {
        return "wandering_trader_" + BuiltInRegistries.ITEM.getKey(item.asItem()).getPath() + "_emerald";
    }

    private static ResourceKey<VillagerTrade> emeraldForItems(String id, Supplier<? extends ItemLike> item, int cost, int maxUses, int villagerXp) {
        return register(id, context -> new VillagerTrade(new TradeCost(item.get(), cost), new ItemStackTemplate(Items.EMERALD, 1), maxUses, villagerXp, 0.05F, Optional.empty(), List.of()));
    }

    private static ResourceKey<VillagerTrade> itemsForEmeralds(String id, Supplier<? extends ItemLike> item, int emeraldCost, int numberOfItems, int maxUses, int villagerXp, float priceMultiplier) {
        return register(id, context -> new VillagerTrade(new TradeCost(Items.EMERALD, emeraldCost), new ItemStackTemplate(item.get().asItem(), numberOfItems), maxUses, villagerXp, priceMultiplier, Optional.empty(), List.of()));
    }

    private static ResourceKey<VillagerTrade> villagerTypeItemForEmeralds(String id, ResourceKey<VillagerType> villagerType, Supplier<? extends ItemLike> item, int emeraldCost, int maxUses, int villagerXp) {
        return register(id, context -> new VillagerTrade(new TradeCost(Items.EMERALD, emeraldCost), new ItemStackTemplate(item.get().asItem(), 1), maxUses, villagerXp, 0.05F,
                VillagerTrades.villagerTypeRestriction(VillagerTrades.villagerTypeHolderSet(context.lookup(Registries.VILLAGER_TYPE), villagerType)), List.of()));
    }

    private static ResourceKey<VillagerTrade> treasureMapForEmeralds(String id, int emeraldCost, TagKey<Structure> destination, Holder<MapDecorationType> mapDecoration, int maxUses, int villagerXp) {
        return register(id, context -> new VillagerTrade(new TradeCost(Items.EMERALD, emeraldCost), new ItemStackTemplate(Items.MAP, 1), maxUses, villagerXp, 0.2F, Optional.empty(),
                List.of(ExplorationMapFunction.makeExplorationMap().setDestination(destination).setMapDecoration(mapDecoration).build())));
    }

    private static ResourceKey<VillagerTrade> register(String id, VillagerTradeFactory factory) {
        ResourceKey<VillagerTrade> key = BiomesWeveGone.key(Registries.VILLAGER_TRADE, id);
        VILLAGER_TRADE_FACTORIES.put(key, factory);
        return key;
    }

    // Each forager tier picks 2 of its trades per villager, matching vanilla's classic per-level trade count.
    private static ResourceKey<TradeSet> registerTradeSet(String id, List<ResourceKey<VillagerTrade>> trades) {
        ResourceKey<TradeSet> key = BiomesWeveGone.key(Registries.TRADE_SET, id);
        TRADE_SET_FACTORIES.put(key, context -> {
            var villagerTradeLookup = context.lookup(Registries.VILLAGER_TRADE);
            return new TradeSet(
                    HolderSet.direct(trades.stream().map(villagerTradeLookup::getOrThrow).toList()),
                    ConstantValue.exactly(Math.min(2, trades.size())),
                    false,
                    Optional.empty()
            );
        });
        return key;
    }

    private static Int2ObjectMap<List<ResourceKey<VillagerTrade>>> toIntMap(ImmutableMap<Integer, List<ResourceKey<VillagerTrade>>> map) {
        return new Int2ObjectOpenHashMap<>(map);
    }

    @FunctionalInterface
    interface VillagerTradeFactory {
        VillagerTrade generate(BootstrapContext<VillagerTrade> context);
    }

    @FunctionalInterface
    interface TradeSetFactory {
        TradeSet generate(BootstrapContext<TradeSet> context);
    }
}
