package net.theobl.createworldofcolor.trains.track;

import com.simibubi.create.Create;
import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.content.trains.track.TrackBlockStateGenerator;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.simibubi.create.content.trains.track.TrackShape;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.theobl.createworldofcolor.CreateWorldOfColor;

import java.util.HashMap;
import java.util.Map;

public class ModTrackBlockStateGenerator extends TrackBlockStateGenerator {

    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        TrackShape value = state.getValue(TrackBlock.SHAPE);
        TrackMaterial material = ((TrackBlock) ctx.getEntry()).getMaterial();
        if (value == TrackShape.NONE) {
            return prov.models().getExistingFile(prov.mcLoc("block/air"));
        }
        String prefix = "block/track/" + material.resourceName() + "/";
        Map<String, String> textureMap = new HashMap<>(); //prefix + get() + material.resName()
        switch (value) {
            case TE, TN, TS, TW -> {
                //portal 1, 2, 3 portal, portal_mip, standard
                textureMap.put("1", "_portal_track");
                textureMap.put("2", "_portal_track_mip");
                textureMap.put("3", "_standard_track");
            }
            case AE, AW, AN, AS -> {
                //ascending 0, 1 standard, mip
                textureMap.put("0", "_standard_track");
                textureMap.put("1", "_standard_track_mip");
            }
            case CR_O, XO, ZO -> {
                //cross ortho 1, 2, 3, standard, mip, crossing
                //normal (x/z)_ortho 1, 2, standard mip
                textureMap.put("1", "_standard_track");
                textureMap.put("2", "_standard_track_mip");
                textureMap.put("3", "_standard_track_crossing");
            }
            default -> {
                //obj_track, 0, 1, 2, standard, mip, crossing
                textureMap.put("0", "_standard_track");
                textureMap.put("1", "_standard_track_mip");
                textureMap.put("2", "_standard_track_crossing");
            }
        }
        BlockModelBuilder builder = prov.models()
                .withExistingParent(prefix + value.getModel(),
                        Create.asResource("block/track/" + value.getModel()))
                .texture("particle", material.particle).renderType("cutout_mipped");
        for (String k : textureMap.keySet()) {
            builder = builder.texture(k, CreateWorldOfColor.asResource("block/" + material.resourceName() + textureMap.get(k)));
        }
        for (String k : new String[]{"segment_left", "segment_right", "tie"}) { // obj_track
            prov.models()
                    .withExistingParent(prefix + k,
                            Create.asResource("block/track/" + k))
                    .texture("0", "block/" + material.resourceName() + "_standard_track")
                    .texture("1", "block/" + material.resourceName() + "_standard_track_mip")
                    .texture("particle", material.particle);
        }
        return builder;
    }
}
