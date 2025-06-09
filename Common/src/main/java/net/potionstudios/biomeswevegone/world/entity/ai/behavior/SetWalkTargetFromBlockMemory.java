package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.custom.PumpkinBurrowBlock;

import java.util.Optional;

public class SetWalkTargetFromBlockMemory {
    public static OneShot<PumpkinWarden> create(
            MemoryModuleType<GlobalPos> blockTargetMemory, float speedModifier, int closeEnoughDist, int tooFarDistance, int tooLongUnreachableDuration
    ) {
        return BehaviorBuilder.create(
                instance -> instance.group(
                                instance.registered(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE), instance.absent(MemoryModuleType.WALK_TARGET), instance.present(blockTargetMemory)
                        )
                        .apply(
                                instance,
                                (memoryAccessor, memoryAccessor2, memoryAccessor3) -> (serverLevel, pumpkinWarden, l) -> {
                                    GlobalPos globalPos = instance.get(memoryAccessor3);
                                    Optional<Long> optional = instance.tryGet(memoryAccessor);
                                    if (globalPos.dimension() == serverLevel.dimension()
                                            && (optional.isEmpty() || serverLevel.getGameTime() - optional.get() <= tooLongUnreachableDuration)) {
                                        BlockPos targetPos = globalPos.pos();

                                        // Check if the memory module type is HOME
                                        if (blockTargetMemory == MemoryModuleType.HOME && serverLevel.getBlockState(targetPos).is(BWGBlocks.PUMPKIN_BURROW.get())) {
                                            Direction facing = serverLevel.getBlockState(targetPos).getValue(PumpkinBurrowBlock.FACING);
                                            targetPos = targetPos.relative(facing);
                                        }

                                        if (targetPos.distManhattan(pumpkinWarden.blockPosition()) > tooFarDistance) {
                                            Vec3 vec3 = null;
                                            int m = 0;

                                            while (vec3 == null || BlockPos.containing(vec3).distManhattan(pumpkinWarden.blockPosition()) > tooFarDistance) {
                                                vec3 = DefaultRandomPos.getPosTowards(pumpkinWarden, 15, 7, Vec3.atBottomCenterOf(targetPos), (float) (Math.PI / 2));
                                                if (++m == 1000) {
                                                    pumpkinWarden.releasePoi(blockTargetMemory);
                                                    memoryAccessor3.erase();
                                                    memoryAccessor.set(l);
                                                    return true;
                                                }
                                            }

                                            memoryAccessor2.set(new WalkTarget(vec3, speedModifier, closeEnoughDist));
                                        } else if (targetPos.distManhattan(pumpkinWarden.blockPosition()) > closeEnoughDist) {
                                            memoryAccessor2.set(new WalkTarget(targetPos, speedModifier, closeEnoughDist));
                                        }
                                    } else {
                                        pumpkinWarden.releasePoi(blockTargetMemory);
                                        memoryAccessor3.erase();
                                        memoryAccessor.set(l);
                                    }

                                    return true;
                                }
                        )
        );
    }
}
