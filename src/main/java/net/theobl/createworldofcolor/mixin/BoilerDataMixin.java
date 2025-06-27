package net.theobl.createworldofcolor.mixin;

import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;
import com.simibubi.create.content.fluids.tank.BoilerData;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoilerData.class)
public class BoilerDataMixin {
    @Shadow
    public int attachedEngines;
    @Shadow
    public int attachedWhistles;

    @Inject(method = "evaluate", at = @At("RETURN"))
    private void evaluate(FluidTankBlockEntity controller, CallbackInfoReturnable<Boolean> cir) {
        BlockPos controllerPos = controller.getBlockPos();
        Level level = controller.getLevel();

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
                        for (DyeColor color : DyeColor.values()) {
                            if (ModBlocks.STEAM_ENGINES.get(color).has(attachedState) && SteamEngineBlock.getFacing(attachedState) == d)
                                attachedEngines++;
                            if (ModBlocks.STEAM_WHISTLES.get(color).has(attachedState)
                                    && WhistleBlock.getAttachedDirection(attachedState)
                                    .getOpposite() == d)
                                attachedWhistles++;
                        }
                    }
                }
            }
        }
    }
}
