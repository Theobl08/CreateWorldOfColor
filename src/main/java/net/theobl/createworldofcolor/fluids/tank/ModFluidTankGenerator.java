package net.theobl.createworldofcolor.fluids.tank;

import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.fluids.tank.FluidTankGenerator;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;

public class ModFluidTankGenerator extends FluidTankGenerator {
    private final String prefix;

    public ModFluidTankGenerator(String prefix) {
        this.prefix = prefix;
    }

    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
                                                BlockState state) {
        Boolean top = state.getValue(FluidTankBlock.TOP);
        Boolean bottom = state.getValue(FluidTankBlock.BOTTOM);
        FluidTankBlock.Shape shape = state.getValue(FluidTankBlock.SHAPE);

        String shapeName = "middle";
        if (top && bottom)
            shapeName = "single";
        else if (top)
            shapeName = "top";
        else if (bottom)
            shapeName = "bottom";

        String modelName = shapeName + (shape == FluidTankBlock.Shape.PLAIN ? "" : "_" + shape.getSerializedName());
        var parent = Create.asResource("block/fluid_tank/block_" + modelName);

        return prov.models()
                .withExistingParent("block/" + prefix + "fluid_tank/block_" + modelName, parent)
                .texture("0", prov.modLoc("block/" + prefix + "fluid_tank_top"))
                .texture("1", prov.modLoc("block/" + prefix + "fluid_tank"))
                .texture("3", prov.modLoc("block/" + prefix + "fluid_tank_window"))
                .texture("4", prov.modLoc("block/" + prefix + "fluid_tank_inner"))
                .texture("5", prov.modLoc("block/" + prefix + "fluid_tank_window_single"))
                .texture("particle", prov.modLoc("block/" + prefix + "fluid_tank"));
    }

    public static <I extends BlockItem> NonNullBiConsumer<DataGenContext<Item, I>, RegistrateItemModelProvider> customBlockItemModel(
            String... folders) {
        return (c, p) -> {
            String path = "block";
            for (String string : folders)
                path += "/" + ("_".equals(string) ? c.getName() : string);
            p.withExistingParent(c.getName(), Create.asResource(path));
        };
    }
}
