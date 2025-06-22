package net.theobl.createworldofcolor.fluids.pipes;

import com.simibubi.create.content.decoration.bracket.BracketedBlockEntityBehaviour;
import com.simibubi.create.content.fluids.FluidPropagator;
import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import com.simibubi.create.content.fluids.pipes.*;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.theobl.createworldofcolor.ModBlockEntityTypes;
import net.theobl.createworldofcolor.ModBlocks;
import net.theobl.createworldofcolor.fluids.tank.ColoredFluidTankBlock;

import javax.annotation.Nullable;
import java.util.Arrays;

public class ColoredFluidPipeBlock extends FluidPipeBlock {
    protected final DyeColor color;

    public ColoredFluidPipeBlock(Properties properties, DyeColor color) {
        super(properties);
        this.color = color;
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        if (tryRemoveBracket(context))
            return InteractionResult.SUCCESS;
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        Direction.Axis axis = getAxis(world, pos, state);
        if (axis == null) {
            Vec3 clickLocation = context.getClickLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
            double closest = Float.MAX_VALUE;
            Direction argClosest = Direction.UP;
            for (Direction direction : Iterate.directions) {
                if (clickedFace.getAxis() == direction.getAxis())
                    continue;
                Vec3 centerOf = Vec3.atCenterOf(direction.getNormal());
                double distance = centerOf.distanceToSqr(clickLocation);
                if (distance < closest) {
                    closest = distance;
                    argClosest = direction;
                }
            }
            axis = argClosest.getAxis();
        }
        if (clickedFace.getAxis() == axis)
            return InteractionResult.PASS;
        if (!world.isClientSide) {
            withBlockEntityDo(world, pos, fpte -> fpte.getBehaviour(FluidTransportBehaviour.TYPE).interfaces.values()
                    .stream()
                    .filter(pc -> pc != null && pc.hasFlow())
                    .findAny().ifPresent($ -> AllAdvancements.GLASS_PIPE.awardTo(context.getPlayer())));
            FluidTransportBehaviour.cacheFlows(world, pos);
            world.setBlockAndUpdate(pos, ModBlocks.GLASS_FLUID_PIPES.get(color).getDefaultState()
                    .setValue(GlassFluidPipeBlock.AXIS, axis)
                    .setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED)));
            FluidTransportBehaviour.loadFlows(world, pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    private Direction.Axis getAxis(BlockGetter world, BlockPos pos, BlockState state) {
        return FluidPropagator.getStraightPipeAxis(state);
    }

    public static BlockState updateConnections(BlockAndTintGetter world, BlockPos pos, BlockState state, @Nullable Direction ignored) {
        BlockState newState = state;
        for (Direction d : Iterate.directions) {
            if (d == ignored)
                continue;
            boolean shouldConnect = canConnectTo(world, pos, state, d);
            newState = newState.setValue(PROPERTY_BY_DIRECTION.get(d), shouldConnect);
        }
        return newState;
    }

    public static boolean canConnectTo(BlockAndTintGetter world, BlockPos pos, BlockState state, Direction direction) {
        BlockPos neighbourPos = pos.relative(direction);
        BlockState neighbourState = world.getBlockState(neighbourPos);

        //if it is a colored tank, and is not the same color as the pipe, don't connect
        if (ColoredFluidTankBlock.isTank(neighbourState)) {
            ColoredFluidPipeBlock block = (ColoredFluidPipeBlock) state.getBlock();
            ColoredFluidTankBlock other = (ColoredFluidTankBlock) neighbourState.getBlock();
            if (block.color != other.getColor())
                return false;
        }

        //Has fluid capability
        if (FluidPropagator.hasFluidCapability(world, neighbourPos, direction.getOpposite()))
            return true;
        //Is a vanilla fluid target
        if (VanillaFluidTargets.canProvideFluidWithoutCapability(neighbourState))
            return true;
        BracketedBlockEntityBehaviour bracket = BlockEntityBehaviour.get(world, neighbourPos, BracketedBlockEntityBehaviour.TYPE);
        //is any type of pipe
        if (isPipe(neighbourState)) {
            //if is a colored pipe and not of the same color, then we don't connect
            if (isPipe(neighbourState) && !sameColor(state, neighbourState)) {
                return false;
            }
            //else just check for brackets and such
            return bracket == null || !bracket.isBracketPresent() ||
                    FluidPropagator.getStraightPipeAxis(neighbourState) == direction.getAxis();
        }

        //if it is a glass pipe and is not of the same color, we don't connect
        if (neighbourState.getBlock() instanceof ColoredGlassFluidPipeBlock glassPipe) {
            if (!sameColor(state, ModBlocks.FLUID_PIPES.get(glassPipe.color).getDefaultState()))
                return false;
        }

        FluidTransportBehaviour transport = BlockEntityBehaviour.get(world, neighbourPos, FluidTransportBehaviour.TYPE);

        if (transport == null) //If it can transport fluid then connect, else, don't connect;
            return false;
        return transport.canHaveFlowToward(neighbourState, direction.getOpposite());
    }

    public static boolean shouldDrawRim(BlockAndTintGetter world, BlockPos pos, BlockState state, Direction direction) {
        BlockPos offsetPos = pos.relative(direction);
        BlockState facingState = world.getBlockState(offsetPos);
        if (facingState.getBlock() instanceof EncasedPipeBlock)
            return true;
        if (!canConnectTo(world, pos, state, direction))
            return true;
        return !isPipe(facingState);
    }

    public static BlockState addOppositeSide(BlockState state, Direction dir) {
        return state.setValue(PROPERTY_BY_DIRECTION.get(dir), true).setValue(PROPERTY_BY_DIRECTION.get(dir.getOpposite()), true);
    }

    public static boolean sameColor(BlockState state, BlockState neighbour) {
        return state.getBlock().equals(neighbour.getBlock());
    }

    public static boolean isPipe(BlockState state) {
        return state.getBlock() instanceof ColoredFluidPipeBlock;
    }

    @Override
    public BlockState updateBlockState(BlockState state, Direction preferredDirection, @Nullable Direction ignore, BlockAndTintGetter world, BlockPos pos) {
        // Do nothing if we are bracketed
        BracketedBlockEntityBehaviour bracket = BlockEntityBehaviour.get(world, pos, BracketedBlockEntityBehaviour.TYPE);
        if (bracket != null && bracket.isBracketPresent())
            return state;

        // get and store initial state
        BlockState prevState = state;
        int prevStateSides = (int) Arrays.stream(Iterate.directions)
                .map(PROPERTY_BY_DIRECTION::get)
                .filter(prevState::getValue)
                .count();

        //Update pipe connections (Update sides that are not ignored)
        state = updateConnections(world, pos, state, ignore);

        // See if it has enough connections
        Direction connectedDirection = null;
        for (Direction d : Iterate.directions) {
            if (isOpenAt(state, d)) {
                if (connectedDirection != null) {
                    return state;
                }
                connectedDirection = d;
            }
        }

        // //add opposite end if only one connection
        if (connectedDirection != null) {
            return state.setValue(PROPERTY_BY_DIRECTION.get(connectedDirection.getOpposite()), true);
        }
        // return pipe facing at the opposite of the direction of the previous state
        if (prevStateSides == 2) {
            Direction foundDir = null;
            for (Direction d : Iterate.directions) {
                if (prevState.getValue(PROPERTY_BY_DIRECTION.get(d))) {
                    foundDir = d;
                    break;
                }
            }
            if (foundDir != null)
                return addOppositeSide(state, foundDir);
        }

        // Use preferred
        return addOppositeSide(state, preferredDirection);
    }


    @Override
    public BlockEntityType<? extends FluidPipeBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.COLORED_PIPES.get(color).get();
    }
}
