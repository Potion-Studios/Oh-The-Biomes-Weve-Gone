package net.potionstudios.biomeswevegone.neoforge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.potionstudios.biomeswevegone.util.BoneMealHandler;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import net.potionstudios.biomeswevegone.world.item.brewing.BWGBrewingRecipes;
import net.potionstudios.biomeswevegone.world.item.tools.ToolInteractions;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.BlockFeatures;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;

/**
 * Used for Vanilla compatibility on the Forge platform.
 * @author Joseph T. McQuigg
 */
public class VanillaCompatNeoForge {
    // FireBlock#setFlammable is private with no public equivalent, so it must be invoked reflectively.
    private static final MethodHandle SET_FLAMMABLE = resolveSetFlammable();

    private static MethodHandle resolveSetFlammable() {
        try {
            var lookup = MethodHandles.privateLookupIn(FireBlock.class, MethodHandles.lookup());
            return lookup.findVirtual(FireBlock.class, "setFlammable", MethodType.methodType(void.class, net.minecraft.world.level.block.Block.class, int.class, int.class));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() {
        ToolInteractions.registerStrippableBlocks((block, stripped) -> {
            AxeItem.STRIPPABLES = new HashMap<>(AxeItem.STRIPPABLES);
            AxeItem.STRIPPABLES.put(block, stripped);
        });
        BlockFeatures.registerFlammable((block, igniteOdds, burnOdds) -> {
            try {
                SET_FLAMMABLE.invoke((FireBlock) Blocks.FIRE, block, igniteOdds, burnOdds);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
        ToolInteractions.registerFlattenables(ShovelItem.FLATTENABLES::put);
        BlockFeatures.registerCompostables((item, chance) -> ComposterBlock.COMPOSTABLES.put(item.asItem(), chance.floatValue()));
    }

    public static void registerVanillaCompatEvents(final IEventBus bus) {
        bus.addListener(VanillaCompatNeoForge::registerTillables);
        bus.addListener(VanillaCompatNeoForge::onBoneMealUse);
        bus.addListener(VanillaCompatNeoForge::registerBrewingRecipes);
        bus.addListener(VanillaCompatNeoForge::onVillagerInteract);
    }

    /**
     * Register tillable blocks.
     * @see BlockEvent.BlockToolModificationEvent
     */
    private static void registerTillables(final BlockEvent.BlockToolModificationEvent event) {
        if (event.getItemAbility() == ItemAbilities.HOE_TILL && event.getLevel().getBlockState(event.getPos().above()).isAir()) {
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
     * Register brewing recipes.
     * @see RegisterBrewingRecipesEvent
     */
    private static void registerBrewingRecipes(final RegisterBrewingRecipesEvent event) {
        BWGBrewingRecipes.buildBrewingRecipes(event.getBuilder()::addMix);
    }

    /**
     * Handle bone meal use.
     * @see BonemealEvent
     */
    private static void onBoneMealUse(final BonemealEvent event) {
        if (!event.getLevel().isClientSide() && BoneMealHandler.bwgBoneMealEventHandler((ServerLevel) event.getLevel(), event.getPos(), event.getState())) {
            event.setSuccessful(true);
            event.getStack().shrink(1);
        }
    }

    /**
     * Handle villager interaction.
     * @see PlayerInteractEvent.EntityInteractSpecific
     */
    private static void onVillagerInteract(final PlayerInteractEvent.EntityInteractSpecific event) {
        event.setCancellationResult(PumpkinWarden.villagerToPumpkinWarden(event.getTarget(), event.getItemStack(), event.getLevel()) ? InteractionResult.SUCCESS : InteractionResult.PASS);
    }
}
