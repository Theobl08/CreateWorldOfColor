//package net.theobl.createworldofcolor.contraptions.actors.psi;
//
//import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceBlockEntity;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockState;
//
//public abstract class ColoredPortableStorageInterfaceBlockEntity extends PortableStorageInterfaceBlockEntity {
//    public ColoredPortableStorageInterfaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
//        super(type, pos, state);
//    }
//
//    boolean isConnected() {
//        int timeUnit = getTransferTimeout();
//        return transferTimer >= ANIMATION && transferTimer <= timeUnit + ANIMATION;
//    }
//
//    float getExtensionDistance(float partialTicks) {
//        return (float) (Math.pow(connectionAnimation.getValue(partialTicks), 2) * distance / 2);
//    }
//}
