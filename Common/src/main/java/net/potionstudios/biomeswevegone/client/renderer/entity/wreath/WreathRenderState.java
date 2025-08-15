package net.potionstudios.biomeswevegone.client.renderer.entity.wreath;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.core.Direction;
import net.potionstudios.biomeswevegone.world.entity.decoration.Wreath;

public class WreathRenderState extends EntityRenderState {
	public Direction direction = Direction.NORTH;
	public Wreath.Type type = Wreath.Type.DEFAULT;
}
