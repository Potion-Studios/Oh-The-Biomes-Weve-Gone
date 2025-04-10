package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.Util;
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
import net.potionstudios.biomeswevegone.world.entity.ai.memory.BWGMemoryModuleType;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DestroyPumpkin extends Behavior<PumpkinWarden> {

    private BlockPos targetBlock;
    private Block fruitBlock;
    protected int tryTicks;

    public DestroyPumpkin() {
        super(Util.make(() -> ImmutableMap.of(
                BWGMemoryModuleType.VISIBLE_PUMPKIN_STEMS.get(), MemoryStatus.VALUE_PRESENT,
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_ABSENT)));
    }

    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PumpkinWarden entity) {
        return entity.canMove() && entity.getCarriedBlock() == null && entity.getBrain().getMemory(BWGMemoryModuleType.VISIBLE_PUMPKIN_STEMS.get()).isPresent() && !entity.getBrain().getMemory(BWGMemoryModuleType.VISIBLE_PUMPKIN_STEMS.get()).get().isEmpty();
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
        }
    }

    @Override
    protected boolean canStillUse(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        return entity.getCarriedBlock() == null && entity.canMove() && entity.getBrain().getMemory(BWGMemoryModuleType.VISIBLE_PUMPKIN_STEMS.get()).isPresent();
    }

    @Override
    protected void start(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        List<BlockPos> blockPosList = entity.getBrain().getMemory(BWGMemoryModuleType.VISIBLE_PUMPKIN_STEMS.get()).get();
        BlockPos blockPos = blockPosList.get(level.getRandom().nextInt(blockPosList.size()));
        Block block =  level.getBlockState(blockPos).getBlock();
        if (block instanceof AttachedStemBlock) {
            targetBlock = blockPos.relative(level.getBlockState(blockPos).getValue(AttachedStemBlock.FACING));
            fruitBlock = level.getBlockState(targetBlock).getBlock();
            entity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosTracker(targetBlock), .8F, 1));
            entity.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(targetBlock));
        } else stop(level, entity, gameTime);
    }

    @Override
    protected void stop(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        entity.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
        entity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        entity.getBrain().eraseMemory(BWGMemoryModuleType.VISIBLE_PUMPKIN_STEMS.get());
    }
}
