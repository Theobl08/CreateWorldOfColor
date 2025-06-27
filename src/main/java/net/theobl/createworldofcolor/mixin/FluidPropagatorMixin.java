package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.fluids.FluidPropagator;
import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import com.simibubi.create.content.fluids.pump.PumpBlock;
import com.simibubi.create.content.fluids.pump.PumpBlockEntity;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.simibubi.create.content.fluids.FluidPropagator.*;

@Mixin(FluidPropagator.class)
public class FluidPropagatorMixin {
    @Inject(method = "propagateChangedPipe", at = @At(value = "INVOKE", target = "Ljava/util/Set;forEach(Ljava/util/function/Consumer;)V"))
    private static void propagateChangedPipe(LevelAccessor world, BlockPos pipePos, BlockState pipeState, CallbackInfo ci,
                                             @Local(ordinal = 1) Set<Pair<PumpBlockEntity, Direction>> discoveredPumps) {
        List<Pair<Integer, BlockPos>> frontier = new ArrayList<>();
        Set<BlockPos> visited = new HashSet<>();

        frontier.add(Pair.of(0, pipePos));

        // Visit all connected pumps to update their network
        while (!frontier.isEmpty()) {
            Pair<Integer, BlockPos> pair = frontier.remove(0);
            BlockPos currentPos = pair.getSecond();
            if (visited.contains(currentPos))
                continue;
            visited.add(currentPos);
            BlockState currentState = currentPos.equals(pipePos) ? pipeState : world.getBlockState(currentPos);
            FluidTransportBehaviour pipe = getPipe(world, currentPos);
            if (pipe == null)
                continue;
            pipe.wipePressure();

            for (Direction direction : getPipeConnections(currentState, pipe)) {
                BlockPos target = currentPos.relative(direction);
                if (world instanceof Level l && !l.isLoaded(target))
                    continue;

                BlockEntity blockEntity = world.getBlockEntity(target);
                BlockState targetState = world.getBlockState(target);
                if (blockEntity instanceof PumpBlockEntity) {
                    if (!ModBlocks.MECHANICAL_PUMPS.get(DyeColor.BLACK).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.BLUE).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.BROWN).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.CYAN).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.GRAY).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.GREEN).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.LIGHT_BLUE).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.LIGHT_GRAY).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.LIME).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.MAGENTA).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.ORANGE).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.PINK).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.PURPLE).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.RED).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.WHITE).has(targetState) &&
                    !ModBlocks.MECHANICAL_PUMPS.get(DyeColor.YELLOW).has(targetState)
                            || targetState.getValue(PumpBlock.FACING)
                            .getAxis() != direction.getAxis())
                        continue;
                    discoveredPumps.add(Pair.of((PumpBlockEntity) blockEntity, direction.getOpposite()));
                    continue;
                }
                if (visited.contains(target))
                    continue;
                FluidTransportBehaviour targetPipe = getPipe(world, target);
                if (targetPipe == null)
                    continue;
                Integer distance = pair.getFirst();
                if (distance >= getPumpRange() && !targetPipe.hasAnyPressure())
                    continue;
                if (targetPipe.canHaveFlowToward(targetState, direction.getOpposite()))
                    frontier.add(Pair.of(distance + 1, target));
            }
        }
    }
}
