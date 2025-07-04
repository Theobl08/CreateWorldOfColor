//package net.theobl.createworldofcolor.contraptions.actors.psi;
//
//import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceBlock;
//import dev.engine_room.flywheel.api.instance.Instance;
//import dev.engine_room.flywheel.api.instance.InstancerProvider;
//import dev.engine_room.flywheel.lib.instance.InstanceTypes;
//import dev.engine_room.flywheel.lib.instance.TransformedInstance;
//import dev.engine_room.flywheel.lib.model.Models;
//import net.createmod.catnip.math.AngleHelper;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.world.item.DyeColor;
//import net.minecraft.world.level.block.state.BlockState;
//
//import java.util.function.Consumer;
//
//public class ColoredPIInstance {
//    protected final DyeColor color;
//
//    private final InstancerProvider instancerProvider;
//    private final BlockState blockState;
//    private final BlockPos instancePos;
//    private final float angleX;
//    private final float angleY;
//
//    private boolean lit;
//    TransformedInstance middle;
//    TransformedInstance top;
//
//    public ColoredPIInstance(InstancerProvider instancerProvider, BlockState blockState, BlockPos instancePos, boolean lit, DyeColor color) {
//        this.color = color;
//
//        this.instancerProvider = instancerProvider;
//        this.blockState = blockState;
//        this.instancePos = instancePos;
//        Direction facing = blockState.getValue(PortableStorageInterfaceBlock.FACING);
//        angleX = facing == Direction.UP ? 0 : facing == Direction.DOWN ? 180 : 90;
//        angleY = AngleHelper.horizontalAngle(facing);
//        this.lit = lit;
//
//        middle = instancerProvider.instancer(InstanceTypes.TRANSFORMED, Models.partial(ColoredPortableStorageInterfaceRenderer.getMiddleForState(blockState, lit, color)))
//                .createInstance();
//        top = instancerProvider.instancer(InstanceTypes.TRANSFORMED, Models.partial(ColoredPortableStorageInterfaceRenderer.getTopForState(blockState, color)))
//                .createInstance();
//    }
//
//    public void beginFrame(float progress) {
//        middle.setIdentityTransform()
//                .translate(instancePos)
//                .center()
//                .rotateYDegrees(angleY)
//                .rotateXDegrees(angleX)
//                .uncenter();
//
//        top.setIdentityTransform()
//                .translate(instancePos)
//                .center()
//                .rotateYDegrees(angleY)
//                .rotateXDegrees(angleX)
//                .uncenter();
//
//        middle.translate(0, progress * 0.5f + 0.375f, 0);
//        top.translate(0, progress, 0);
//
//        middle.setChanged();
//        top.setChanged();
//    }
//
//    public void tick(boolean lit) {
//        if (this.lit != lit) {
//            this.lit = lit;
//            instancerProvider.instancer(InstanceTypes.TRANSFORMED, Models.partial(ColoredPortableStorageInterfaceRenderer.getMiddleForState(blockState, lit, color)))
//                    .stealInstance(middle);
//        }
//    }
//
//    public void remove() {
//        middle.delete();
//        top.delete();
//    }
//
//    public void collectCrumblingInstances(Consumer<Instance> consumer) {
//        consumer.accept(middle);
//        consumer.accept(top);
//    }
//}
