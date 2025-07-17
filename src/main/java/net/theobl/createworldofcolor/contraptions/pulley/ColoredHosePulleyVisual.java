package net.theobl.createworldofcolor.contraptions.pulley;

import com.simibubi.create.content.contraptions.pulley.HosePulleyVisual;
import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlockEntity;
import dev.engine_room.flywheel.api.instance.Instancer;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import net.minecraft.world.item.DyeColor;
import net.theobl.createworldofcolor.ModPartialModels;
import net.theobl.createworldofcolor.fluids.hosePulley.ColoredHosePulleyBlock;
import net.theobl.createworldofcolor.fluids.hosePulley.ColoredHosePulleyBlockEntity;

public class ColoredHosePulleyVisual extends HosePulleyVisual {
    public ColoredHosePulleyVisual(VisualizationContext dispatcher, HosePulleyBlockEntity blockEntity, float partialTick) {
        super(dispatcher, blockEntity, partialTick);
    }

    @Override
    protected Instancer<TransformedInstance> getMagnetModel() {
        if(this.blockEntity.getBlockState().getBlock() instanceof ColoredHosePulleyBlock block)
            return instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(ModPartialModels.COLORED_HOSE_MAGNET.get(block.color)));
        else
            return super.getMagnetModel();
    }

    @Override
    protected Instancer<TransformedInstance> getHalfMagnetModel() {
        if(this.blockEntity.getBlockState().getBlock() instanceof ColoredHosePulleyBlock block)
            return instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(ModPartialModels.COLORED_HOSE_HALF_MAGNET.get(block.color)));
        else
            return super.getHalfMagnetModel();
    }
}
