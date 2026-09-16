package net.potionstudios.biomeswevegone.world.level.block.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FrostedAmberBlockEntity extends BlockEntity {
    public FrostedAmberBlockEntity(BlockPos pos, BlockState blockState) {
        super(BWGBlockEntityType.FROSTED_AMBER.get(), pos, blockState);
    }
}
