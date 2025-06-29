package net.theobl.createworldofcolor.fluids.spout;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import com.simibubi.create.content.fluids.spout.SpoutRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.platform.NeoForgeCatnipServices;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.fluids.FluidStack;
import net.theobl.createworldofcolor.ModPartialModels;
import net.theobl.createworldofcolor.mixin.SpoutBlockEntityAccessor;

import java.util.List;
import java.util.Map;

public class ColoredSpoutRenderer extends SpoutRenderer {
    protected final DyeColor color;
    static final List<Map<DyeColor, PartialModel>> BITS = List.of(ModPartialModels.COLORED_SPOUT_TOP, ModPartialModels.COLORED_SPOUT_MIDDLE, ModPartialModels.COLORED_SPOUT_BOTTOM);
    public ColoredSpoutRenderer(BlockEntityRendererProvider.Context context, DyeColor color) {
        super(context);
        this.color = color;
    }

    protected void renderSafe(SpoutBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        SmartFluidTankBehaviour tank = ((SpoutBlockEntityAccessor) be).getTank();
        if (tank == null)
            return;

        SmartFluidTankBehaviour.TankSegment primaryTank = tank.getPrimaryTank();
        FluidStack fluidStack = primaryTank.getRenderedFluid();
        float level = primaryTank.getFluidLevel()
                .getValue(partialTicks);

        if (!fluidStack.isEmpty() && level != 0) {
            boolean top = fluidStack.getFluid()
                    .getFluidType()
                    .isLighterThanAir();

            level = Math.max(level, 0.175f);
            float min = 2.5f / 16f;
            float max = min + (11 / 16f);
            float yOffset = (11 / 16f) * level;

            ms.pushPose();
            if (!top) ms.translate(0, yOffset, 0);
            else ms.translate(0, max - min, 0);

            NeoForgeCatnipServices.FLUID_RENDERER.renderFluidBox(fluidStack, min, min - yOffset, min, max, min,
                    max, buffer, ms, light, false, true);

            ms.popPose();
        }

        int processingTicks = be.processingTicks;
        float processingPT = processingTicks - partialTicks;
        float processingProgress = 1 - (processingPT - 5) / 10;
        processingProgress = Mth.clamp(processingProgress, 0, 1);
        float radius = 0;

        if (!fluidStack.isEmpty() && processingTicks != -1) {
            radius = (float) (Math.pow(((2 * processingProgress) - 1), 2) - 1);
            AABB bb = new AABB(0.5, 0.0, 0.5, 0.5, -1.2, 0.5).inflate(radius / 32f);
            NeoForgeCatnipServices.FLUID_RENDERER.renderFluidBox(fluidStack, (float) bb.minX, (float) bb.minY, (float) bb.minZ,
                    (float) bb.maxX, (float) bb.maxY, (float) bb.maxZ, buffer, ms, light, true, true);
        }

        float squeeze = radius;
        if (processingPT < 0)
            squeeze = 0;
        else if (processingPT < 2)
            squeeze = Mth.lerp(processingPT / 2f, 0, -1);
        else if (processingPT < 10)
            squeeze = -1;

        ms.pushPose();
        for (Map<DyeColor, PartialModel> bit : BITS) {
            CachedBuffers.partial(bit.get(color), be.getBlockState())
                    .light(light)
                    .renderInto(ms, buffer.getBuffer(RenderType.solid()));
            ms.translate(0, -3 * squeeze / 32f, 0);
        }
        ms.popPose();
    }
}
