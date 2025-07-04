//package net.theobl.createworldofcolor.contraptions.actors.psi;
//
//import dev.engine_room.flywheel.api.instance.Instance;
//import dev.engine_room.flywheel.api.visual.DynamicVisual;
//import dev.engine_room.flywheel.api.visual.TickableVisual;
//import dev.engine_room.flywheel.api.visualization.VisualizationContext;
//import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
//import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
//import dev.engine_room.flywheel.lib.visual.SimpleTickableVisual;
//import net.minecraft.world.item.DyeColor;
//
//import java.util.function.Consumer;
//
//public class ColoredPSIVisual extends AbstractBlockEntityVisual<ColoredPortableFluidInterfaceBlockEntity> implements SimpleDynamicVisual, SimpleTickableVisual {
//
//    private final ColoredPIInstance instance;
//
//    public ColoredPSIVisual(VisualizationContext visualizationContext, ColoredPortableFluidInterfaceBlockEntity blockEntity, float partialTick, DyeColor color) {
//        super(visualizationContext, blockEntity, partialTick);
//
//        instance = new ColoredPIInstance(visualizationContext.instancerProvider(), blockState, getVisualPosition(), isLit(), color);
//        instance.beginFrame(blockEntity.getExtensionDistance(partialTick));
//    }
//
//    @Override
//    public void tick(TickableVisual.Context ctx) {
//        instance.tick(isLit());
//    }
//
//    @Override
//    public void beginFrame(DynamicVisual.Context ctx) {
//        instance.beginFrame(blockEntity.getExtensionDistance(ctx.partialTick()));
//    }
//
//    @Override
//    public void updateLight(float partialTick) {
//        relight(instance.middle, instance.top);
//    }
//
//    @Override
//    protected void _delete() {
//        instance.remove();
//    }
//
//    private boolean isLit() {
//        return blockEntity.isConnected();
//    }
//
//    @Override
//    public void collectCrumblingInstances(Consumer<Instance> consumer) {
//        instance.collectCrumblingInstances(consumer);
//    }
//}
