package com.jsorrell.carpetskyadditions.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.storage.loot.LootContext;

public class ConvertSpiderTrigger extends SimpleCriterionTrigger<ConvertSpiderTrigger.Conditions> {
    public void trigger(ServerPlayer player, Spider spider, CaveSpider caveSpider) {
        LootContext spiderLootContext = EntityPredicate.createContext(player, spider);
        LootContext caveSpiderLootContext = EntityPredicate.createContext(player, caveSpider);
        trigger(player, conditions -> conditions.matches(spiderLootContext, caveSpiderLootContext));
    }

    @Override
    public Codec<ConvertSpiderTrigger.Conditions> codec() {
        return ConvertSpiderTrigger.Conditions.CODEC;
    }

    public record Conditions(
            Optional<ContextAwarePredicate> player,
            Optional<ContextAwarePredicate> spider,
            Optional<ContextAwarePredicate> caveSpider)
            implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<ConvertSpiderTrigger.Conditions> CODEC =
                RecordCodecBuilder.create(instance -> instance.group(
                                EntityPredicate.ADVANCEMENT_CODEC
                                        .optionalFieldOf("player")
                                        .forGetter(ConvertSpiderTrigger.Conditions::player),
                                EntityPredicate.ADVANCEMENT_CODEC
                                        .optionalFieldOf("spider")
                                        .forGetter(ConvertSpiderTrigger.Conditions::spider),
                                EntityPredicate.ADVANCEMENT_CODEC
                                        .optionalFieldOf("caveSpider")
                                        .forGetter(ConvertSpiderTrigger.Conditions::caveSpider))
                        .apply(instance, ConvertSpiderTrigger.Conditions::new));

        public boolean matches(LootContext spiderContext, LootContext caveSpiderContext) {
            if (spider.isEmpty() || caveSpider.isEmpty()) {
                return false;
            }

            return spider.get().matches(spiderContext) && caveSpider.get().matches(caveSpiderContext);
        }
    }
}
