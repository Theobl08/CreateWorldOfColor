//package net.theobl.createworldofcolor.contraptions.actors.psi;
//
//import com.simibubi.create.content.contraptions.behaviour.MovementContext;
//import com.simibubi.create.content.contraptions.render.ActorVisual;
//import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
//import dev.engine_room.flywheel.api.visualization.VisualizationContext;
//import net.createmod.catnip.animation.AnimationTickHolder;
//import net.createmod.catnip.animation.LerpedFloat;
//import net.minecraft.world.item.DyeColor;
//
//public class ColoredPSIActorVisual extends ActorVisual {
//
//    private final ColoredPIInstance instance;
//
//    public ColoredPSIActorVisual(VisualizationContext context, VirtualRenderWorld world, MovementContext movementContext, DyeColor color) {
//        super(context, world, movementContext);
//
//        instance = new ColoredPIInstance(context.instancerProvider(), movementContext.state, movementContext.localPos, false, color);
//
//        instance.middle.light(localBlockLight(), 0);
//        instance.top.light(localBlockLight(), 0);
//    }
//
//    @Override
//    public void beginFrame() {
//        LerpedFloat lf = ColoredPortableStorageInterfaceMovement.getAnimation(context);
//        instance.tick(lf.settled());
//        instance.beginFrame(lf.getValue(AnimationTickHolder.getPartialTicks()));
//    }
//
//    @Override
//    protected void _delete() {
//        instance.remove();
//    }
//}
