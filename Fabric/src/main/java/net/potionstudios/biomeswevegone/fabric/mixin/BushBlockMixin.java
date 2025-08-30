package net.potionstudios.biomeswevegone.fabric.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BushBlock.class)
public abstract class BushBlockMixin {
	/**
	 * @reason Allows crops to be placed on Blocks that extend FarmBlock
	 * @author Joseph T. McQuigg
	 */
	@Inject(method = "mayPlaceOn", at = @At("RETURN"), cancellable = true)
	protected void mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(cir.getReturnValue() || state.getBlock() instanceof FarmBlock);
	}
}
