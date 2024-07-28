package net.potionstudios.biomeswevegone.world.item.tools;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWood;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWoodSet;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Joseph T. McQuigg
 */
public class ToolInteractions {

    public static void registerStrippableBlocks(BiConsumer<Block, Block> consumer) {
        BWGWoodSet.woodsets().forEach(set -> {
            consumer.accept(set.logstem(), set.strippedLogStem());
            consumer.accept(set.wood(), set.strippedWood());
        });
        consumer.accept(BWGWood.PALO_VERDE_LOG.get(), BWGWood.STRIPPED_PALO_VERDE_LOG.get());
        consumer.accept(BWGWood.PALO_VERDE_WOOD.get(), BWGWood.STRIPPED_PALO_VERDE_WOOD.get());
    }

    public static void registerFlattenables(BiConsumer<Block, BlockState> consumer) {
        consumer.accept(BWGBlocks.LUSH_GRASS_BLOCK.get(), BWGBlocks.LUSH_DIRT_PATH.get().defaultBlockState());
        consumer.accept(BWGBlocks.LUSH_DIRT.get(), BWGBlocks.LUSH_DIRT_PATH.get().defaultBlockState());
        consumer.accept(BWGBlocks.SANDY_DIRT.get(), BWGBlocks.SANDY_DIRT_PATH.get().defaultBlockState());
    }

    public static void registerTillables(BiConsumer<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> consumer) {
        consumer.accept(BWGBlocks.LUSH_GRASS_BLOCK.get(), Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(BWGBlocks.LUSH_FARMLAND.get().defaultBlockState())));
        consumer.accept(BWGBlocks.LUSH_DIRT.get(), Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(BWGBlocks.LUSH_FARMLAND.get().defaultBlockState())));
        consumer.accept(BWGBlocks.SANDY_DIRT.get(), Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(BWGBlocks.SANDY_FARMLAND.get().defaultBlockState())));
        consumer.accept(BWGBlocks.PEAT.get(), Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(Blocks.FARMLAND.defaultBlockState())));
    }
}
