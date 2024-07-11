package com.jsorrell.carpetskyadditions.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;

public class GenerateGeodeTrigger extends SimpleCriterionTrigger<GenerateGeodeTrigger.Conditions> {
    public void trigger(ServerPlayer player) {
        trigger(player, conditions -> true);
    }

    @Override
    public Codec<Conditions> codec() {
        return GenerateGeodeTrigger.Conditions.CODEC;
    }

    public record Conditions(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<GenerateGeodeTrigger.Conditions> CODEC =
                RecordCodecBuilder.create(instance -> instance.group(EntityPredicate.ADVANCEMENT_CODEC
                                .optionalFieldOf("player")
                                .forGetter(GenerateGeodeTrigger.Conditions::player))
                        .apply(instance, GenerateGeodeTrigger.Conditions::new));
    }
}
