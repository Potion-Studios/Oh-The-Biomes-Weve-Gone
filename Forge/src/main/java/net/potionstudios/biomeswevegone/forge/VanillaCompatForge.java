package net.potionstudios.biomeswevegone.forge;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.*;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.potionstudios.biomeswevegone.world.item.tools.ToolInteractions;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.BlockFeatures;

import java.util.HashMap;

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
        BlockFeatures.registerCompostables((item, chance) -> ComposterBlock.add(chance, item));
        ToolInteractions.registerFlattenables(ShovelItem.FLATTENABLES::put);
        ToolInteractions.registerTillables(HoeItem.TILLABLES::put);
    }

    public static void registerVanillaCompatEvents(IEventBus bus) {
        bus.addListener(VanillaCompatForge::registerFuels);
    }

    /**
     * Register fuels for the furnace.
     * @see FurnaceFuelBurnTimeEvent
     */
    private static void registerFuels(final FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().is(BWGBlocks.PEAT.get().asItem()))
            event.setBurnTime(1200);
    }
}
