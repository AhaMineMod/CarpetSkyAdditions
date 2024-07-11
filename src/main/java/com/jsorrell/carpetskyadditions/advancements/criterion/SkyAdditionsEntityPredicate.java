package com.jsorrell.carpetskyadditions.advancements.criterion;

import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public record SkyAdditionsEntityPredicate(
        Optional<SkyAdditionsLocationPredicate> location, Optional<SkyAdditionsLocationPredicate> steppingOnLocation) {
    public static final SkyAdditionsEntityPredicate ANY =
            new SkyAdditionsEntityPredicate(SkyAdditionsLocationPredicate.ANY, SkyAdditionsLocationPredicate.ANY);

    public boolean matches(ServerLevel level, Vec3 position, Entity entity) {
        if (this == ANY) return true;
        if (entity == null) return false;

        if (!location.get().matches(level, entity.getX(), entity.getY(), entity.getZ())) return false;

        if (steppingOnLocation.get() != SkyAdditionsLocationPredicate.ANY.get()) {
            Vec3 stepPos = Vec3.atCenterOf(entity.getOnPos());
            return steppingOnLocation.get().matches(level, stepPos.x(), stepPos.y(), stepPos.z());
        }
        return true;
    }
}
