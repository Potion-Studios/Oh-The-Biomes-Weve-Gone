package net.potionstudios.biomeswevegone.world.level.block.plants.bush;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
/*
public class NightshadeBerryBush extends BWGBerryBush {

    public NightshadeBerryBush() {
        super(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH).lightLevel((state) -> 5), () -> BWGItems.NIGHTSHADE_BERRIES, true);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int i = state.getValue(AGE);
        if (i < 3 && level.getRawBrightness(pos.above(), 0) <= 10 && random.nextInt(5) == 0)
            level.setBlock(pos, state.setValue(AGE, i + 1), 2);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.PHANTOM && entity.getType() != EntityType.ENDERMAN) {
            entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
            if (!level.isClientSide && state.getValue(AGE) > 0 && (entity.xOld != entity.getX() || entity.zOld != entity.getZ()))
                if ((org.joml.Math.abs(entity.getX() - entity.xOld)) >= (double) 0.003F || org.joml.Math.abs(entity.getZ() - entity.zOld) >= (double) 0.003F)
                    entity.hurt(entity.damageSources().sweetBerryBush(), 1.0F);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Vec3 vec3 = this.getShape(state, level, pos, CollisionContext.empty()).bounds().getCenter();
        double d0 = (double) pos.getX() + vec3.x;
        double d1 = (double) pos.getZ() + vec3.z;
        for (int i = 0; i < 3; ++i) {
            if (random.nextBoolean())
                level.addParticle(ParticleTypes.WARPED_SPORE, d0 + random.nextDouble() / 2.0D * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + 0.4D, d1 + random.nextDouble() / 2.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
        }
    }
}

 */
