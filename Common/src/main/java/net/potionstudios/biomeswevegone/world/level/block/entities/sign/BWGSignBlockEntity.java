package net.potionstudios.biomeswevegone.world.level.block.entities.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.biomeswevegone.world.level.block.entities.BWGBlockEntityType;

public class BWGSignBlockEntity extends SignBlockEntity {
    public BWGSignBlockEntity(BlockPos pos, BlockState blockState) {
        super(BWGBlockEntityType.SIGNS.get(), pos, blockState);
    }
}
