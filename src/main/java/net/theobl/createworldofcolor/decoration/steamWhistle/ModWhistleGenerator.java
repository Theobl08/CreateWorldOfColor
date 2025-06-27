package net.theobl.createworldofcolor.decoration.steamWhistle;

import com.simibubi.create.Create;
import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;
import com.simibubi.create.content.decoration.steamWhistle.WhistleGenerator;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.theobl.createworldofcolor.CreateWorldOfColor;

public class ModWhistleGenerator extends WhistleGenerator {
    private final String prefix;

    public ModWhistleGenerator(String prefix) {
        this.prefix = prefix;
    }
    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        String parentPath = "block/steam_whistle";
        String path = "block/" + ctx.getName();

        String engine = "block/" + prefix + "engine";
        String copperRedStonePlate = "block/" + prefix + "copper_redstone_plate";

        prov.models().withExistingParent(path + "/block_large_floor", Create.asResource(parentPath + "/block_large_floor"))
                .texture("1", engine)
                .texture("2", copperRedStonePlate);

        prov.models().withExistingParent(path + "/block_large_wall", Create.asResource(parentPath + "/block_large_wall"))
                .texture("1", engine)
                .texture("2", copperRedStonePlate);

        prov.models().withExistingParent(path + "/block_medium_floor", Create.asResource(parentPath + "/block_medium_floor"))
                .texture("1", engine)
                .texture("2", copperRedStonePlate);

        prov.models().withExistingParent(path + "/block_medium_wall", Create.asResource(parentPath + "/block_medium_wall"))
                .texture("1", engine)
                .texture("2", copperRedStonePlate);

        prov.models().withExistingParent(path + "/block_small_floor", Create.asResource(parentPath + "/block_small_floor"))
                .texture("1", engine)
                .texture("2", copperRedStonePlate);

        prov.models().withExistingParent(path + "/block_small_wall", Create.asResource(parentPath + "/block_small_wall"))
                .texture("1", engine)
                .texture("2", copperRedStonePlate);

        prov.models().withExistingParent(path + "/item", Create.asResource(parentPath + "/item"))
                .texture("1", engine)
                .texture("2", copperRedStonePlate);

        String wall = state.getValue(WhistleBlock.WALL) ? "wall" : "floor";
        String size = state.getValue(WhistleBlock.SIZE)
                .getSerializedName();
        boolean powered = state.getValue(WhistleBlock.POWERED);
        ModelFile model = AssetLookup.partialBaseModel(ctx, prov, size, wall);
        if (!powered)
            return model;
        ResourceLocation parentLocation = model.getLocation();
        return prov.models()
                .withExistingParent(parentLocation.getPath() + "_powered", parentLocation)
                .texture("2", CreateWorldOfColor.asResource("block/" + prefix + "copper_redstone_plate_powered"));
    }
}
