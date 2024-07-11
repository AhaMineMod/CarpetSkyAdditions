package com.jsorrell.carpetskyadditions.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

public class AllayVexTrigger extends SimpleCriterionTrigger<AllayVexTrigger.Conditions> {
    @Override
    public @NotNull Codec<Conditions> codec() {
        return AllayVexTrigger.Conditions.CODEC;
    }

    public void trigger(ServerPlayer player, Vex vex, Allay allay) {
        LootContext vexLootContext = EntityPredicate.createContext(player, vex);
        LootContext allayLootContext = EntityPredicate.createContext(player, allay);
        trigger(player, conditions -> conditions.matches(vexLootContext, allayLootContext));
    }

    public record Conditions(
            Optional<ContextAwarePredicate> player,
            Optional<ContextAwarePredicate> vex,
            Optional<ContextAwarePredicate> allay)
            implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<AllayVexTrigger.Conditions> CODEC =
                RecordCodecBuilder.create(instance -> instance.group(
                                EntityPredicate.ADVANCEMENT_CODEC
                                        .optionalFieldOf("player")
                                        .forGetter(Conditions::player),
                                EntityPredicate.ADVANCEMENT_CODEC
                                        .optionalFieldOf("vex")
                                        .forGetter(Conditions::vex),
                                EntityPredicate.ADVANCEMENT_CODEC
                                        .optionalFieldOf("allay")
                                        .forGetter(Conditions::allay))
                        .apply(instance, AllayVexTrigger.Conditions::new));

        public boolean matches(LootContext vexContext, LootContext allayContext) {
            if (vex.isEmpty() || allay.isEmpty()) {
                return false;
            }

            return vex.get().matches(vexContext) && allay.get().matches(allayContext);
        }
    }
}
