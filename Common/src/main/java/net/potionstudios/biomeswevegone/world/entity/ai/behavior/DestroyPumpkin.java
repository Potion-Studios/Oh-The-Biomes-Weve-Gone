package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
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
import java.util.function.Predicate;

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
    protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PumpkinWarden pumpkinWarden) {
        return pumpkinWarden.canMove() && pumpkinWarden.getItemInHand(InteractionHand.MAIN_HAND).isEmpty();
    }

    @Override
    protected void tick(@NotNull ServerLevel level, @NotNull PumpkinWarden pumpkinWarden, long gameTime) {
        if (targetBlock == null) return;
        if (!targetBlock.closerToCenterThan(pumpkinWarden.position(), 1.0)) {
            tryTicks++;
            if (tryTicks > 200)
                stop(level, pumpkinWarden, gameTime);
        } else if (level.getBlockState(targetBlock).is(fruitBlock)){
            BlockState blockState = level.getBlockState(targetBlock);
            level.destroyBlock(targetBlock, false, pumpkinWarden);
            level.gameEvent(GameEvent.BLOCK_DESTROY, targetBlock, GameEvent.Context.of(pumpkinWarden, blockState));
            pumpkinWarden.setItemInHand(InteractionHand.MAIN_HAND, blockState.getBlock().asItem().getDefaultInstance());
            stop(level, pumpkinWarden, gameTime);
        } else stop(level, pumpkinWarden, gameTime);
    }

    @Override
    protected boolean canStillUse(@NotNull ServerLevel level, @NotNull PumpkinWarden pumpkinWarden, long gameTime) {
        return checkExtraStartConditions(level, pumpkinWarden);
    }

    @Override
    protected void start(@NotNull ServerLevel level, @NotNull PumpkinWarden pumpkinWarden, long gameTime) {
        Optional<BlockPos> optionalBlockPos = findNearestBlock(level, pumpkinWarden.blockPosition(), blockState -> blockState.getBlock() instanceof AttachedStemBlock, 15);
        if (optionalBlockPos.isPresent()) {
            targetBlock = optionalBlockPos.get().relative(level.getBlockState(optionalBlockPos.get()).getValue(AttachedStemBlock.FACING));
            fruitBlock = level.getBlockState(targetBlock).getBlock();
            pumpkinWarden.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosTracker(targetBlock), .8F, 1));
            pumpkinWarden.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(targetBlock));
        } else stop(level, pumpkinWarden, gameTime);
    }

    private Optional<BlockPos> findNearestBlock(@NotNull ServerLevel level, BlockPos blockPos, Predicate<BlockState> predicate, double distance) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for (int i = 0; i <= distance; i = i > 0 ? -i : 1 - i)
            for (int j = 0; j < distance; j++)
                for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k)
                    for (int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                        mutableBlockPos.setWithOffset(blockPos, k, i - 1, l);
                        if (blockPos.closerThan(mutableBlockPos, distance) && predicate.test(level.getBlockState(mutableBlockPos)))
                            return Optional.of(mutableBlockPos.immutable());
                    }

        return Optional.empty();
    }

    @Override
    protected void stop(@NotNull ServerLevel level, @NotNull PumpkinWarden pumpkinWarden, long gameTime) {
        pumpkinWarden.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
        pumpkinWarden.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
    }
}
