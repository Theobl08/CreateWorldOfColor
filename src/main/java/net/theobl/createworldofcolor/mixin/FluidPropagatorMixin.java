package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.fluids.FluidPropagator;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;
import net.theobl.createworldofcolor.ModHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FluidPropagator.class)
public abstract class FluidPropagatorMixin {
    @ModifyExpressionValue(method = "propagateChangedPipe", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean propagateChangedPipe(boolean original, @Local(ordinal = 2) BlockState targetState) {
        return original || ModHelper.isColoredBlock(targetState, ModBlocks.MECHANICAL_PUMPS);
    }
}
