package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.fluids.FluidPropagator;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FluidPropagator.class)
public abstract class FluidPropagatorMixin {
    @ModifyExpressionValue(method = "propagateChangedPipe", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean propagateChangedPipe(boolean original, @Local(ordinal = 2) BlockState targetState) {
        return original || (ModBlocks.MECHANICAL_PUMPS.get(DyeColor.BLACK).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.BLUE).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.BROWN).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.CYAN).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.GRAY).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.GREEN).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.LIGHT_BLUE).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.LIGHT_GRAY).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.LIME).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.MAGENTA).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.ORANGE).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.PINK).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.PURPLE).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.RED).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.WHITE).has(targetState) ||
                ModBlocks.MECHANICAL_PUMPS.get(DyeColor.YELLOW).has(targetState));
    }
}
