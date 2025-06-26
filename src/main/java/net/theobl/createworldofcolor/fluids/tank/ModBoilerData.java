package net.theobl.createworldofcolor.fluids.tank;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;
import com.simibubi.create.content.fluids.tank.BoilerData;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;

public class ModBoilerData extends BoilerData {

    public boolean evaluate(FluidTankBlockEntity controller) {
        BlockPos controllerPos = controller.getBlockPos();
        Level level = controller.getLevel();
        int prevEngines = attachedEngines;
        int prevWhistles = attachedWhistles;
        attachedEngines = 0;
        attachedWhistles = 0;

        for (int yOffset = 0; yOffset < controller.getHeight(); yOffset++) {
            for (int xOffset = 0; xOffset < controller.getWidth(); xOffset++) {
                for (int zOffset = 0; zOffset < controller.getWidth(); zOffset++) {

                    BlockPos pos = controllerPos.offset(xOffset, yOffset, zOffset);
                    BlockState blockState = level.getBlockState(pos);
                    if (!FluidTankBlock.isTank(blockState))
                        continue;
                    for (Direction d : Iterate.directions) {
                        BlockPos attachedPos = pos.relative(d);
                        BlockState attachedState = level.getBlockState(attachedPos);
                        ColoredFluidTankBlock coloredTank = (ColoredFluidTankBlock) blockState.getBlock();
                        if ((AllBlocks.STEAM_ENGINE.has(attachedState) || ModBlocks.STEAM_ENGINES.get(coloredTank.getColor()).has(attachedState)) &&
                                SteamEngineBlock.getFacing(attachedState) == d)
                            attachedEngines++;
                        if (AllBlocks.STEAM_WHISTLE.has(attachedState)
                                && WhistleBlock.getAttachedDirection(attachedState)
                                .getOpposite() == d)
                            attachedWhistles++;
                    }
                }
            }
        }

        needsHeatLevelUpdate = true;
        return prevEngines != attachedEngines || prevWhistles != attachedWhistles;
    }
}
