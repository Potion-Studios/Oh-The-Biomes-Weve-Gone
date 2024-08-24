package net.potionstudios.biomeswevegone.world.entity.npc;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BWGVillagerTrades {

    public static final Map<VillagerProfession, List<Pair<Integer, MerchantOffer>>> TRADES = new HashMap<>();

    public static void makeTrades() {
        TRADES.put(BWGVillagerProfessions.FORAGER.get(), List.of(
            Pair.of(1, createEmeraldForItemsOffer(Items.RED_MUSHROOM, 10, 12, 2)),
            Pair.of(1, createEmeraldForItemsOffer(Items.BROWN_MUSHROOM, 10, 12, 2)),
            Pair.of(1, createEmeraldForItemsOffer(BWGBlocks.GREEN_MUSHROOM.get(), 10, 12, 2)),
            Pair.of(2, createEmeraldForItemsOffer(BWGBlocks.WOOD_BLEWIT.get(), 8, 12, 3)),
            Pair.of(2, createItemsForEmeraldsOffer(BWGItems.WHITE_PUFFBALL_CAP.get(), 4, 5, 4, 2, 0.05f)),
            Pair.of(3, createEmeraldForItemsOffer(BWGItems.WHITE_PUFFBALL_SPORES.get(), 4, 5, 4)),
            Pair.of(4, createItemsForEmeraldsOffer(BWGBlocks.WITCH_HAZEL_BRANCH.get(),4, 9, 4, 3, 0.05f)),
            Pair.of(4, createItemsForEmeraldsOffer(BWGBlocks.WITCH_HAZEL_BLOSSOM.get(), 10, 1, 10, 3, 0.05f))
        ));
    }

    private static MerchantOffer createEmeraldForItemsOffer(ItemLike item, int cost, int maxUses, int villagerXp) {
        return new MerchantOffer(new ItemStack(item, cost), new ItemStack(Items.EMERALD), maxUses, villagerXp, 0.05F);
    }

    private static MerchantOffer createItemsForEmeraldsOffer(ItemLike item, int emeraldCost, int numberOfItems, int maxUses, int villagerXp, float priceMultiplier) {
        return new MerchantOffer(new ItemStack(Items.EMERALD, emeraldCost), new ItemStack(item, numberOfItems), maxUses, villagerXp, priceMultiplier);
    }
}
