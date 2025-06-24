package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.fluids.FluidPropagator;
import com.simibubi.create.content.fluids.pump.PumpBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FluidPropagator.class)
public class FluidPropagatorMixin {
    @Redirect(method = "propagateChangedPipe", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private static boolean propagateChangedPipeColored(BlockEntry<PumpBlock> instance, BlockState state, @Local(ordinal = 1) BlockState targetState) {
        if(state.getBlock() instanceof PumpBlock)
            return (!AllBlocks.MECHANICAL_PUMP.has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.BLACK).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.BLUE).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.BROWN).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.CYAN).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.GRAY).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.GREEN).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.LIGHT_BLUE).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.LIGHT_GRAY).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.LIME).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.MAGENTA).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.ORANGE).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.PINK).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.PURPLE).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.RED).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.WHITE).has(targetState) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.YELLOW).has(targetState));
        else
            return false;
    }
}
