package net.potionstudios.biomeswevegone.world.level.block.wood.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.potionstudios.biomeswevegone.world.level.block.entities.sign.BWGSignBlockEntity;
import org.jetbrains.annotations.NotNull;

public class BWGStandingSignBlock extends StandingSignBlock {
    public BWGStandingSignBlock(Properties properties, WoodType type) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos arg, @NotNull BlockState arg2) {
        return new BWGSignBlockEntity(arg, arg2);
    }
}
