package net.potionstudios.biomeswevegone.world.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.potionstudios.biomeswevegone.world.entity.decoration.Wreath;
import org.jetbrains.annotations.NotNull;

public class WreathItem extends Item {
    private final Wreath.Type type;
    public WreathItem(Properties properties, Wreath.Type type) {
        super(properties);
        this.type = type;
    }

    protected boolean mayPlace(Player player, @NotNull Direction direction, @NotNull ItemStack hangingEntityStack, @NotNull BlockPos pos) {
        return !player.level().isOutsideBuildHeight(pos) && player.mayUseItemAt(pos, direction, hangingEntityStack);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        BlockPos blockPos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos blockPos2 = blockPos.relative(direction);
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        if (player != null && !this.mayPlace(player, direction, itemStack, blockPos2)) {
            return InteractionResult.FAIL;
        } else {
            Level level = context.getLevel();
            if (!level.isClientSide()) {
                Wreath wreath = new Wreath(level, blockPos2, direction, type);
                wreath.playPlacementSound();
                level.gameEvent(player, GameEvent.ENTITY_PLACE, wreath.position());
                wreath.setPos(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
                level.addFreshEntity(wreath);
            }
            itemStack.shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
    }

    public Wreath.Type getType() {
        return type;
    }
}
