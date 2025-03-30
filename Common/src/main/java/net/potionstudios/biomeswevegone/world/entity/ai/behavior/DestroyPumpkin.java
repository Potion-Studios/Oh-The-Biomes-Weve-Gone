package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.entity.ai.memory.BWGMemoryModuleType;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;

public class DestroyPumpkin extends Behavior<PumpkinWarden> {

    private BlockPos targetPos = BlockPos.ZERO;
    protected int tryTicks;

    public DestroyPumpkin() {
        super(Util.make(() -> ImmutableMap.of(BWGMemoryModuleType.PUMPKIN_STEMS.get(), MemoryStatus.VALUE_PRESENT)));
    }

    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PumpkinWarden entity) {
        BiomesWeveGone.LOGGER.info("Checking if Pumpkin has been destroyed.");
        return entity.canMove() && entity.getCarriedBlock() == null;
    }

    @Override
    protected void tick(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        if (!targetPos.closerToCenterThan(entity.position(), 1.0)) {
            tryTicks++;
            if (tryTicks % 40 == 0)
                entity.getNavigation().moveTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, 1.0);
        } else {
            BlockState blockState = level.getBlockState(targetPos);
            level.destroyBlock(targetPos, false, entity);
            level.gameEvent(GameEvent.BLOCK_DESTROY, targetPos, GameEvent.Context.of(entity, blockState));
            entity.setCarriedBlock(blockState);
        }
    }

    @Override
    protected boolean canStillUse(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        return entity.getBrain().getMemory(BWGMemoryModuleType.PUMPKIN_STEMS.get()).isPresent() && entity.getCarriedBlock() != null && entity.canMove();
    }

    @Override
    protected void start(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        /*
        entity.getBrain().getMemory(BWGMemoryModuleType.PUMPKIN_STEMS.get()).ifPresent(stemPositions ->
                targetPos = stemPositions.get(entity.getRandom().nextInt(stemPositions.size())));
        tryTicks = 0;

         */
    }
}
