package net.potionstudios.biomeswevegone.world.level.block.plants.flower;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BWGBonemealableFlowerBlock extends BWGFlowerBlock implements BonemealableBlock {

	private final Supplier<? extends Block> growableBlock;

	public BWGBonemealableFlowerBlock(Properties properties, TagKey<Block> validGround, VoxelShape shape, Supplier<? extends Block> growableBlock) {
		super(properties, validGround, shape);
		this.growableBlock = growableBlock;
	}

	public BWGBonemealableFlowerBlock(Properties properties, Supplier<? extends Block> growableBlock) {
		super(properties);
		this.growableBlock = growableBlock;
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		BlockState growableState = growableBlock.get().defaultBlockState();
		if (growableState.canSurvive(level, pos))
			if (growableBlock.get() instanceof DoublePlantBlock)
				DoublePlantBlock.placeAt(level, growableState, pos, 2);
			else level.setBlock(pos, growableState, 3);
	}
}
