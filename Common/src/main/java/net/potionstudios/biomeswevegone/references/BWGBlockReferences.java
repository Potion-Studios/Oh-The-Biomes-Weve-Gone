package net.potionstudios.biomeswevegone.references;

import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.potionstudios.biomeswevegone.BiomesWeveGone;

public class BWGBlockReferences {
    public static final ResourceKey<Block> RIPE_ORCHARD_LEAVES = createKey("ripe_orchard_leaves");
    public static final ResourceKey<Block> RIPE_BAOBAB_LEAVES = createKey("ripe_baobab_leaves");
    public static final ResourceKey<Block> GREEN_APPLE_SKYRIS_LEAVES = createKey("green_apple_skyris_leaves");
    public static final ResourceKey<Block> RIPE_YUCCA_LEAVES = createKey("ripe_yucca_leaves");
    public static final ResourceKey<Block> FLOWERING_SPIRIT_LEAVES = createKey("flowering_spirit_leaves");
    public static final ResourceKey<Block> RIPE_PALISADE_LEAVES = createKey("ripe_palisade_leaves");
    public static final ResourceKey<Block> PALE_PUMPKIN = createKey("pale_pumpkin");
    public static final ResourceKey<Block> PALE_PUMPKIN_STEM = createKey("pale_pumpkin_stem");
    public static final ResourceKey<Block> ATTACHED_PALE_PUMPKIN_STEM = createKey("attached_pale_pumpkin_stem");

    private static ResourceKey<Block> createKey(String id) {
        return BiomesWeveGone.key(Registries.BLOCK, id);
    }
}
