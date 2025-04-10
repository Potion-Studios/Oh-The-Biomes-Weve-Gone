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
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.entity.ai.memory.BWGMemoryModuleType;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DestroyPumpkin extends Behavior<PumpkinWarden> {

    private BlockPos targetBlock;
    protected int tryTicks;

    public DestroyPumpkin() {
        super(Util.make(() -> ImmutableMap.of(
                BWGMemoryModuleType.VISIBLE_PUMPKIN_STEMS.get(), MemoryStatus.VALUE_PRESENT,
                MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED,
                MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED)));
    }

    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PumpkinWarden entity) {
        return entity.canMove() && entity.getCarriedBlock() == null && entity.getBrain().getMemory(BWGMemoryModuleType.VISIBLE_PUMPKIN_STEMS.get()).isPresent() && !entity.getBrain().getMemory(BWGMemoryModuleType.VISIBLE_PUMPKIN_STEMS.get()).get().isEmpty();
    }

    @Override
    protected void tick(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        if (targetBlock == null) return;
        BiomesWeveGone.LOGGER.info("Destroying pumpkin stem at {}", targetBlock);
        if (targetBlock.closerToCenterThan(entity.position(), 1.0)) {
            tryTicks++;
        } else if (!level.getBlockState(targetBlock).isAir()){
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
        BiomesWeveGone.LOGGER.info("Found {} visible pumpkin stems", blockPosList.size());
        BlockPos blockPos = blockPosList.get(level.getRandom().nextInt(blockPosList.size()));
        Block block =  level.getBlockState(blockPos).getBlock();
        BiomesWeveGone.LOGGER.info("Stem Block is {}", block.builtInRegistryHolder());
        if (block instanceof AttachedStemBlock) {
            BiomesWeveGone.LOGGER.info("Found pumpkin stem at {}", blockPos);
            targetBlock = blockPos.relative(level.getBlockState(blockPos).getValue(AttachedStemBlock.FACING));
            BiomesWeveGone.LOGGER.info("Target stem block is {}", targetBlock);
            entity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(targetBlock, 1.0F, 0));
            entity.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(targetBlock));
            BiomesWeveGone.LOGGER.info("Memories are set, Walk target is {}, Look target is {}", entity.getBrain().getMemory(MemoryModuleType.WALK_TARGET), entity.getBrain().getMemory(MemoryModuleType.LOOK_TARGET));
        } else stop(level, entity, gameTime);
    }

    @Override
    protected void stop(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        entity.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
        entity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
    }
}
