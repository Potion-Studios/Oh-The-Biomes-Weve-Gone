package net.potionstudios.biomeswevegone;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.potionstudios.biomeswevegone.advancements.critereon.BWGEntitySubPredicates;
import net.potionstudios.biomeswevegone.component.BWGDataComponents;
import net.potionstudios.biomeswevegone.config.configs.BWGMiscConfig;
import net.potionstudios.biomeswevegone.config.configs.BWGMobSpawnConfig;
import net.potionstudios.biomeswevegone.sounds.BWGSounds;
import net.potionstudios.biomeswevegone.compat.vanilla.dispenser.BWGDispenseItemBehavior;
import net.potionstudios.biomeswevegone.tags.BWGEntityTypeTags;
import net.potionstudios.biomeswevegone.world.entity.BWGEntityType;
import net.potionstudios.biomeswevegone.world.entity.ai.memory.BWGMemoryModuleType;
import net.potionstudios.biomeswevegone.world.entity.ai.sensing.BWGSensorType;
import net.potionstudios.biomeswevegone.world.entity.ai.village.poi.BWGPoiTypes;
import net.potionstudios.biomeswevegone.world.entity.npc.BWGVillagerProfessions;
import net.potionstudios.biomeswevegone.world.entity.npc.BWGVillagerTypes;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import net.potionstudios.biomeswevegone.world.entity.schedule.BWGSchedule;
import net.potionstudios.biomeswevegone.world.item.BWGCreativeTabs;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.entities.BWGBlockEntityType;
import net.potionstudios.biomeswevegone.world.level.levelgen.blockpredicates.BWGBlockPredicateTypes;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.BWGFeatures;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.configured.BWGConfiguredFeatures;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.placed.BWGPlacedFeatures;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.stateproviders.BWGStateProviders;
import net.potionstudios.biomeswevegone.world.level.levelgen.feature.treedecorators.BWGTreeDecorators;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.BWGStructurePieceTypes;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.BWGStructureTypes;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.BWGTemplatePools;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.BWGCustomStructureProcessors;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.village.PlaceInVillage;
import net.potionstudios.biomeswevegone.world.level.levelgen.surfacerules.BWGRuleSources;
import net.potionstudios.biomeswevegone.world.level.saveddata.maps.BWGMapDecorationTypes;
import org.slf4j.Logger;

/**
 * The main class for Oh The Biomes We've Gone.
 * @author Joseph T. McQuigg
 */
public class BiomesWeveGone {

    /** The mod id for Oh The Biomes We've Gone. */
    public static final String MOD_ID = "biomeswevegone";

    /** The logger for Oh The Biomes We've Gone. */
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Initializes the mod.
     */
    public static void init() {
        BWGDataComponents.dataComponents();
        BWGItems.items();
        BWGBlocks.blocks();
        BWGBlockEntityType.blockEntities();
        BWGEntityType.entities();
        BWGCreativeTabs.tabs();
        BWGSounds.sounds();
        BWGBlockPredicateTypes.blockPredicateTypes();
        BWGStateProviders.stateProviders();
        BWGTreeDecorators.treeDecorators();
        BWGFeatures.features();
        BWGStructurePieceTypes.structurePieceTypes();
        BWGStructureTypes.structureTypes();
        BWGConfiguredFeatures.configuredFeatures();
        BWGPlacedFeatures.placedFeatures();
        BWGRuleSources.ruleSources();
        BWGTemplatePools.templatePools();
        BWGPoiTypes.poiTypes();
        BWGVillagerProfessions.professions();
        BWGCustomStructureProcessors.processors();
        BWGVillagerTypes.villagerTypes();
        BWGSchedule.schedules();
        BWGMemoryModuleType.memoryModuleTypes();
        BWGSensorType.sensorTypes();
        BWGMapDecorationTypes.mapDecorationTypes();
        BWGEntitySubPredicates.entitySubPredicates();
    }

    /**
     * Ran later in the initialization process to set up common things.
     */
    public static void commonSetup() {
        BWGMobSpawnConfig.reload();
        BWGMiscConfig.reload();
    }

    /**
     * Ran after the mod is fully loaded to finish setup.
     */
    public static void postInit() {
        BWGDispenseItemBehavior.registerDispenseItemBehavior();
    }

    /**
     * Runs on Server Start.
     * @param server the server to run on
     */
    public static void serverStart(MinecraftServer server) {
        PlaceInVillage.addStructuresToVillages(server);
    }

    /**
     * Runs when an entity is loaded.
     * This is used to add an attack goal to monsters that should attack Pumpkin Wardens.
     * @param entity the entity that is loaded
     */
    public static void onEntityLoad(Entity entity) {
        if (entity instanceof Mob mob && entity.getType().is(BWGEntityTypeTags.ATTACKS_PUMPKIN_WARDEN))
            mob.goalSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, PumpkinWarden.class, false));
    }

    /**
     * Creates a new resource location for Oh The Biomes We've Gone.
     * @param name the name of the resource
     * @return the new resource location with the Biomes We've Gone location
     */
    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    /**
     * Creates a new resource key for Oh The Biomes We've Gone.
     * @param registryKey the registry key for the resource
     * @param name the name of the resource
     * @return the new resource key with the Biomes We've Gone location
     */
    public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registryKey, String name) {
        return ResourceKey.create(registryKey, id(name));
    }
}
