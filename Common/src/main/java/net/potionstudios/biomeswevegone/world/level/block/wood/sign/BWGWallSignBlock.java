package net.potionstudios.biomeswevegone.world.level.block.wood.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.potionstudios.biomeswevegone.world.level.block.entities.sign.BWGSignBlockEntity;
import org.jetbrains.annotations.NotNull;

public class BWGWallSignBlock extends WallSignBlock {
    public BWGWallSignBlock(Properties properties, WoodType type) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos arg, @NotNull BlockState arg2) {
        return new BWGSignBlockEntity(arg, arg2);
    }
}
