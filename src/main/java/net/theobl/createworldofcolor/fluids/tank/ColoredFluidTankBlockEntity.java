package net.theobl.createworldofcolor.fluids.tank;

import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.theobl.createworldofcolor.ModBlockEntityTypes;
import net.theobl.createworldofcolor.blockEntity.IMultiBlockEntityContainerExtension;
import net.theobl.createworldofcolor.connectivity.ModConnectivityHandler;

public class ColoredFluidTankBlockEntity extends FluidTankBlockEntity implements IMultiBlockEntityContainerExtension {
    public ColoredFluidTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        boiler = new ModBoilerData();
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
//        event.registerBlockEntity(
//                Capabilities.FluidHandler.BLOCK,
//                ModBlockEntityTypes.COLORED_TANKS.get(),
//                (be, context) -> {
//                    if (be.fluidCapability == null)
//                        be.refreshCapability();
//                    return be.fluidCapability;
//                }
//        );
        for(DyeColor color : DyeColor.values()) {
            event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                ModBlockEntityTypes.COLORED_TANKS.get(color).get(),
                (be, context) -> {
                    if (be.fluidCapability == null)
                        be.refreshCapability();
                    return be.fluidCapability;
                }
            );
        }
    }

    @Override
    protected void updateConnectivity() {
        updateConnectivity = false;
        if (level.isClientSide)
            return;
        if (!isController())
            return;
        //ModConnectivityHandler.formMulti(this);
        ConnectivityHandler.formMulti(this);
    }

    @Override
    public ColoredFluidTankBlockEntity getControllerBE() {
        if (isController() || !hasLevel())
            return this;
        BlockEntity blockEntity = level.getBlockEntity(controller);
        if (blockEntity instanceof ColoredFluidTankBlockEntity)
            return (ColoredFluidTankBlockEntity) blockEntity;
        return null;
    }

    void refreshCapability() {
        fluidCapability = handlerForCapability();
        invalidateCapabilities();
    }

    private IFluidHandler handlerForCapability() {
        return isController() ? (boiler.isActive() ? boiler.createHandler() : tankInventory)
                : ((getControllerBE() != null) ? getControllerBE().handlerForCapability() : new FluidTank(0));
    }

//    @Override
//    public void notifyMultiUpdated() {
//        BlockState state = this.getBlockState();
//        if (ColoredFluidTankBlock.isTank(state)) { // safety
//            state = state.setValue(FluidTankBlock.BOTTOM, getController().getY() == getBlockPos().getY());
//            state = state.setValue(FluidTankBlock.TOP, getController().getY() + height - 1 == getBlockPos().getY());
//            level.setBlock(getBlockPos(), state, 6);
//        }
//        super.notifyMultiUpdated();
//    }
//
//    @Override
//    public void removeController(boolean keepFluids) {
//        BlockState state = getBlockState();
//        if (ColoredFluidTankBlock.isTank(state)) {
//            state = state.setValue(FluidTankBlock.BOTTOM, true);
//            state = state.setValue(FluidTankBlock.TOP, true);
//            state = state.setValue(FluidTankBlock.SHAPE, window ? FluidTankBlock.Shape.WINDOW : FluidTankBlock.Shape.PLAIN);
//            getLevel().setBlock(worldPosition, state, 22);
//        }
//        super.removeController(keepFluids);
//    }

    public boolean canConnectWith(BlockPos pos, BlockGetter getter) {
        BlockEntity be = getter.getBlockEntity(pos);
        if (be instanceof ColoredFluidTankBlockEntity) {
            return be.getBlockState().getBlock().equals(this.getBlockState().getBlock());
        }
        return false;
    }
}
