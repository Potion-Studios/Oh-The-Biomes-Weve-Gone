package net.potionstudios.biomeswevegone.neoforge.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BWGFarmLandBlock extends net.potionstudios.biomeswevegone.world.level.block.custom.BWGFarmLandBlock implements IBlockExtension {
    public BWGFarmLandBlock(Supplier<Block> dirt) {
        super(dirt);
    }

    @Override
    public @NotNull TriState canSustainPlant(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos soilPosition, @NotNull Direction facing, @NotNull BlockState plant) {
        return TriState.TRUE;
    }
}
