package net.theobl.createworldofcolor.fluids.pipes;

import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.pipes.SmartFluidPipeGenerator;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;

public class ModSmartFluidPipeGenerator extends SmartFluidPipeGenerator {
    private final String prefix;

    public ModSmartFluidPipeGenerator(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        String parentPath = "block/smart_fluid_pipe";
        String path = "block/" + ctx.getName();

        String pipeTexture = "block/" + prefix + "pipes";
        String smartTexture = "block/" + prefix + "smart_pipe_";
        String copperTexture = "block/copper/" + prefix + "copper_roof_top";

        prov.models().withExistingParent(path + "/block", Create.asResource(parentPath + "/block"))
                .texture("2", smartTexture + 1)
                .texture("3", smartTexture + 2)
                .texture("4", pipeTexture)
                .texture("particle", copperTexture);
        prov.models().withExistingParent(path + "/item", Create.asResource(parentPath + "/item"))
                .texture("1", pipeTexture)
                .texture("2", smartTexture + 1)
                .texture("3", smartTexture + 2);

        return super.getModel(ctx, prov, state);
    }
}
