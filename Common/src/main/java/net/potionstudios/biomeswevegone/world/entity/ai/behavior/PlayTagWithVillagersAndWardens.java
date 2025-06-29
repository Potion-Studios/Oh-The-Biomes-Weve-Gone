package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.biomeswevegone.world.entity.ai.memory.BWGMemoryModuleType;

import java.util.*;

public class PlayTagWithVillagersAndWardens {

    public static BehaviorControl<PathfinderMob> create() {
        return BehaviorBuilder.create(
                instance -> instance.group(
                                instance.present(BWGMemoryModuleType.VISIBLE_PUMPKIN_WARDENS.get()),
                                instance.present(MemoryModuleType.VISIBLE_VILLAGER_BABIES),
                                instance.absent(MemoryModuleType.WALK_TARGET),
                                instance.registered(MemoryModuleType.LOOK_TARGET),
                                instance.registered(MemoryModuleType.INTERACTION_TARGET)
                        )
                        .apply(instance, (memoryAccessor, memoryAccessor1, memoryAccessor2, memoryAccessor3, memoryAccessor4) -> (serverLevel, pathfinderMob, l) -> {
                            if (serverLevel.getRandom().nextInt(10) != 0) {
                                return false;
                            } else {
                                List<LivingEntity> list = new ArrayList<>(instance.get(memoryAccessor));
                                list.addAll(instance.get(memoryAccessor1));
                                Optional<LivingEntity> optional = list.stream().filter(livingEntity -> isFriendChasingMe(pathfinderMob, livingEntity)).findAny();
                                if (optional.isEmpty()) {
                                    Optional<LivingEntity> optional2 = findSomeoneBeingChased(list);
                                    if (optional2.isPresent())
                                        chaseKid(memoryAccessor4, memoryAccessor3, memoryAccessor2, optional2.get());
                                    else
                                        list.stream().findAny().ifPresent(livingEntity -> chaseKid(memoryAccessor4, memoryAccessor3, memoryAccessor2, livingEntity));
                                } else {
                                    for (int i = 0; i < 10; i++) {
                                        Vec3 vec3 = LandRandomPos.getPos(pathfinderMob, 20, 8);
                                        if (vec3 != null && serverLevel.isVillage(BlockPos.containing(vec3))) {
                                            memoryAccessor2.set(new WalkTarget(vec3, 0.6F, 0));
                                            break;
                                        }
                                    }
                                }
                                return true;
                            }
                        })
        );
    }

    private static void chaseKid(
            MemoryAccessor<?, LivingEntity> interactionTarget, MemoryAccessor<?, PositionTracker> lookTarget, MemoryAccessor<?, WalkTarget> walkTarget, LivingEntity kid
    ) {
        interactionTarget.set(kid);
        lookTarget.set(new EntityTracker(kid, true));
        walkTarget.set(new WalkTarget(new EntityTracker(kid, false), 0.6F, 1));
    }

    private static Optional<LivingEntity> findSomeoneBeingChased(List<LivingEntity> kids) {
        Map<LivingEntity, Integer> map = checkHowManyChasersEachFriendHas(kids);
        return map.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .filter(entry -> entry.getValue() > 0 && entry.getValue() <= 5)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    private static Map<LivingEntity, Integer> checkHowManyChasersEachFriendHas(List<LivingEntity> kids) {
        Map<LivingEntity, Integer> map = Maps.newHashMap();
        kids.stream()
                .filter(PlayTagWithVillagersAndWardens::isChasingSomeone)
                .forEach(livingEntity -> map.compute(whoAreYouChasing(livingEntity), (livingEntityx, integer) -> integer == null ? 1 : integer + 1));
        return map;
    }

    private static LivingEntity whoAreYouChasing(LivingEntity kid) {
        return kid.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
    }

    private static boolean isChasingSomeone(LivingEntity kid) {
        return kid.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).isPresent();
    }

    private static boolean isFriendChasingMe(LivingEntity entity, LivingEntity kid) {
        return kid.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).filter(livingEntity2 -> livingEntity2 == entity).isPresent();
    }
}
