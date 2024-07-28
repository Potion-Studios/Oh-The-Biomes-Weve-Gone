package net.potionstudios.biomeswevegone.world.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class BWGSpreadableBlock extends GrassBlock implements BonemealableBlock {

	private final Supplier<? extends Block> spreadable;

	public BWGSpreadableBlock(Properties properties, Supplier<? extends Block> spreadable) {
		super(properties);
		this.spreadable = spreadable;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!SpreadingSnowyDirtBlock.canBeGrass(state, level, pos))
			level.setBlockAndUpdate(pos, this.spreadable.get().defaultBlockState());
		else if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
			BlockState blockstate = this.defaultBlockState();
			for (int i = 0; i < 4; ++i) {
				BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
				if (level.getBlockState(blockpos).is(this.spreadable.get()) && SpreadingSnowyDirtBlock.canPropagate(blockstate, level, blockpos))
					level.setBlockAndUpdate(blockpos, blockstate.setValue(SNOWY, level.getBlockState(blockpos.above()).is(Blocks.SNOW)));
			}
		}
	}
}
