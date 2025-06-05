package net.potionstudios.biomeswevegone.world.level.block.entities.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.biomeswevegone.world.level.block.entities.BWGBlockEntityType;
import org.jetbrains.annotations.NotNull;

public class BWGHangingSignBlockEntity extends HangingSignBlockEntity {
	public BWGHangingSignBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(blockPos, blockState);
	}

	@Override
	public @NotNull BlockEntityType<?> getType() {
		return BWGBlockEntityType.HANGING_SIGNS.get();
	}

	@Override
	public boolean isValidBlockState(@NotNull BlockState blockState) {
		return getType().isValid(blockState);
	}
}
