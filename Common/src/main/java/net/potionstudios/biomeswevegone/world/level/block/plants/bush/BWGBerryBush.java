package net.potionstudios.biomeswevegone.world.level.block.plants.bush;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BWGBerryBush extends SweetBerryBushBlock {
    protected final ResourceKey<Item> item;
    private final boolean hurtEntityInside;

    public BWGBerryBush(Properties properties, ResourceKey<Item> item, boolean hurtEntityInside) {
        super(properties);
        this.item = item;
        this.hurtEntityInside = hurtEntityInside;
    }

    public BWGBerryBush(ResourceKey<Item> item, boolean hurtEntityInside) {
        this(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH), item, hurtEntityInside);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return level.registryAccess().registryOrThrow(Registries.ITEM).getOrThrow(item).getDefaultInstance();
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        int age = state.getValue(AGE);
        if (age > 1) {
            int numberOfItems = 1 + level.random.nextInt(2);
            popResource(level, pos, new ItemStack(level.registryAccess().registryOrThrow(Registries.ITEM).getOrThrow(item), numberOfItems + ((age == MAX_AGE) ? 1 : 0)));
            level.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            BlockState blockState = state.setValue(AGE, 1);
            level.setBlock(pos, blockState, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockState));
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return InteractionResult.PASS;
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (hurtEntityInside) super.entityInside(state, level, pos, entity);
    }

    /**
     * Overrides the path type for mobs walking through the bush.getBlockPathType
     * @see net.neoforged.neoforge.common.extensions.IBlockExtension#getBlockPathType
     * @see net.minecraftforge.common.extensions.IForgeBlock#getBlockPathType
     */
    @Nullable
    PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return hurtEntityInside ? PathType.DAMAGE_OTHER : null;
    }
}
