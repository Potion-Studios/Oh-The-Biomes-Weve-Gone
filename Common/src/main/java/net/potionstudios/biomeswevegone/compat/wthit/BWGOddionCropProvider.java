package net.potionstudios.biomeswevegone.compat.wthit;

import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.potionstudios.biomeswevegone.world.level.block.plants.bush.OddionCrop;
import org.jspecify.annotations.NonNull;

enum BWGOddionCropProvider implements IBlockComponentProvider {

    INSTANCE;

    private static void addHatchingTooltip(ITooltip tooltip, int timer) {
        tooltip.addLine(Component.translatable("tooltip.waila.hatch_chance", (timer * 10) + "%"));
    }

    @Override
    public void appendBody(@NonNull ITooltip tooltip, @NonNull IBlockAccessor accessor, IPluginConfig config) {
        if (config.getBoolean(Identifier.withDefaultNamespace("plant.crop_progress")))
            if (accessor.getBlockState().getValue(OddionCrop.HATCHING))
                addHatchingTooltip(tooltip, accessor.getBlockState().getValue(OddionCrop.TIMER));
    }
}
