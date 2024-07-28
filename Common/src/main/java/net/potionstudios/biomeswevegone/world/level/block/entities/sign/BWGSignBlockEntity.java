package net.potionstudios.biomeswevegone.world.level.block.entities.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.biomeswevegone.world.level.block.entities.BWGBlockEntities;

public class BWGSignBlockEntity extends SignBlockEntity {
    public BWGSignBlockEntity(BlockPos pos, BlockState blockState) {
        super(BWGBlockEntities.SIGNS.get(), pos, blockState);
    }
}
