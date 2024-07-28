package net.potionstudios.biomeswevegone.world.level.block.plants.vegetation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class PoisonIvyBlock extends VineBlock {
    public PoisonIvyBlock(Properties properties) {
        super(properties);
    }

    public PoisonIvyBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.GRASS).strength(0.2f).randomTicks().noCollission());
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.getDifficulty() != Difficulty.PEACEFUL)
            if (entity instanceof LivingEntity livingentity)
                if (!livingentity.isInvulnerableTo(entity.damageSources().magic()) && !livingentity.hasEffect(MobEffects.POISON))
                    livingentity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
    }
}
