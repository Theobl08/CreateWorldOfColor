package net.theobl.createworldofcolor.mixin;

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
    private static boolean propagateChangedPipeColored(BlockEntry<PumpBlock> instance, BlockState state) {
        if(state.getBlock() instanceof PumpBlock)
            return (!AllBlocks.MECHANICAL_PUMP.has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.BLACK).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.BLUE).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.BROWN).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.CYAN).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.GRAY).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.GREEN).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.LIGHT_BLUE).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.LIGHT_GRAY).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.LIME).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.MAGENTA).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.ORANGE).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.PINK).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.PURPLE).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.RED).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.WHITE).has(state) &&
                    !ModBlocks.FLUID_PIPES.get(DyeColor.YELLOW).has(state));
        else
            return false;
    }
}
