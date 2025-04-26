package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AcquirePoi;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.pathfinder.Path;
import net.potionstudios.biomeswevegone.world.entity.ai.village.poi.BWGPoiTypes;
import net.potionstudios.biomeswevegone.world.level.block.custom.PumpkinBurrowBlock;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableLong;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SetClosestPumpkinBurrowAsWalkTarget {

    public static BehaviorControl<PathfinderMob> create(float speedModifier) {
        Long2LongMap long2LongMap = new Long2LongOpenHashMap();
        MutableLong mutableLong = new MutableLong(0L);
        return BehaviorBuilder.create(
                instance -> instance.group(instance.absent(MemoryModuleType.WALK_TARGET), instance.absent(MemoryModuleType.HOME))
                        .apply(
                                instance,
                                (memoryAccessor, memoryAccessor2) -> (serverLevel, pathfinderMob, l) -> {
                                    if (serverLevel.getGameTime() - mutableLong.getValue() < 20L) {
                                        return false;
                                    } else {
                                        PoiManager poiManager = serverLevel.getPoiManager();
                                        Optional<BlockPos> optional = poiManager.findClosest(holder -> holder.is(BWGPoiTypes.PUMPKIN_BURROW), pathfinderMob.blockPosition(), 48, PoiManager.Occupancy.ANY);
                                        if (optional.isPresent() && !(optional.get().distSqr(pathfinderMob.blockPosition()) <= 4.0)) {
                                            MutableInt mutableInt = new MutableInt(0);
                                            mutableLong.setValue(serverLevel.getGameTime() + serverLevel.getRandom().nextInt(20));
                                            Predicate<BlockPos> predicate = blockPosx -> {
                                                long lx = blockPosx.asLong();
                                                if (long2LongMap.containsKey(lx)) {
                                                    return false;
                                                } else if (mutableInt.incrementAndGet() >= 5) {
                                                    return false;
                                                } else {
                                                    long2LongMap.put(lx, mutableLong.getValue() + 40L);
                                                    return true;
                                                }
                                            };
                                            Set<Pair<Holder<PoiType>, BlockPos>> set = poiManager.findAllWithType(
                                                            holder -> holder.is(BWGPoiTypes.PUMPKIN_BURROW), predicate, pathfinderMob.blockPosition(), 48, PoiManager.Occupancy.ANY
                                                    )
                                                    .collect(Collectors.toSet());
                                            Path path = AcquirePoi.findPathToPois(pathfinderMob, set);
                                            if (path != null && path.canReach()) {
                                                BlockPos blockPos = path.getTarget();
                                                Optional<Holder<PoiType>> optional2 = poiManager.getType(blockPos);
                                                if (optional2.isPresent()) {
                                                    memoryAccessor.set(new WalkTarget(blockPos.relative(serverLevel.getBlockState(blockPos).getValue(PumpkinBurrowBlock.FACING)), speedModifier, 0));
                                                    DebugPackets.sendPoiTicketCountPacket(serverLevel, blockPos);
                                                }
                                            } else if (mutableInt.getValue() < 5) {
                                                long2LongMap.long2LongEntrySet().removeIf(entry -> entry.getLongValue() < mutableLong.getValue());
                                            }

                                            return true;
                                        } else {
                                            return false;
                                        }
                                    }
                                }
                        )
        );
    }
}
