package net.potionstudios.biomeswevegone.world.entity.ai.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;

public class GoToClosestVillage {
    public static BehaviorControl<PumpkinWarden> create(float speedModifier, int closeEnoughDist) {
        return BehaviorBuilder.create((instance) -> instance.group(instance.absent(MemoryModuleType.WALK_TARGET)).apply(instance, (memoryAccessor) -> (serverLevel, pumpkinWarden, l) -> {
            if (serverLevel.isVillage(pumpkinWarden.blockPosition())) {
                return false;
            } else {
                PoiManager poiManager = serverLevel.getPoiManager();
                int j = poiManager.sectionsToVillage(SectionPos.of(pumpkinWarden.blockPosition()));
                Vec3 vec3 = null;

                for(int k = 0; k < 5; ++k) {
                    Vec3 vec32 = LandRandomPos.getPos(pumpkinWarden, 15, 7, (blockPos) -> (double)(-poiManager.sectionsToVillage(SectionPos.of(blockPos))));
                    if (vec32 != null) {
                        int m = poiManager.sectionsToVillage(SectionPos.of(BlockPos.containing(vec32)));
                        if (m < j) {
                            vec3 = vec32;
                            break;
                        }

                        if (m == j) {
                            vec3 = vec32;
                        }
                    }
                }

                if (vec3 != null) {
                    memoryAccessor.set(new WalkTarget(vec3, speedModifier, closeEnoughDist));
                }

                return true;
            }
        }));
    }
}
