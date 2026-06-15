package net.potionstudios.biomeswevegone.world.entity.animal.horse;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.potionstudios.biomeswevegone.world.item.BWGItems;

public class BWGHorse {
    public static InteractionResult mobInteract(Horse horse, Player player, InteractionHand hand) {
        boolean bl = !horse.isBaby() && horse.isTamed() && player.isSecondaryUseActive();
        if (!horse.isVehicle() && !bl) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (itemStack.is(BWGItems.GREEN_APPLE.get())) {
                InteractionResult result = horse.fedFood(player, Items.APPLE.getDefaultInstance());
                if (!player.getAbilities().instabuild)
                    itemStack.shrink(1);
                if (result == InteractionResult.CONSUME)
                    return InteractionResult.SUCCESS;
                return result;
            }
        }
        return InteractionResult.PASS;
    }
}
