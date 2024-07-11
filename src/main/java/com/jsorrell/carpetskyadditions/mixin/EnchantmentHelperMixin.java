package com.jsorrell.carpetskyadditions.mixin;

import static com.jsorrell.carpetskyadditions.helpers.SkyAdditionsEnchantmentHelper.SWIFT_SNEAK_ENCHANTABLE_TAG;

import com.jsorrell.carpetskyadditions.helpers.SkyAdditionsEnchantmentHelper;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(
            method = "getAvailableEnchantmentResults",
            at =
                    @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void forceAllowSwiftSneak(
            int modifiedEnchantingLevel,
            ItemStack stack,
            Stream<Holder<Enchantment>> possibleEnchantments,
            CallbackInfoReturnable<List<EnchantmentInstance>> cir,
            List<EnchantmentInstance> enchantmentList) {
        if (stack.getTags().anyMatch(i -> i.location().getNamespace().equals(SWIFT_SNEAK_ENCHANTABLE_TAG))) {
            var ENCHANTMENTS_SWIFT_SNEAK = Minecraft.getInstance()
                    .level
                    .registryAccess()
                    .registryOrThrow(Registries.ENCHANTMENT)
                    .getHolder(Enchantments.SWIFT_SNEAK);
            if (ENCHANTMENTS_SWIFT_SNEAK.get().value().canEnchant(stack) || stack.is(Items.BOOK)) {
                for (int level = 3; 1 <= level; --level) {
                    if (SkyAdditionsEnchantmentHelper.getSwiftSneakMinCost(level) <= modifiedEnchantingLevel
                            && modifiedEnchantingLevel <= SkyAdditionsEnchantmentHelper.getSwiftSneakMaxCost(level)) {
                        enchantmentList.add(new EnchantmentInstance(ENCHANTMENTS_SWIFT_SNEAK.get(), level));
                        break;
                    }
                }
            }
        }
    }
}
