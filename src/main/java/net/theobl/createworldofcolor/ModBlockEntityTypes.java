package net.theobl.createworldofcolor;

import com.simibubi.create.content.fluids.tank.FluidTankRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.world.item.DyeColor;
import net.theobl.createworldofcolor.fluids.pipes.ColoredFluidPipeBlockEntity;
import net.theobl.createworldofcolor.fluids.spout.ColoredSpoutBlockEntity;
import net.theobl.createworldofcolor.fluids.spout.ColoredSpoutRenderer;
import net.theobl.createworldofcolor.fluids.tank.ColoredFluidTankBlockEntity;

import java.util.HashMap;

public class ModBlockEntityTypes {
//
//    public static final BlockEntityEntry<ColoredFluidTankBlockEntity> COLORED_TANKS = CreateWorldOfColor.REGISTRATE
//            .blockEntity("colored_tank", ColoredFluidTankBlockEntity::new)
//            .validBlocks(ModBlocks.FLUID_TANKS.toArray())
//            .renderer(() -> FluidTankRenderer::new)
//            .register();

    public static final HashMap<DyeColor, BlockEntityEntry<ColoredFluidTankBlockEntity>> COLORED_TANKS = new HashMap<>();
    public static final HashMap<DyeColor, BlockEntityEntry<ColoredFluidPipeBlockEntity>> COLORED_PIPES = new HashMap<>();
    public static final HashMap<DyeColor, BlockEntityEntry<ColoredSpoutBlockEntity>> COLORED_SPOUTS = new HashMap<>();

    private static void registerColoredTanks() {
        for (DyeColor color : DyeColor.values()) {
            String id = color.getSerializedName();
            COLORED_TANKS.put(color, CreateWorldOfColor.REGISTRATE.blockEntity(id + "_fluid_tank", ColoredFluidTankBlockEntity::new)
                    .validBlocks(ModBlocks.FLUID_TANKS.get(color))
                    .renderer(() -> FluidTankRenderer::new)
                    .register());
            COLORED_PIPES.put(color, CreateWorldOfColor.REGISTRATE.blockEntity(id + "_fluid_pipe", ColoredFluidPipeBlockEntity::new)
                    .validBlocks(ModBlocks.FLUID_PIPES.get(color))
                    .register());
            COLORED_SPOUTS.put(color, CreateWorldOfColor.REGISTRATE.blockEntity(id + "_spout", ColoredSpoutBlockEntity::new)
                    .validBlocks(ModBlocks.SPOUTS.get(color))
                    .renderer(() -> context -> new ColoredSpoutRenderer(context, color))
                    .register());
        }
    }

    public static void register() {
        registerColoredTanks();
    }
}
