package net.potionstudios.biomeswevegone.world.entity.ai.sensing;

import com.google.common.collect.ImmutableList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.potionstudios.biomeswevegone.world.entity.BWGEntityType;
import net.potionstudios.biomeswevegone.world.entity.ai.memory.BWGMemoryModuleType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class PumpkinWardenSensor extends Sensor<LivingEntity> {
    @Override
    public @NotNull Set<MemoryModuleType<?>> requires() {
        return Set.of(BWGMemoryModuleType.VISIBLE_PUMPKIN_WARDENS.get());
    }

    @Override
    protected void doTick(@NotNull ServerLevel level, @NotNull LivingEntity entity) {
        entity.getBrain().setMemory(BWGMemoryModuleType.VISIBLE_PUMPKIN_WARDENS.get(), getNearestPumpkinWardens(entity));
    }

    private List<LivingEntity> getNearestPumpkinWardens(LivingEntity livingEntity) {
        return ImmutableList.copyOf(getVisibleEntities(livingEntity).findAll(search -> search.getType() == BWGEntityType.PUMPKIN_WARDEN.get()));
    }

    private NearestVisibleLivingEntities getVisibleEntities(LivingEntity livingEntity) {
        return livingEntity.getBrain()
                .getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
                .orElse(NearestVisibleLivingEntities.empty());
    }
}
