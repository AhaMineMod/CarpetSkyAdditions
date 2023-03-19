package com.jsorrell.carpetskyadditions.helpers;

import com.jsorrell.carpetskyadditions.settings.SkyAdditionsSettings;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class LightningConverter {
    public static void strike(World world, BlockPos pos) {
        BlockState rawHitBlock = world.getBlockState(pos);
        BlockPos hitBlockPos;
        BlockState hitBlock;
        if (rawHitBlock.isOf(Blocks.LIGHTNING_ROD)) {
            hitBlockPos = pos.offset(rawHitBlock.get(LightningRodBlock.FACING).getOpposite());
            hitBlock = world.getBlockState(hitBlockPos);
        } else {
            hitBlockPos = pos;
            hitBlock = rawHitBlock;
        }

        alchemizeVinesToGlowLichen(world, hitBlockPos, hitBlock);
    }

    protected static void alchemizeVinesToGlowLichen(World world, BlockPos hitBlockPos, BlockState hitBlock) {
        if (!(SkyAdditionsSettings.lightningElectrifiesVines && hitBlock.isOf(Blocks.GLOWSTONE))) return;

        for (Direction dir : Direction.values()) {
            BlockPos adjacentBlockPos = hitBlockPos.add(dir.getVector());
            BlockState adjacentBlock = world.getBlockState(adjacentBlockPos);
            Direction opDir = dir.getOpposite();
            if (adjacentBlock.isOf(Blocks.VINE) && adjacentBlock.get(VineBlock.getFacingProperty(opDir))) {
                BlockState glowLichen =
                        Blocks.GLOW_LICHEN.getDefaultState().with(GlowLichenBlock.getProperty(opDir), true);
                world.setBlockState(adjacentBlockPos, glowLichen);
            }
        }
    }
}
