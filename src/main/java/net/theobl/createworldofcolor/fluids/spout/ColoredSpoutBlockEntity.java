package net.theobl.createworldofcolor.fluids.spout;

import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.theobl.createworldofcolor.ModBlockEntityTypes;
import net.theobl.createworldofcolor.mixin.SpoutBlockEntityAccessor;

public class ColoredSpoutBlockEntity extends SpoutBlockEntity {
    public ColoredSpoutBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        for(DyeColor color : DyeColor.values()) {
            event.registerBlockEntity(
                    Capabilities.FluidHandler.BLOCK,
                    ModBlockEntityTypes.COLORED_SPOUTS.get(color).get(),
                    (be, context) -> {
                        if (context != Direction.DOWN)
                            return ((SpoutBlockEntityAccessor) be).getTank().getCapability();
                        return null;
                    }
            );
        }
    }
}
