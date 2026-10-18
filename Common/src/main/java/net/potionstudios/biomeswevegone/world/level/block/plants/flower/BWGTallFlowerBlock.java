package net.potionstudios.biomeswevegone.world.level.block.plants.flower;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.potionstudios.biomeswevegone.client.particle.BWGParticles;
import net.potionstudios.biomeswevegone.tags.BWGItemTags;
import org.jetbrains.annotations.NotNull;

/**
 * A tall flower block that can only be placed on a specific block.
 * @see TallFlowerBlock
 * @author Joseph T. McQuigg
 */
public class BWGTallFlowerBlock extends TallFlowerBlock {

    public static final BooleanProperty SHEARED = BooleanProperty.create("sheared");

    /** The block that the flower can be placed on. */
    private final TagKey<Block> validGround;

    /**
     * Creates a Tall flower block that can only be placed on the given block.
     * @param properties The properties of the block.
     * @param validGround The block that the flower can be placed on.
     */
    public BWGTallFlowerBlock(Properties properties, TagKey<Block> validGround) {
        super(properties);
        this.validGround = validGround;
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(SHEARED, false));
    }

    /**
     * Creates a Tall flower block that can only be placed on dirt.
     * @see BlockTags#DIRT
     * @param properties The properties of the block.
     */
    public BWGTallFlowerBlock(Properties properties) {
        this(properties, BlockTags.DIRT);
    }

    /**
     * Checks if the block can be placed on the given block.
     * @param state The state of the block.
     * @param worldIn The world the block is in.
     * @param pos The position of the block.
     * @return If the block can be placed on the given block.
     */
    @Override
    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
        return state.is(validGround);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (stack.is(BWGItemTags.SHEARS) && !state.getValue(SHEARED)) {
            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!level.isClientSide()) {
                BlockPos otherHalfPos = state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
                BlockState otherHalfState = level.getBlockState(otherHalfPos);

                level.setBlockAndUpdate(pos, state.setValue(SHEARED, true));
                if (otherHalfState.is(this))
                    level.setBlockAndUpdate(otherHalfPos, otherHalfState.setValue(SHEARED, true));

                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                level.gameEvent(player, GameEvent.SHEAR, pos);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(SHEARED)) return;
        if (random.nextDouble() > 0.05) return;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        mutableBlockPos.set(pos.getX() + random.nextInt(-6, 6), pos.getY() + random.nextInt(-1, 3), pos.getZ() + random.nextInt(-6, 6));
        if (!level.getBlockState(mutableBlockPos).isCollisionShapeFullBlock(level, mutableBlockPos)) {
            level.addParticle(
                    BWGParticles.BUTTERFLY.get(),
                    (double)mutableBlockPos.getX() + random.nextDouble(),
                    (double)mutableBlockPos.getY() + random.nextDouble(),
                    (double)mutableBlockPos.getZ() + random.nextDouble(),
                    0.0,
                    0.0,
                    0.0
            );
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, SHEARED);
    }
}
