package net.theobl.createworldofcolor.fluids.hosePulley;

import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlock;
import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.theobl.createworldofcolor.ModBlockEntityTypes;
import net.theobl.createworldofcolor.mixin.HosePulleyBlockEntityAccessor;

public class ColoredHosePulleyBlockEntity extends HosePulleyBlockEntity {
    public ColoredHosePulleyBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        for (DyeColor color : DyeColor.values()) {
            event.registerBlockEntity(
                    Capabilities.FluidHandler.BLOCK,
                    ModBlockEntityTypes.COLORED_HOSE_PULLEYS.get(color).get(),
                    (be, context) -> {
                        if (context == null || HosePulleyBlock.hasPipeTowards(be.level, be.worldPosition, be.getBlockState(), context))
                            return ((HosePulleyBlockEntityAccessor) be).getHandler();
                        return null;
                    }
            );
        }
    }
}
