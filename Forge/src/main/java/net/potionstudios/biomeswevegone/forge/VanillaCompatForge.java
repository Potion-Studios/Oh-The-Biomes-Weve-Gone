package net.potionstudios.biomeswevegone.forge;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.util.Result;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.potionstudios.biomeswevegone.util.BoneMealHandler;
import net.potionstudios.biomeswevegone.config.configs.BWGTradesConfig;
import net.potionstudios.biomeswevegone.world.entity.npc.BWGVillagerTrades;
import net.potionstudios.biomeswevegone.world.entity.npc.BWGVillagerTypes;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import net.potionstudios.biomeswevegone.world.item.brewing.BWGBrewingRecipes;
import net.potionstudios.biomeswevegone.world.item.tools.ToolInteractions;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.BlockFeatures;

import java.util.HashMap;
import java.util.List;

/**
 * Used for Vanilla compatibility on the Forge platform.
 * @author Joseph T. McQuigg
 */
public class VanillaCompatForge {
    public static void init() {
        ToolInteractions.registerStrippableBlocks((block, stripped) -> {
            AxeItem.STRIPPABLES = new HashMap<>(AxeItem.STRIPPABLES);
            AxeItem.STRIPPABLES.put(block, stripped);
        });
        BlockFeatures.registerFlammable(((FireBlock) Blocks.FIRE)::setFlammable);
        BlockFeatures.registerCompostables((item, chance) -> ComposterBlock.COMPOSTABLES.put(item.asItem(), chance.floatValue()));
        ToolInteractions.registerFlattenables(ShovelItem.FLATTENABLES::put);
        BWGVillagerTypes.setVillagerBiomes(VillagerType::registerBiomeType);
    }

    public static void registerVanillaCompatEvents(final BusGroup bus) {
        BlockEvent.BlockToolModificationEvent.BUS.addListener(VanillaCompatForge::registerTillables);
        FurnaceFuelBurnTimeEvent.BUS.addListener(VanillaCompatForge::registerFuels);
        if (!BWGTradesConfig.INSTANCE.trades.disableTrades.value()) {
            VillagerTradesEvent.BUS.addListener(VanillaCompatForge::onVillagerTrade);
            if (BWGTradesConfig.INSTANCE.wanderingTraderTrades.enableBWGItemsTrades.value())
                WandererTradesEvent.BUS.addListener(VanillaCompatForge::onWanderingTrade);
        }
        BrewingRecipeRegisterEvent.BUS.addListener(VanillaCompatForge::registerBrewingRecipes);
        BonemealEvent.BUS.addListener(VanillaCompatForge::onBoneMealUse);
        PlayerInteractEvent.EntityInteractSpecific.BUS.addListener(VanillaCompatForge::onVillagerInteract);
    }

    /**
     * Register tillable blocks.
     * @see BlockEvent.BlockToolModificationEvent
     */
    private static void registerTillables(final BlockEvent.BlockToolModificationEvent event) {
        if (event.getToolAction() == ToolActions.HOE_TILL && event.getLevel().getBlockState(event.getPos().above()).isAir()) {
            BlockState state = event.getState();
            if (state.is(BWGBlocks.LUSH_GRASS_BLOCK.get()) || state.is(BWGBlocks.LUSH_DIRT.get()))
                event.setFinalState(BWGBlocks.LUSH_FARMLAND.get().defaultBlockState());
            else if (state.is(BWGBlocks.SANDY_DIRT.get()))
                event.setFinalState(BWGBlocks.SANDY_FARMLAND.get().defaultBlockState());
            else if (state.is(BWGBlocks.PEAT.get()))
                event.setFinalState(Blocks.FARMLAND.defaultBlockState());
        }
    }

    /**
     * Register fuels for the furnace.
     * @see FurnaceFuelBurnTimeEvent
     */
    private static void registerFuels(final FurnaceFuelBurnTimeEvent event) {
        BlockFeatures.registerFurnaceFuels((block, burnTime) -> {
            if (event.getItemStack().is(block.asItem())) event.setBurnTime(burnTime);
        });
    }

    /**
     * Register villager trades.
     * @see VillagerTradesEvent
     */
    private static void onVillagerTrade(final VillagerTradesEvent event) {
        if (BWGVillagerTrades.TRADES.containsKey(event.getType())) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            BWGVillagerTrades.TRADES.get(event.getType())
                    .forEach((level, offers) -> {
                        List<VillagerTrades.ItemListing> tradeList = trades.get(level);
                        tradeList.addAll(offers);
                    });
        }
    }

    /**
     * Register wandering trader trades.
     * @see WandererTradesEvent
     */
    private static void onWanderingTrade(final WandererTradesEvent event) {
        BWGVillagerTrades.WANDERING_TRADER_TRADES.forEach((level, offers) -> event.getPools().add(new WandererTradesEvent.Pool(offers, level)));
    }

    /**
     * Register brewing recipes.
     * @see BrewingRecipeRegisterEvent
     */
    private static void registerBrewingRecipes(final BrewingRecipeRegisterEvent event) {
        BWGBrewingRecipes.buildBrewingRecipes(event.getBuilder()::addMix);
    }

    /**
     * Handle bone meal use.
     * @see BonemealEvent
     */
    private static void onBoneMealUse(final BonemealEvent event) {
        if (!event.getLevel().isClientSide() && BoneMealHandler.bwgBoneMealEventHandler((ServerLevel) event.getLevel(), event.getPos(), event.getBlock()))
            event.setResult(Result.ALLOW);
    }

    /**
     * Handle villager interaction.
     * @see PlayerInteractEvent.EntityInteractSpecific
     */
    private static void onVillagerInteract(final PlayerInteractEvent.EntityInteractSpecific event) {
        event.setCancellationResult(PumpkinWarden.villagerToPumpkinWarden(event.getTarget(), event.getItemStack(), event.getLevel()) ? InteractionResult.FAIL : InteractionResult.PASS);
    }
}
