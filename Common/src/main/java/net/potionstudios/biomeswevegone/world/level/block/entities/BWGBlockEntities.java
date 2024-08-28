package net.potionstudios.biomeswevegone.world.level.block.entities;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;
import net.potionstudios.biomeswevegone.world.level.block.entities.sign.BWGHangingSignBlockEntity;
import net.potionstudios.biomeswevegone.world.level.block.entities.sign.BWGSignBlockEntity;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWood;

import java.util.function.Supplier;

public class BWGBlockEntities {
    public static final Supplier<BlockEntityType<BWGSignBlockEntity>> SIGNS = register("sign", () -> BlockEntityType.Builder.of(
            BWGSignBlockEntity::new,
            BWGWood.ASPEN.sign(), BWGWood.ASPEN.wallSign(),
            BWGWood.BAOBAB.sign(), BWGWood.BAOBAB.wallSign(),
            BWGWood.BLUE_ENCHANTED.sign(), BWGWood.BLUE_ENCHANTED.wallSign(),
            BWGWood.CIKA.sign(), BWGWood.CIKA.wallSign(),
            BWGWood.CYPRESS.sign(), BWGWood.CYPRESS.wallSign(),
            BWGWood.EBONY.sign(), BWGWood.EBONY.wallSign(),
            BWGWood.FIR.sign(), BWGWood.FIR.wallSign(),
            BWGWood.FLORUS.sign(), BWGWood.FLORUS.wallSign(),
            BWGWood.GREEN_ENCHANTED.sign(), BWGWood.GREEN_ENCHANTED.wallSign(),
            BWGWood.HOLLY.sign(), BWGWood.HOLLY.wallSign(),
            BWGWood.IRONWOOD.sign(), BWGWood.IRONWOOD.wallSign(),
            BWGWood.JACARANDA.sign(), BWGWood.JACARANDA.wallSign(),
            BWGWood.MAHOGANY.sign(), BWGWood.MAHOGANY.wallSign(),
            BWGWood.MAPLE.sign(), BWGWood.MAPLE.wallSign(),
            BWGWood.PALM.sign(), BWGWood.PALM.wallSign(),
            BWGWood.PINE.sign(), BWGWood.PINE.wallSign(),
            BWGWood.RAINBOW_EUCALYPTUS.sign(), BWGWood.RAINBOW_EUCALYPTUS.wallSign(),
            BWGWood.REDWOOD.sign(), BWGWood.REDWOOD.wallSign(),
            BWGWood.SAKURA.sign(), BWGWood.SAKURA.wallSign(),
            BWGWood.SKYRIS.sign(), BWGWood.SKYRIS.wallSign(),
            BWGWood.WHITE_MANGROVE.sign(), BWGWood.WHITE_MANGROVE.wallSign(),
            BWGWood.WILLOW.sign(), BWGWood.WILLOW.wallSign(),
            BWGWood.WITCH_HAZEL.sign(), BWGWood.WITCH_HAZEL.wallSign(),
            BWGWood.ZELKOVA.sign(), BWGWood.ZELKOVA.wallSign()));

    public static final Supplier<BlockEntityType<BWGHangingSignBlockEntity>> HANGING_SIGNS = register("hanging_sign", () -> BlockEntityType.Builder.of(
       BWGHangingSignBlockEntity::new,
       BWGWood.ASPEN.hangingSign(), BWGWood.ASPEN.wallHangingSign(),
       BWGWood.BAOBAB.hangingSign(), BWGWood.BAOBAB.wallHangingSign(),
       BWGWood.BLUE_ENCHANTED.hangingSign(), BWGWood.BLUE_ENCHANTED.wallHangingSign(),
       BWGWood.CIKA.hangingSign(), BWGWood.CIKA.wallHangingSign(),
       BWGWood.CYPRESS.hangingSign(), BWGWood.CYPRESS.wallHangingSign(),
       BWGWood.EBONY.hangingSign(), BWGWood.EBONY.wallHangingSign(),
       BWGWood.FIR.hangingSign(), BWGWood.FIR.wallHangingSign(),
       BWGWood.FLORUS.hangingSign(), BWGWood.FLORUS.wallHangingSign(),
       BWGWood.GREEN_ENCHANTED.hangingSign(), BWGWood.GREEN_ENCHANTED.wallHangingSign(),
       BWGWood.HOLLY.hangingSign(), BWGWood.HOLLY.wallHangingSign(),
       BWGWood.IRONWOOD.hangingSign(), BWGWood.IRONWOOD.wallHangingSign(),
       BWGWood.JACARANDA.hangingSign(), BWGWood.JACARANDA.wallHangingSign(),
       BWGWood.MAHOGANY.hangingSign(), BWGWood.MAHOGANY.wallHangingSign(),
       BWGWood.MAPLE.hangingSign(), BWGWood.MAPLE.wallHangingSign(),
       BWGWood.PALM.hangingSign(), BWGWood.PALM.wallHangingSign(),
       BWGWood.PINE.hangingSign(), BWGWood.PINE.wallHangingSign(),
       BWGWood.RAINBOW_EUCALYPTUS.hangingSign(), BWGWood.RAINBOW_EUCALYPTUS.wallHangingSign(),
       BWGWood.REDWOOD.hangingSign(), BWGWood.REDWOOD.wallHangingSign(),
       BWGWood.SAKURA.hangingSign(), BWGWood.SAKURA.wallHangingSign(),
       BWGWood.SKYRIS.hangingSign(), BWGWood.SKYRIS.wallHangingSign(),
       BWGWood.WHITE_MANGROVE.hangingSign(), BWGWood.WHITE_MANGROVE.wallHangingSign(),
       BWGWood.WILLOW.hangingSign(), BWGWood.WILLOW.wallHangingSign(),
       BWGWood.WITCH_HAZEL.hangingSign(), BWGWood.WITCH_HAZEL.wallHangingSign(),
       BWGWood.ZELKOVA.hangingSign(), BWGWood.ZELKOVA.wallHangingSign()));

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String key, Supplier<BlockEntityType.Builder<T>> builder) {
        return PlatformHandler.PLATFORM_HANDLER.registerBlockEntity(key, builder);
    }

    public static void blockEntities() {
        BiomesWeveGone.LOGGER.info("Registering Oh The Biomes We've Gone Block Entities");
    }
}
