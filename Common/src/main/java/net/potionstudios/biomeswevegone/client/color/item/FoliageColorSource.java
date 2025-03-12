package net.potionstudios.biomeswevegone.client.color.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.FoliageColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record FoliageColorSource(float temperature, float downfall) implements ItemTintSource {

    public static final MapCodec<FoliageColorSource> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(ExtraCodecs.floatRange(0.0F, 1.0F).fieldOf("temperature").forGetter(FoliageColorSource::temperature), ExtraCodecs.floatRange(0.0F, 1.0F).fieldOf("downfall").forGetter(FoliageColorSource::downfall)).apply(instance, FoliageColorSource::new));

    public FoliageColorSource() {
        this(0.5F, 1.0F);
    }

    @Override
    public int calculate(@NotNull ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
        if (entity == null || level == null) return FoliageColor.get(temperature, downfall);
        return BiomeColors.getAverageFoliageColor(level, entity.getOnPos());
    }

    @Override
    public @NotNull MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }
}
