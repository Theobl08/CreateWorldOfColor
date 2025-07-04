//package net.theobl.createworldofcolor.contraptions.actors.psi;
//
//import com.simibubi.create.content.contraptions.Contraption;
//import com.simibubi.create.content.contraptions.actors.psi.PortableFluidInterfaceBlockEntity;
//import net.minecraft.MethodsReturnNonnullByDefault;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockState;
//import net.neoforged.neoforge.fluids.FluidStack;
//import net.neoforged.neoforge.fluids.capability.IFluidHandler;
//import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
//
//import javax.annotation.ParametersAreNonnullByDefault;
//
//public class ColoredPortableFluidInterfaceBlockEntity extends ColoredPortableStorageInterfaceBlockEntity {
//
//    protected IFluidHandler capability;
//
//    public ColoredPortableFluidInterfaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
//        super(type, pos, state);
//        capability = createEmptyHandler();
//    }
//
////    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
////        for (DyeColor color : DyeColor.values())
////            event.registerBlockEntity(
////                    Capabilities.FluidHandler.BLOCK,
////                    ModBlockEntityTypes.PORTABLE_FLUID_INTERFACES.get(color).get(),
////                    (be, context) -> be.capability
////            );
////    }
//
//    @Override
//    public void startTransferringTo(Contraption contraption, float distance) {
//        PortableFluidInterfaceBlockEntity be = new PortableFluidInterfaceBlockEntity(this.getType(), this.worldPosition, this.getBlockState());
//        capability = be.new InterfaceFluidHandler(contraption.getStorage().getFluids());
//        invalidateCapability();
//        super.startTransferringTo(contraption, distance);
//    }
//
//    @Override
//    protected void invalidateCapability() {
//        invalidateCapabilities();
//    }
//
//    @Override
//    protected void stopTransferring() {
//        capability = createEmptyHandler();
//        invalidateCapability();
//        super.stopTransferring();
//    }
//
//    private IFluidHandler createEmptyHandler() {
//        PortableFluidInterfaceBlockEntity be = new PortableFluidInterfaceBlockEntity(this.getType(), this.worldPosition, this.getBlockState());
//        return new ColoredInterfaceFluidHandler(new FluidTank(0), be);
//    }
//
//    @ParametersAreNonnullByDefault
//    @MethodsReturnNonnullByDefault
//    public class ColoredInterfaceFluidHandler extends PortableFluidInterfaceBlockEntity.InterfaceFluidHandler {
//
//        private final IFluidHandler wrapped;
//
//        public ColoredInterfaceFluidHandler(IFluidHandler wrapped, PortableFluidInterfaceBlockEntity be) {
//            be.super(wrapped);
//            this.wrapped = wrapped;
//        }
//
//        @Override
//        public int getTanks() {
//            return wrapped.getTanks();
//        }
//
//        @Override
//        public FluidStack getFluidInTank(int tank) {
//            return wrapped.getFluidInTank(tank);
//        }
//
//        @Override
//        public int getTankCapacity(int tank) {
//            return wrapped.getTankCapacity(tank);
//        }
//
//        @Override
//        public boolean isFluidValid(int tank, FluidStack stack) {
//            return wrapped.isFluidValid(tank, stack);
//        }
//
//        @Override
//        public int fill(FluidStack resource, FluidAction action) {
//            if (!isConnected())
//                return 0;
//            int fill = wrapped.fill(resource, action);
//            if (fill > 0 && action.execute())
//                keepAlive();
//            return fill;
//        }
//
//        @Override
//        public FluidStack drain(FluidStack resource, FluidAction action) {
//            if (!canTransfer())
//                return FluidStack.EMPTY;
//            FluidStack drain = wrapped.drain(resource, action);
//            if (!drain.isEmpty() && action.execute())
//                keepAlive();
//            return drain;
//        }
//
//        @Override
//        public FluidStack drain(int maxDrain, FluidAction action) {
//            if (!canTransfer())
//                return FluidStack.EMPTY;
//            FluidStack drain = wrapped.drain(maxDrain, action);
//            if (!drain.isEmpty() && action.execute())
//                keepAlive();
//            return drain;
//        }
//
//        public void keepAlive() {
//            onContentTransferred();
//        }
//
//    }
//}
