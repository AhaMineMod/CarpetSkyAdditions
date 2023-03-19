package com.jsorrell.carpetskyadditions.helpers;

import com.google.common.collect.ImmutableMap;
import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffers;

public class WanderingTrader {
    public static Int2ObjectMap<TradeOffers.Factory[]> getTrades() {
        List<TradeOffers.Factory> tier1Trades =
                new ArrayList<>(Arrays.asList(TradeOffers.WANDERING_TRADER_TRADES.get(1)));
        List<TradeOffers.Factory> tier2Trades =
                new ArrayList<>(Arrays.asList(TradeOffers.WANDERING_TRADER_TRADES.get(2)));

        if (SkyAdditionsSettings.tallFlowersFromWanderingTrader) {
            Collections.addAll(
                    tier1Trades,
                    new TradeOffers.SellItemFactory(Items.SUNFLOWER, 1, 1, 12, 1),
                    new TradeOffers.SellItemFactory(Items.LILAC, 1, 1, 12, 1),
                    new TradeOffers.SellItemFactory(Items.ROSE_BUSH, 1, 1, 12, 1),
                    new TradeOffers.SellItemFactory(Items.PEONY, 1, 1, 12, 1));
        }

        if (SkyAdditionsSettings.lavaFromWanderingTrader) {
            tier2Trades.add(new TradeOffers.ProcessItemFactory(Items.BUCKET, 1, 16, Items.LAVA_BUCKET, 1, 1, 1));
        }

        return new Int2ObjectOpenHashMap<>(ImmutableMap.of(
                1,
                tier1Trades.toArray(new TradeOffers.Factory[0]),
                2,
                tier2Trades.toArray(new TradeOffers.Factory[0])));
    }
}
