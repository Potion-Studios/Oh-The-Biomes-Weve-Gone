package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation.pumpkin;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PaleCarvedPumpkinBlock extends CarvedPumpkinBlock {
    public PaleCarvedPumpkinBlock(Properties properties) {
        super(properties);
    }

    public PaleCarvedPumpkinBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.JACK_O_LANTERN));
    }
}
