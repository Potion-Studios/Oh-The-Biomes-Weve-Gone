package net.potionstudios.biomeswevegone.client.color.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.potionstudios.biomeswevegone.client.BiomesWeveGoneClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record BorealisIceColorSource(int defaultColor) implements ItemTintSource {
	public static final MapCodec<BorealisIceColorSource> MAP_CODEC = ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default").xmap(BorealisIceColorSource::new, BorealisIceColorSource::defaultColor);

	@Override
	public int calculate(@NotNull ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
		if (entity == null) return defaultColor;
		return BiomesWeveGoneClient.getBorealisIceColor(entity.getOnPos());
	}

	@Override
	public @NotNull MapCodec<? extends ItemTintSource> type() {
		return MAP_CODEC;
	}
}
