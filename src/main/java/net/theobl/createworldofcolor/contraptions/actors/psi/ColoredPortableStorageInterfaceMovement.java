package net.theobl.createworldofcolor.contraptions.actors.psi;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceBlock;
import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceBlockEntity;
import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceMovement;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.theobl.createworldofcolor.mixin.PortableStorageInterfaceBlockEntityAccessor;

import java.util.Optional;

public class ColoredPortableStorageInterfaceMovement extends PortableStorageInterfaceMovement {

    static final String _workingPos_ = "WorkingPos";
    static final String _clientPrevPos_ = "ClientPrevPos";

    public void tick(MovementContext context) {
        if (context.world.isClientSide)
            getAnimation(context).tickChaser();

        boolean onCarriage = context.contraption instanceof CarriageContraption;
        if (onCarriage && context.motion.length() > 1 / 4f)
            return;

        if (context.world.isClientSide) {
            BlockPos pos = BlockPos.containing(context.position);
            if (!findInterface(context, pos))
                reset(context);
            return;
        }

        if (!context.data.contains(_workingPos_)) {
            if (context.stall)
                cancelStall(context);
            return;
        }

        BlockPos pos = NBTHelper.readBlockPos(context.data, _workingPos_);
        Vec3 target = VecHelper.getCenterOf(pos);

        if (!context.stall && !onCarriage
                && context.position.closerThan(target, target.distanceTo(context.position.add(context.motion))))
            context.stall = true;

        Optional<Direction> currentFacingIfValid = getCurrentFacingIfValid(context);
        if (!currentFacingIfValid.isPresent())
            return;

        PortableStorageInterfaceBlockEntity stationaryInterface =
                getStationaryInterfaceAt(context.world, pos, context.state, currentFacingIfValid.get());
        if (stationaryInterface == null) {
            reset(context);
            return;
        }

        if (((PortableStorageInterfaceBlockEntityAccessor) stationaryInterface).createworldofcolor$getConnectedEntity() == null)
            stationaryInterface.startTransferringTo(context.contraption, ((PortableStorageInterfaceBlockEntityAccessor) stationaryInterface).createworldofcolor$getDistance());

        boolean timerBelow = ((PortableStorageInterfaceBlockEntityAccessor)stationaryInterface).createworldofcolor$getTransferTimer() <= PortableStorageInterfaceBlockEntity.ANIMATION;
        stationaryInterface.keepAlive = 2;
        if (context.stall && timerBelow) {
            context.stall = false;
        }
    }

    protected boolean findInterface(MovementContext context, BlockPos pos) {
        if (context.contraption instanceof CarriageContraption cc && !cc.notInPortal())
            return false;
        Optional<Direction> currentFacingIfValid = getCurrentFacingIfValid(context);
        if (!currentFacingIfValid.isPresent())
            return false;

        Direction currentFacing = currentFacingIfValid.get();
        PortableStorageInterfaceBlockEntity psi =
                findStationaryInterface(context.world, pos, context.state, currentFacing);

        if (psi == null)
            return false;
        if (psi.isPowered())
            return false;

        context.data.put(_workingPos_, NbtUtils.writeBlockPos(psi.getBlockPos()));
        if (!context.world.isClientSide) {
            Vec3 diff = VecHelper.getCenterOf(psi.getBlockPos())
                    .subtract(context.position);
            diff = VecHelper.project(diff, Vec3.atLowerCornerOf(currentFacing.getNormal()));
            float distance = (float) (diff.length() + 1.85f - 1);
            psi.startTransferringTo(context.contraption, distance);
        } else {
            context.data.put(_clientPrevPos_, NbtUtils.writeBlockPos(pos));
            if (context.contraption instanceof CarriageContraption || context.contraption.entity.isStalled()
                    || context.motion.lengthSqr() == 0)
                getAnimation(context).chase(((PortableStorageInterfaceBlockEntityAccessor)psi).createworldofcolor$getDistance() / 2, 0.25f, LerpedFloat.Chaser.LINEAR);
        }

        return true;
    }


    private PortableStorageInterfaceBlockEntity findStationaryInterface(Level world, BlockPos pos, BlockState state, Direction facing) {
        for (int i = 0; i < 2; i++) {
            PortableStorageInterfaceBlockEntity interfaceAt =
                    getStationaryInterfaceAt(world, pos.relative(facing, i), state, facing);
            if (interfaceAt == null)
                continue;
            return interfaceAt;
        }
        return null;
    }

    private PortableStorageInterfaceBlockEntity getStationaryInterfaceAt(Level world, BlockPos pos, BlockState state, Direction facing) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof PortableStorageInterfaceBlockEntity psi))
            return null;
        BlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() != state.getBlock() && blockState.getBlock() != AllBlocks.PORTABLE_FLUID_INTERFACE.get())
            return null;
        if (blockState.getValue(PortableStorageInterfaceBlock.FACING) != facing.getOpposite())
            return null;
        if (psi.isPowered())
            return null;
        return psi;
    }

    private Optional<Direction> getCurrentFacingIfValid(MovementContext context) {
        Vec3 directionVec = Vec3.atLowerCornerOf(context.state.getValue(PortableStorageInterfaceBlock.FACING)
                .getNormal());
        directionVec = context.rotation.apply(directionVec);
        Direction facingFromVector = Direction.getNearest(directionVec.x, directionVec.y, directionVec.z);
        if (directionVec.distanceTo(Vec3.atLowerCornerOf(facingFromVector.getNormal())) > 1 / 2f)
            return Optional.empty();
        return Optional.of(facingFromVector);
    }
}
