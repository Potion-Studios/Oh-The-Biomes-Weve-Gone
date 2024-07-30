package net.potionstudios.biomeswevegone.world.level.levelgen.structure;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.potionstudios.biomeswevegone.RegistrationHandlerA;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.arch.ArchStructure;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.canyon.CanyonStructure;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.lake.LargeLakeStructure;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.plateau.GourPlateauStructure;
import net.potionstudios.biomeswevegone.world.level.levelgen.structure.sharpenedrock.SharpenedRockStructure;

import java.util.function.Supplier;

public class BWGStructureTypes {

    public static final Supplier<StructureType<SharpenedRockStructure>> SHARPENED_ROCK = create("sharpened_rock", () -> () -> SharpenedRockStructure.CODEC);
    public static final Supplier<StructureType<GourPlateauStructure>> OVERHANG_PLATEAU = create("overhang_plateau", () -> () -> GourPlateauStructure.CODEC);
    public static final Supplier<StructureType<CanyonStructure>> CANYON = create("canyon", () -> () -> CanyonStructure.CODEC);
    public static final Supplier<StructureType<LargeLakeStructure>> LARGE_LAKE = create("large_lake", () -> () -> LargeLakeStructure.CODEC);
    public static final Supplier<StructureType<ArchStructure>> ARCH = create("arch", () -> () -> ArchStructure.CODEC);

    public static <S extends Structure> Supplier<StructureType<S>> create(String id, Supplier<StructureType<S>> structureTypeSupplier) {
        return RegistrationHandlerA.REGISTRATION.register(BuiltInRegistries.STRUCTURE_TYPE, id, structureTypeSupplier);
    }

    public static void init() {}
}