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
import net.potionstudios.biomeswevegone.world.entity.BWGEntities;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;

public class PumpkinWardenGoalPackages {

    private static final float SPEED_MODIFIER = 0.5F;

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super PumpkinWarden>>> getPlayPackage() {
        return ImmutableList.of(
                Pair.of(0, new MoveToTargetSink(80, 120)),
                getFullLookBehavior(),
                Pair.of(5, PlayTagWithOtherKids.create()),
                Pair.of(
                        5,
                        new RunOne<>(
                                ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_ABSENT),
                                ImmutableList.of(
                                        Pair.of(InteractWith.of(BWGEntities.PUMPKIN_WARDEN.get(), 8, MemoryModuleType.INTERACTION_TARGET, SPEED_MODIFIER, 2), 2),
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

    private static Pair<Integer, BehaviorControl<LivingEntity>> getFullLookBehavior() {
        return Pair.of(
                5,
                new RunOne<>(
                        ImmutableList.of(
                                Pair.of(SetEntityLookTarget.create(EntityType.CAT, 8.0F), 8),
                                Pair.of(SetEntityLookTarget.create(EntityType.VILLAGER, 8.0F), 2),
                                Pair.of(SetEntityLookTarget.create(BWGEntities.PUMPKIN_WARDEN.get(), 8.0F), 2),
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

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super PumpkinWarden>>> getWorkPackage() {
        return ImmutableList.of(
                Pair.of(99, UpdateActivityFromSchedule.create())
        );
    }
}
