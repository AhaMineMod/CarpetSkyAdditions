package com.jsorrell.carpetskyadditions.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ThrownPotion.class)
public abstract class ThrownPotionMixin extends ThrowableItemProjectile {
    @Shadow
    protected abstract boolean isLingering();

    public ThrownPotionMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    //    @Inject(
    //            method = "onHit",
    //            at =
    //                    @At(
    //                            value = "INVOKE",
    //                            target =
    //
    // "Lnet/minecraft/world/item/alchemy/PotionUtils;getMobEffects(Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;"),
    //            locals = LocalCapture.CAPTURE_FAILSOFT)
    //    private void onThickPotionCollision(HitResult result, CallbackInfo ci) {
    //        if (SkyAdditionsSettings.renewableDeepslateFromSplash) {
    //            if (potion == DeepslateConversionHelper.CONVERSION_POTION) {
    //                Vec3 hitPos = hitResult.getType() == HitResult.Type.BLOCK ? hitResult.getLocation() : position();
    //                if (isLingering()) {
    //                    // Create the cloud b/c vanilla doesn't when there are no potion effects
    //                    AreaEffectCloud cloud = new AreaEffectCloud(level(), hitPos.x(), hitPos.y(), hitPos.z());
    //                    cloud.setRadius(3.0f);
    //                    cloud.setWaitTime(10);
    //                    cloud.setRadiusPerTick(-cloud.getRadius() / cloud.getDuration());
    //                    cloud.setPotionContents(PotionContents.EMPTY.withPotion(potion));
    //                    CompoundTag nbt = stack.getTag();
    //                    if (nbt != null && nbt.contains("CustomPotionColor", Tag.TAG_ANY_NUMERIC)) {
    //                        cloud.setFixedColor(nbt.getInt("CustomPotionColor"));
    //                    }
    //                    level().addFreshEntity(cloud);
    //                } else {
    //                    DeepslateConversionHelper.convertDeepslateAtSplash(level(), hitPos);
    //                }
    //            }
    //        }
    //    }
}
