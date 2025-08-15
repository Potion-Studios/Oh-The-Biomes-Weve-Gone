package net.potionstudios.biomeswevegone.forge.client.model;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.potionstudios.biomeswevegone.world.entity.decoration.Wreath;

public class WreathBlockState {
    protected static final EnumProperty<Wreath.Type> TYPE = EnumProperty.create("type", Wreath.Type.class);

    public static final StateDefinition<Block, BlockState> STATE = new StateDefinition.Builder<Block, BlockState>(Blocks.AIR).add(TYPE).create(Block::defaultBlockState, BlockState::new);
}
