package net.potionstudios.biomeswevegone.world.level.block.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PumpkinBurrowBlockEntity extends BlockEntity {
    public PumpkinBurrowBlockEntity(BlockPos pos, BlockState blockState) {
        super(BWGBlockEntities.PUMPKIN_BURROW.get(), pos, blockState);
    }


}
