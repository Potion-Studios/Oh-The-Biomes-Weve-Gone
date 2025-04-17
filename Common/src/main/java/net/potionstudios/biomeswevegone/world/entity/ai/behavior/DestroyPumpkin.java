package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DestroyPumpkin extends Behavior<PumpkinWarden> {

    private BlockPos targetBlock;
    private Block fruitBlock;
    protected int tryTicks;

    public DestroyPumpkin() {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_ABSENT));
    }

    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PumpkinWarden entity) {
        return entity.canMove() && entity.getCarriedBlock() == null;
    }

    @Override
    protected void tick(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        if (targetBlock == null) return;
        if (!targetBlock.closerToCenterThan(entity.position(), 1.0)) {
            tryTicks++;
            if (tryTicks > 200)
                stop(level, entity, gameTime);
        } else if (level.getBlockState(targetBlock).is(fruitBlock)){
            BlockState blockState = level.getBlockState(targetBlock);
            level.destroyBlock(targetBlock, false, entity);
            level.gameEvent(GameEvent.BLOCK_DESTROY, targetBlock, GameEvent.Context.of(entity, blockState));
            entity.setCarriedBlock(blockState);
            stop(level, entity, gameTime);
        } else stop(level, entity, gameTime);
    }

    @Override
    protected boolean canStillUse(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        return checkExtraStartConditions(level, entity);
    }

    @Override
    protected void start(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        Optional<BlockPos> optionalBlockPos = findAttachedStemFruit(level, entity.blockPosition());
        if (optionalBlockPos.isPresent()) {
            targetBlock = optionalBlockPos.get().relative(level.getBlockState(optionalBlockPos.get()).getValue(AttachedStemBlock.FACING));
            fruitBlock = level.getBlockState(targetBlock).getBlock();
            entity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosTracker(targetBlock), .8F, 1));
            entity.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(targetBlock));
        } else stop(level, entity, gameTime);
    }

    private Optional<BlockPos> findAttachedStemFruit(@NotNull ServerLevel level, BlockPos entityPosition) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int y = 2; -2 <= y; y--)
            for (int x = -25; x < 25; x++)
                for (int z = -25; z < 25; z++) {
                    mutableBlockPos.setWithOffset(entityPosition, x, y, z);
                    if (level.getBlockState(mutableBlockPos).getBlock() instanceof AttachedStemBlock)
                        return Optional.of(mutableBlockPos.immutable());
                }

        return Optional.empty();
    }

    @Override
    protected void stop(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        entity.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
        entity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
    }
}
