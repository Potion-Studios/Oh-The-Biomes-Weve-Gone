package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.potionstudios.biomeswevegone.world.entity.BWGEntityType;
import net.potionstudios.biomeswevegone.world.entity.ai.village.poi.BWGPoiTypes;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;

import java.util.Optional;

public class PumpkinWardenGoalPackages {

    private static final float SPEED_MODIFIER = 0.5F;

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super PumpkinWarden>>> getCorePackage() {
        return ImmutableList.of(
                Pair.of(0, new Swim(0.8F)),
                Pair.of(0, InteractWithDoor.create()),
                Pair.of(0, new LookAtTargetSink(45, 90)),
                Pair.of(0, new PumpkinWardenPanicTrigger()),
                Pair.of(0, ReactToBell.create()),
                Pair.of(1, new MoveToTargetSink()),
                Pair.of(4, new Eat()),
                Pair.of(6, new PlaceInContainer()),
                Pair.of(10, AcquirePoi.create(holder -> holder.is(BWGPoiTypes.PUMPKIN_BURROW), MemoryModuleType.HOME, false, Optional.of((byte)14))),
                Pair.of(10, AcquirePoi.create(holder -> holder.is(PoiTypes.MEETING), MemoryModuleType.MEETING_POINT, false, Optional.of((byte)14)))
        );
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super PumpkinWarden>>> getWorkPackage() {
        return ImmutableList.of(
                getMinimalLookBehavior(),
                Pair.of(1, new DestroyPumpkin()),
                Pair.of(5, GoToWantedItem.create(SPEED_MODIFIER, false, 10)),
                Pair.of(10, SetLookAndInteract.create(EntityType.PLAYER, 4)),
                Pair.of(20, new RunOne<>(ImmutableList.of(Pair.of(VillageBoundRandomStroll.create(SPEED_MODIFIER), 1)))),
                Pair.of(99, UpdateActivityFromSchedule.create())
        );
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super PumpkinWarden>>> getPlayPackage() {
        return ImmutableList.of(
                Pair.of(0, new MoveToTargetSink(80, 120)),
                getFullLookBehavior(),
                Pair.of(5, PlayTagWithVillagersAndWardens.create()),
                Pair.of(
                        5,
                        new RunOne<>(
                                ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_ABSENT),
                                ImmutableList.of(
                                        Pair.of(InteractWith.of(BWGEntityType.PUMPKIN_WARDEN.get(), 8, MemoryModuleType.INTERACTION_TARGET, SPEED_MODIFIER, 2), 2),
                                        Pair.of(InteractWith.of(EntityType.VILLAGER, 8, MemoryModuleType.INTERACTION_TARGET, SPEED_MODIFIER, 2), 2),
                                        Pair.of(InteractWith.of(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, SPEED_MODIFIER, 2), 1),
                                        Pair.of(VillageBoundRandomStroll.create(SPEED_MODIFIER), 1),
                                        Pair.of(SetWalkTargetFromLookTarget.create(SPEED_MODIFIER, 2), 1),
                                        Pair.of(new DoNothing(20, 40), 2)
                                )
                        )
                ),
                Pair.of(99, UpdateActivityFromSchedule.create())
        );
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super PumpkinWarden>>> getRestPackage() {
        return ImmutableList.of(
                Pair.of(2, SetWalkTargetFromBlockMemory.create(MemoryModuleType.HOME, SPEED_MODIFIER, 0, 150, 1200)),
                Pair.of(3, ValidateNearbyPoi.create(holder -> holder.is(BWGPoiTypes.PUMPKIN_BURROW), MemoryModuleType.HOME)),
                Pair.of(3, new EnterPumpkinBurrow()),
                Pair.of(
                        5,
                        new RunOne<>(
                                ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_ABSENT),
                                ImmutableList.of(
                                        Pair.of(SetClosestPumpkinBurrowAsWalkTarget.create(SPEED_MODIFIER), 1),
                                        Pair.of(InsideBrownianWalk.create(SPEED_MODIFIER), 4),
                                        Pair.of(GoToClosestVillage.create(SPEED_MODIFIER, 4), 2),
                                        Pair.of(new DoNothing(20, 40), 2)
                                )
                        )
                ),
                getMinimalLookBehavior(),
                Pair.of(99, UpdateActivityFromSchedule.create())
        );
    }

    public static ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super PumpkinWarden>>> getMeetPackage() {
        return ImmutableList.of(
                Pair.of(2, TriggerGate.triggerOneShuffled(
                        ImmutableList.of(Pair.of(StrollAroundPoi.create(MemoryModuleType.MEETING_POINT, 0.4F, 40), 2), Pair.of(SocializeAtBell.create(), 2)))
                ),
                Pair.of(10, SetLookAndInteract.create(EntityType.PLAYER, 4)),
                Pair.of(2, SetWalkTargetFromBlockMemory.create(MemoryModuleType.MEETING_POINT, SPEED_MODIFIER, 6, 100, 200)),
                Pair.of(3, ValidateNearbyPoi.create(holder -> holder.is(PoiTypes.MEETING), MemoryModuleType.MEETING_POINT)),
                getFullLookBehavior(),
                Pair.of(99, UpdateActivityFromSchedule.create()));
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super PumpkinWarden>>> getIdlePackage() {
        return ImmutableList.of(
                Pair.of(2,
                        new RunOne<>(
                                ImmutableList.of(
                                        Pair.of(InteractWith.of(BWGEntityType.PUMPKIN_WARDEN.get(), 8, MemoryModuleType.INTERACTION_TARGET, SPEED_MODIFIER, 2), 2),
                                        Pair.of(InteractWith.of(EntityType.VILLAGER, 8, MemoryModuleType.INTERACTION_TARGET, SPEED_MODIFIER, 2), 2),
                                        Pair.of(VillageBoundRandomStroll.create(SPEED_MODIFIER), 1),
                                        Pair.of(SetWalkTargetFromLookTarget.create(SPEED_MODIFIER, 2), 1),
                                        Pair.of(new DoNothing(30, 60), 1)
                                )
                        )),
                Pair.of(3, SetLookAndInteract.create(EntityType.PLAYER, 4)),
                getFullLookBehavior(),
                Pair.of(99, UpdateActivityFromSchedule.create())
        );
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super PumpkinWarden>>> getPanicPackage() {
        return ImmutableList.of(
                Pair.of(1, SetWalkTargetAwayFrom.entity(MemoryModuleType.NEAREST_HOSTILE, SPEED_MODIFIER * 1.5F, 6, false)),
                Pair.of(1, SetWalkTargetAwayFrom.entity(MemoryModuleType.HURT_BY_ENTITY, SPEED_MODIFIER * 1.5F, 6, false)),
                Pair.of(3, VillageBoundRandomStroll.create(SPEED_MODIFIER * 1.5F, 2, 2)),
                getMinimalLookBehavior()
        );
    }

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super PumpkinWarden>>> getHidePackage() {
        return ImmutableList.of(
                Pair.of(0, new Unhide())
        );
    }

    private static Pair<Integer, BehaviorControl<LivingEntity>> getFullLookBehavior() {
        return Pair.of(
                5,
                new RunOne<>(
                        ImmutableList.of(
                                Pair.of(SetEntityLookTarget.create(EntityType.CAT, 8.0F), 8),
                                Pair.of(SetEntityLookTarget.create(EntityType.VILLAGER, 8.0F), 2),
                                Pair.of(SetEntityLookTarget.create(BWGEntityType.PUMPKIN_WARDEN.get(), 8.0F), 2),
                                Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), 2),
                                Pair.of(SetEntityLookTarget.create(MobCategory.CREATURE, 8.0F), 1),
                                Pair.of(SetEntityLookTarget.create(MobCategory.WATER_CREATURE, 8.0F), 1),
                                Pair.of(SetEntityLookTarget.create(MobCategory.AXOLOTLS, 8.0F), 1),
                                Pair.of(SetEntityLookTarget.create(MobCategory.UNDERGROUND_WATER_CREATURE, 8.0F), 1),
                                Pair.of(SetEntityLookTarget.create(MobCategory.WATER_AMBIENT, 8.0F), 1),
                                Pair.of(SetEntityLookTarget.create(MobCategory.MONSTER, 8.0F), 1),
                                Pair.of(new DoNothing(30, 60), 2)
                        )
                )
        );
    }

    private static Pair<Integer, BehaviorControl<LivingEntity>> getMinimalLookBehavior() {
        return Pair.of(
                5,
                new RunOne<>(
                        ImmutableList.of(
                                Pair.of(SetEntityLookTarget.create(BWGEntityType.PUMPKIN_WARDEN.get(), 8.0F), 2),
                                Pair.of(SetEntityLookTarget.create(EntityType.VILLAGER, 8.0F), 2),
                                Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), 2),
                                Pair.of(new DoNothing(30, 60), 8)
                        )
                )
        );
    }
}
