package net.potionstudios.biomeswevegone.world.level.block.plants.tree.fruit;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BWGFruitBlock extends Block implements BonemealableBlock {
    public static final MapCodec<BWGFruitBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            propertiesCodec(),
            ResourceKey.codec(Registries.ITEM).fieldOf("fruit").forGetter(block -> block.fruit),
            ResourceKey.codec(Registries.BLOCK).fieldOf("leaves").forGetter(block -> block.leaves)
    ).apply(instance, BWGFruitBlock::new));

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final int MAX_AGE = 3;

    private final ResourceKey<Item> fruit;
    private final ResourceKey<Block> leaves;

    public BWGFruitBlock(Properties properties, ResourceKey<Item> fruit, ResourceKey<Block> leaves) {
        super(properties);
        this.fruit = fruit;
        this.leaves = leaves;
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    protected @NotNull MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(AGE)) {
            case 0 -> Block.box(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);
            case 1 -> Block.box(4.0D, 6.0D, 4.0D, 12.0D, 16.0D, 12.0D);
            case 2 -> Block.box(3.0D, 3.0D, 3.0D, 13.0D, 16.0D, 13.0D);
            case 3 -> Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
            default -> throw new IllegalStateException("Unexpected value: " + state.getValue(AGE));
        };
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return level.registryAccess().registryOrThrow(Registries.ITEM).getOrThrow(fruit).getDefaultInstance();
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (player.isCreative() && state.getValue(AGE) != MAX_AGE && stack.is(getFruit(level))) {
            level.setBlock(pos, state.setValue(AGE, state.getValue(AGE) + 1), 2);
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (state.getValue(AGE) == MAX_AGE) {
            popResource(level, pos, level.registryAccess().registryOrThrow(Registries.ITEM).getOrThrow(fruit).getDefaultInstance());
            level.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            level.setBlock(pos, state.setValue(AGE, 0), 2);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.above()).is(leaves);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < MAX_AGE;
    }

    @Override
    public void randomTick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        int age = state.getValue(AGE);
        if (age < MAX_AGE && level.getRawBrightness(pos.above(), 0) >= 9 && random.nextInt(5) == 0)
            level.setBlock(pos, state.setValue(AGE, age + 1), 2);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, BlockState state) {
        return state.getValue(AGE) < MAX_AGE;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, BlockState state) {
        level.setBlock(pos, state.setValue(AGE, Math.min(MAX_AGE, state.getValue(AGE) + 1)), 2);
    }

    @Override
    protected void onProjectileHit(@NotNull Level level, @NotNull BlockState state, @NotNull BlockHitResult hit, @NotNull Projectile projectile) {
        if (level.isClientSide()) return;
        level.destroyBlock(hit.getBlockPos(), true, projectile);
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (context instanceof EntityCollisionContext entityCollisionContext)
            if (entityCollisionContext.getEntity() instanceof LivingEntity)
                return Shapes.empty();
        return super.getCollisionShape(state, level, pos, context);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public @NotNull Item getFruit(Level level) {
        return level.registryAccess().registryOrThrow(Registries.ITEM).getOrThrow(fruit);
    }

    public @NotNull ResourceKey<Item> getFruit() {
        return fruit;
    }

    public @NotNull LeavesBlock getLeaves(Level level) {
        return (LeavesBlock) level.registryAccess().registryOrThrow(Registries.BLOCK).getOrThrow(leaves);
    }

    public @NotNull ResourceKey<Block> getLeaves() {
        return leaves;
    }
}
