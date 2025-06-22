package net.theobl.createworldofcolor.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.pipes.EncasedPipeBlock;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.math.Pointing;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ModBlockStateGen {

    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> pump(DyeColor color) {
        return (c, p) -> {
            String colorName = color.getSerializedName();

            String parentPath = "block/mechanical_pump";
            String path = "block/" + c.getName();

            String pumpTexture = "block/" + colorName + "_pump";

            p.models().withExistingParent(path + "/block", Create.asResource(parentPath + "/block"))
                    .texture("4", pumpTexture)
                    .texture("particle", pumpTexture);

            p.models().withExistingParent(path + "/cog", Create.asResource(parentPath + "/cog"))
                    .texture("2", pumpTexture);

            p.models().withExistingParent(path + "/item", Create.asResource(parentPath + "/item"))
                    .texture("4", pumpTexture)
                    .texture("particle", pumpTexture);

            BlockStateGen.directionalBlockIgnoresWaterlogged(c, p, getBlockModel(true, c, p));
        };
    }

    private static <T extends Block> Function<BlockState, ModelFile> getBlockModel(boolean customItem,
                                                                                   DataGenContext<Block, T> c, RegistrateBlockstateProvider p) {
        return $ -> customItem ? AssetLookup.partialBaseModel(c, p) : AssetLookup.standardModel(c, p);
    }

    public static <P extends EncasedPipeBlock> NonNullBiConsumer<DataGenContext<Block, P>, RegistrateBlockstateProvider> encasedPipe(DyeColor color) {
        return (c, p) -> {
            String parentPath = "block/encased_fluid_pipe";
            String path = "block/" + c.getName();

            String colorName = color.getSerializedName();
            String pipeTexture = "block/encased_" + colorName + "_pipe"; // Path to pipe texture
            String casingTexture = "block/" + colorName + "_casing"; //colored casing

            p.models().withExistingParent(path + "/block_flat", Create.asResource(parentPath + "/block_flat"))
                    .texture("0", casingTexture)
                    .texture("particle", casingTexture);

            p.models().withExistingParent(path + "/block_open", Create.asResource(parentPath + "/block_open"))
                    .texture("0", pipeTexture)
                    .texture("particle", pipeTexture);

            ModelFile open = AssetLookup.partialBaseModel(c, p, "open");
            ModelFile flat = AssetLookup.partialBaseModel(c, p, "flat");
            MultiPartBlockStateBuilder builder = p.getMultipartBuilder(c.get());
            for (boolean flatPass : Iterate.trueAndFalse)
                for (Direction d : Iterate.directions) {
                    int verticalAngle = d == Direction.UP ? 90 : d == Direction.DOWN ? -90 : 0;
                    builder.part()
                            .modelFile(flatPass ? flat : open)
                            .rotationX(verticalAngle)
                            .rotationY((int) (d.toYRot() + (d.getAxis()
                                    .isVertical() ? 90 : 0)) % 360)
                            .addModel()
                            .condition(EncasedPipeBlock.FACING_TO_PROPERTY_MAP.get(d), !flatPass)
                            .end();
                }
        };
    }

    public static <P extends FluidPipeBlock> NonNullBiConsumer<DataGenContext<Block, P>, RegistrateBlockstateProvider> pipe(DyeColor color) {
        return (c, p) -> {
            String parentPath = "block/fluid_pipe";
            String path = "block/" + c.getName();
            String colorName = color.getSerializedName();

            String pipeTexture = "block/" + colorName + "_pipes"; // Path to pipe texture
            String copperTexture = "block/copper/" + colorName + "_copper_roof_top"; //colored copper texture (to avoid a hard dependency with WorldOfColor, my other mod)

            String LU = "lu";
            String RU = "ru";
            String LD = "ld";
            String RD = "rd";
            String LR = "lr";
            String UD = "ud";
            String U = "u";
            String D = "d";
            String L = "l";
            String R = "r";

            List<String> orientations = ImmutableList.of(LU, RU, LD, RD, LR, UD, U, D, L, R);
            Map<String, Pair<Integer, Integer>> uvs = ImmutableMap.<String, Pair<Integer, Integer>>builder()
                    .put(LU, Pair.of(12, 4))
                    .put(RU, Pair.of(8, 4))
                    .put(LD, Pair.of(12, 0))
                    .put(RD, Pair.of(8, 0))
                    .put(LR, Pair.of(4, 8))
                    .put(UD, Pair.of(0, 8))
                    .put(U, Pair.of(4, 4))
                    .put(D, Pair.of(0, 0))
                    .put(L, Pair.of(4, 0))
                    .put(R, Pair.of(0, 4))
                    .build();

            Map<Direction.Axis, ResourceLocation> coreTemplates = new IdentityHashMap<>();
            Map<Pair<String, Direction.Axis>, ModelFile> coreModels = new HashMap<>();

//            for (Direction.Axis axis : Iterate.axes) {
//                for (String orientation : orientations) {
//                    Pair<String, Direction.Axis> key = Pair.of(orientation, axis);
//                    String sourceModel = parentPath + "/" + orientation + "_" + axis.getName(); //Single model for all pipes
//                    String coloredModel = path + "/" + orientation + "_" + axis.getName(); //each pipe has its own model
//
//                    coreModels.put(key, p.models()
//                            .withExistingParent(coloredModel, Create.asResource(sourceModel))
//                            .texture("0", p.modLoc("block/" + colorName + "_pipes_connected"))
//                            .texture("particle", p.modLoc(copperTexture)));
//                }
//            }

            //Cores
            for (Direction.Axis axis : Iterate.axes) {
                String sourceModel = parentPath + "/core_" + axis.getSerializedName();
                String outputModel = path + "/core_" + axis.getSerializedName();
                p.models().withExistingParent(outputModel, Create.asResource(sourceModel))
                        .texture("0", "block/" + colorName + "_pipes_connected")
                        .texture("particle", copperTexture);
            }

            for (Direction.Axis axis : Iterate.axes)
                coreTemplates.put(axis, p.modLoc(path + "/core_" + axis.getSerializedName()));

            for (Direction.Axis axis : Iterate.axes) {
                ResourceLocation parent = coreTemplates.get(axis);
                for (String s : orientations) {
                    Pair<String, Direction.Axis> key = Pair.of(s, axis);
                    String modelName = path + "/" + s + "_" + axis.getSerializedName();
                    coreModels.put(key, p.models()
                            .withExistingParent(modelName, parent)
                            .element()
                            .from(4, 4, 4)
                            .to(12, 12, 12)
                            .face(Direction.get(Direction.AxisDirection.POSITIVE, axis))
                            .end()
                            .face(Direction.get(Direction.AxisDirection.NEGATIVE, axis))
                            .end()
                            .faces((d, builder) -> {
                                Pair<Integer, Integer> pair = uvs.get(s);
                                float u = pair.getKey();
                                float v = pair.getValue();
                                if (d == Direction.UP)
                                    builder.uvs(u + 4, v + 4, u, v);
                                if (d == Direction.DOWN)
                                    builder.uvs(u + 4, v, u, v + 4);
                                if (d == Direction.NORTH)
                                    builder.uvs(u, v, u + 4, v + 4);
                                if (d == Direction.SOUTH)
                                    builder.uvs(u + 4, v, u, v + 4);
                                if (d == Direction.EAST)
                                    builder.uvs(u, v, u + 4, v + 4);
                                if (d == Direction.WEST)
                                    builder.uvs(u + 4, v, u, v + 4);
                                builder.texture("#0");
                            })
                            .end());
                }
            }

            MultiPartBlockStateBuilder builder = p.getMultipartBuilder(c.get());
            for (Direction.Axis axis : Iterate.axes) {
                putPart(coreModels, builder, axis, LU, true, false, true, false);
                putPart(coreModels, builder, axis, RU, true, false, false, true);
                putPart(coreModels, builder, axis, LD, false, true, true, false);
                putPart(coreModels, builder, axis, RD, false, true, false, true);
                putPart(coreModels, builder, axis, UD, true, true, false, false);
                putPart(coreModels, builder, axis, U, true, false, false, false);
                putPart(coreModels, builder, axis, D, false, true, false, false);
                putPart(coreModels, builder, axis, LR, false, false, true, true);
                putPart(coreModels, builder, axis, L, false, false, true, false);
                putPart(coreModels, builder, axis, R, false, false, false, true);
            }

            //Partials
            //Casing
            String casingSource = parentPath + "/casing";
            String casingOutput = path + "/casing";
            p.models().withExistingParent(casingOutput, Create.asResource(casingSource))
                    .texture("0", pipeTexture)
                    .texture("particle", copperTexture);

            //Window
            String windowSource = parentPath + "/window";
            String windowOutput = path + "/window";
            p.models().withExistingParent(windowOutput, Create.asResource(windowSource))
                    .texture("0", "block/glass_" + colorName + "_fluid_pipe")
                    .texture("particle", copperTexture);

            //Item
            p.models().withExistingParent("block/" + c.getName() + "/item", Create.asResource(parentPath + "/item"))
                    .texture("1", pipeTexture)
                    .texture("particle", copperTexture);

            //Other ones
            List<String> partials = List.of("connection", "drain", "rim", "rim_connector");
            for(String partial : partials) {
                for (Direction d : Iterate.directions) {
                    String sourceModel = parentPath + "/" + partial + "/" + d.getName();
                    String outputModel = path + "/" + partial + "/" + d.getName();

                    p.models().withExistingParent(outputModel, Create.asResource(sourceModel))
                            .texture("0", pipeTexture)
                            .texture("particle", pipeTexture);
                }
            }

        };
    }

    private static void putPart(Map<Pair<String, Direction.Axis>, ModelFile> coreModels, MultiPartBlockStateBuilder builder,
                                Direction.Axis axis, String s, boolean up, boolean down, boolean left, boolean right) {
        Direction positiveAxis = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        Map<Direction, BooleanProperty> propertyMap = FluidPipeBlock.PROPERTY_BY_DIRECTION;

        Direction upD = Pointing.UP.getCombinedDirection(positiveAxis);
        Direction leftD = Pointing.LEFT.getCombinedDirection(positiveAxis);
        Direction rightD = Pointing.RIGHT.getCombinedDirection(positiveAxis);
        Direction downD = Pointing.DOWN.getCombinedDirection(positiveAxis);

        if (axis == Direction.Axis.Y || axis == Direction.Axis.X) {
            leftD = leftD.getOpposite();
            rightD = rightD.getOpposite();
        }

        builder.part()
                .modelFile(coreModels.get(Pair.of(s, axis)))
                .addModel()
                .condition(propertyMap.get(upD), up)
                .condition(propertyMap.get(leftD), left)
                .condition(propertyMap.get(rightD), right)
                .condition(propertyMap.get(downD), down)
                .end();
    }
}
