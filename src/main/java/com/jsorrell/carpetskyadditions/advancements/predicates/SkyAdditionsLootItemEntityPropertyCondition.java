package com.jsorrell.carpetskyadditions.advancements.predicates;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.Set;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record SkyAdditionsLootItemEntityPropertyCondition(
        Optional<EntityPredicate> predicate, LootContext.EntityTarget entityTarget) implements LootItemCondition {

    public static final Codec<SkyAdditionsLootItemEntityPropertyCondition> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                            EntityPredicate.CODEC
                                    .optionalFieldOf("predicate")
                                    .forGetter(SkyAdditionsLootItemEntityPropertyCondition::predicate),
                            LootContext.EntityTarget.CODEC
                                    .fieldOf("entity")
                                    .forGetter(SkyAdditionsLootItemEntityPropertyCondition::entityTarget))
                    .apply(instance, SkyAdditionsLootItemEntityPropertyCondition::new));

    @Override
    public @NotNull LootItemConditionType getType() {
        return SkyAdditionsLootItemConditions.ENTITY_PROPERTIES;
    }

    @Override
    public @NotNull Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.ORIGIN, this.entityTarget.getParam());
    }

    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getParamOrNull(entityTarget.getParam());
        Vec3 origin = lootContext.getParamOrNull(LootContextParams.ORIGIN);
        return this.predicate.isPresent()
                && ((EntityPredicate) this.predicate.get()).matches(lootContext.getLevel(), origin, entity);
    }

    public static LootItemCondition.Builder entityPresent(LootContext.EntityTarget target) {
        return hasProperties(target, EntityPredicate.Builder.entity());
    }

    public static LootItemCondition.Builder hasProperties(
            LootContext.EntityTarget target, EntityPredicate.Builder predicateBuilder) {
        return () -> new SkyAdditionsLootItemEntityPropertyCondition(Optional.of(predicateBuilder.build()), target);
    }

    public static LootItemCondition.Builder hasProperties(
            LootContext.EntityTarget target, EntityPredicate entityPredicate) {
        return () -> new SkyAdditionsLootItemEntityPropertyCondition(Optional.of(entityPredicate), target);
    }
}
