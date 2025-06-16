package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.biomeswevegone.world.level.block.custom.PumpkinBurrowBlock;

import java.util.function.Predicate;

public class ValidateNearbyPoi {

	public static BehaviorControl<LivingEntity> create(Predicate<Holder<PoiType>> poiValidator, MemoryModuleType<GlobalPos> poiPosMemory) {
		return BehaviorBuilder.create(
				instance -> instance.group(instance.present(poiPosMemory)).apply(instance, memoryAccessor -> (serverLevel, livingEntity, l) -> {
					GlobalPos globalPos = instance.get(memoryAccessor);
					BlockPos blockPos = globalPos.pos();
					if (serverLevel.dimension() == globalPos.dimension() && blockPos.closerToCenterThan(livingEntity.position(), 16.0)) {
						ServerLevel serverLevel2 = serverLevel.getServer().getLevel(globalPos.dimension());
						if (serverLevel2 == null || !serverLevel2.getPoiManager().exists(blockPos, poiValidator)) {
							memoryAccessor.erase();
						} else if (burrowIsOccupied(serverLevel2, blockPos, livingEntity)) {
							memoryAccessor.erase();
							serverLevel.getPoiManager().release(blockPos);
							DebugPackets.sendPoiTicketCountPacket(serverLevel, blockPos);
						}

						return true;
					} else {
						return false;
					}
				})
		);
	}

	private static boolean burrowIsOccupied(ServerLevel level, BlockPos pos, LivingEntity entity) {
		BlockState blockState = level.getBlockState(pos);
		return blockState.getBlock() instanceof PumpkinBurrowBlock && blockState.getValue(PumpkinBurrowBlock.OCCUPIED) && !entity.isSleeping();
	}
}
