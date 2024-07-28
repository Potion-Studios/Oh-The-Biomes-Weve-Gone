package net.potionstudios.biomeswevegone.forge.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BWGFarmLandBlock extends net.potionstudios.biomeswevegone.world.level.block.custom.BWGFarmLandBlock implements IForgeBlock {
    public BWGFarmLandBlock(Supplier<Block> dirt) {
        super(dirt);
    }

    @Override
    public boolean canSustainPlant(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Direction facing, @NotNull IPlantable plantable) {
        return true;
    }
}
