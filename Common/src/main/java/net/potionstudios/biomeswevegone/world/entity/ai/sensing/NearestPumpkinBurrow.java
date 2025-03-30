package net.potionstudios.biomeswevegone.world.entity.ai.sensing;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.AcquirePoi;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.pathfinder.Path;
import net.potionstudios.biomeswevegone.world.entity.ai.village.poi.BWGPoiTypes;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NearestPumpkinBurrow extends Sensor<PumpkinWarden> {
    private final Long2LongMap batchCache = new Long2LongOpenHashMap();
    private int triedCount;
    private long lastUpdate;

    public NearestPumpkinBurrow() {
        super(20);
    }

    @Override
    protected void doTick(@NotNull ServerLevel level, @NotNull PumpkinWarden entity) {
        this.triedCount = 0;
        this.lastUpdate = level.getGameTime() + level.getRandom().nextInt(20);
        PoiManager poiManager = level.getPoiManager();
        Predicate<BlockPos> predicate = blockPosx -> {
            long l = blockPosx.asLong();
            if (this.batchCache.containsKey(l)) {
                return false;
            } else if (++this.triedCount >= 5) {
                return false;
            } else {
                this.batchCache.put(l, this.lastUpdate + 40L);
                return true;
            }
        };
        Set<Pair<Holder<PoiType>, BlockPos>> set = poiManager.findAllWithType(
                        holder -> holder.is(BWGPoiTypes.PUMPKIN_BURROW), predicate, entity.blockPosition(), 48, PoiManager.Occupancy.ANY
                )
                .collect(Collectors.toSet());
        Path path = AcquirePoi.findPathToPois(entity, set);
        if (path != null && path.canReach()) {
            BlockPos blockPos = path.getTarget();
            Optional<Holder<PoiType>> optional = poiManager.getType(blockPos);
            if (optional.isPresent()) {
                entity.getBrain().setMemory(MemoryModuleType.HOME, new GlobalPos(level.dimension(), blockPos));
            }
        } else if (this.triedCount < 5) {
            this.batchCache.long2LongEntrySet().removeIf(entry -> entry.getLongValue() < this.lastUpdate);
        }
    }

    @Override
    public @NotNull Set<MemoryModuleType<?>> requires() {
        return Set.of(MemoryModuleType.HOME);
    }
}
