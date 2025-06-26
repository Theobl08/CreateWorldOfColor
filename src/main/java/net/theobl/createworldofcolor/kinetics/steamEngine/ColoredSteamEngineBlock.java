package net.theobl.createworldofcolor.kinetics.steamEngine;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;

public class ColoredSteamEngineBlock extends SteamEngineBlock {

    protected final DyeColor color;
    public ColoredSteamEngineBlock(Properties properties, DyeColor color) {
        super(properties);
        this.color = color;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canAttach(pLevel, pPos, getConnectedDirection(pState).getOpposite(), color);
    }

    public static boolean canAttach(LevelReader reader, BlockPos pos, Direction direction, DyeColor color) {
        BlockPos blockpos = pos.relative(direction);
        return reader.getBlockState(blockpos).getBlock().equals(AllBlocks.FLUID_TANK.get()) || // Regular fluid tank
                reader.getBlockState(blockpos).getBlock().equals(AllBlocks.CREATIVE_FLUID_TANK.get()) || // Creative fluid tank
                reader.getBlockState(blockpos).getBlock().equals(ModBlocks.FLUID_TANKS.get(color).get()); // Fluid tank of same color
    }
}
