package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import org.jetbrains.annotations.NotNull;

public class Eat extends Behavior<PumpkinWarden> {
    public Eat() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull PumpkinWarden pumpkinWarden) {
        return pumpkinWarden.getItemInHand(InteractionHand.MAIN_HAND).has(DataComponents.FOOD) && pumpkinWarden.getHealth() < pumpkinWarden.getMaxHealth();
    }

    @Override
    protected void start(@NotNull ServerLevel level, @NotNull PumpkinWarden entity, long gameTime) {
        entity.eat(level, entity.getItemInHand(InteractionHand.MAIN_HAND));
        entity.setHealth(entity.getHealth() + 2);
    }
}
