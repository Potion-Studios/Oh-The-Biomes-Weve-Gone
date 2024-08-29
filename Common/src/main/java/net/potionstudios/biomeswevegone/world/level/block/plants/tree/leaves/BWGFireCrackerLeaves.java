package net.potionstudios.biomeswevegone.world.level.block.plants.tree.leaves;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.potionstudios.biomeswevegone.tags.BWGItemTags;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import org.jetbrains.annotations.NotNull;

public class BWGFireCrackerLeaves extends LeavesBlock {
    public BWGFireCrackerLeaves(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        RandomSource random = level.getRandom();
        if (itemStack.is(BWGItemTags.SHEARS) && level.getBlockState(pos.below()).is(BlockTags.DIRT)) {
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            double d = (double) pos.getX() + random.nextDouble();
            double e = (double) pos.getY() + 1.0D;
            double f = (double) pos.getZ() + random.nextDouble();
            level.setBlockAndUpdate(pos, BWGBlocks.FIRECRACKER_FLOWER_BUSH.getBlockState());
            level.neighborChanged(pos, BWGBlocks.FIRECRACKER_FLOWER_BUSH.getBlock(), pos);
            level.addParticle(ParticleTypes.HAPPY_VILLAGER, d, e, f, 0.0D, 0.0D, 0.0D);
            level.gameEvent(player, GameEvent.SHEAR, pos);
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
