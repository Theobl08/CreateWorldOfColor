package net.theobl.createworldofcolor.decoration.girder;

import com.simibubi.create.Create;
import com.simibubi.create.content.decoration.girder.GirderBlock;
import com.simibubi.create.content.decoration.girder.GirderEncasedShaftBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public class ColoredGirderBlockStateGenerator {

	public static void blockStateWithShaft(DataGenContext<Block, ? extends GirderEncasedShaftBlock> c, RegistrateBlockstateProvider p, DyeColor color) {
		MultiPartBlockStateBuilder builder = p.getMultipartBuilder(c.get());
		String colorName = color.getSerializedName();

		String path = "block/" + c.getName();

		String baseTexture = "block/" + colorName + "_girder";
		String particle = "block/copper/" + colorName + "_copper_roof_top";

        BlockModelBuilder baseModel = p.models().withExistingParent(path + "/block", Create.asResource("block/metal_girder_encased_shaft/block"))
				.texture("0", baseTexture)
				.texture("particle", particle);

		builder.part()
//			.modelFile(AssetLookup.partialBaseModel(c, p))
			.modelFile(baseModel)
			.rotationY(0)
			.addModel()
			.condition(GirderEncasedShaftBlock.HORIZONTAL_AXIS, Axis.Z)
			.end();

		builder.part()
//			.modelFile(AssetLookup.partialBaseModel(c, p))
			.modelFile(baseModel)
			.rotationY(90)
			.addModel()
			.condition(GirderEncasedShaftBlock.HORIZONTAL_AXIS, Axis.X)
			.end();

		builder.part()
//			.modelFile(AssetLookup.partialBaseModel(c, p, "top"))
			.modelFile(p.models().withExistingParent(path + "/block_top", Create.asResource("block/metal_girder_encased_shaft/block_top"))
					.texture("0", baseTexture)
					.texture("particle", particle))
			.addModel()
			.condition(GirderEncasedShaftBlock.TOP, true)
			.end();

		builder.part()
//			.modelFile(AssetLookup.partialBaseModel(c, p, "bottom"))
			.modelFile(p.models().withExistingParent(path + "/block_bottom", Create.asResource("block/metal_girder_encased_shaft/block_bottom"))
					.texture("0", baseTexture)
					.texture("particle", particle))
			.addModel()
			.condition(GirderEncasedShaftBlock.BOTTOM, true)
			.end();

	}

	public static void blockState(DataGenContext<Block, ? extends GirderBlock> c, RegistrateBlockstateProvider p, DyeColor color) {
		MultiPartBlockStateBuilder builder = p.getMultipartBuilder(c.get());
		String colorName = color.getSerializedName();

		String path = "block/" + c.getName();

		String base = "block/" + colorName + "_girder";
		String pole = "block/" + colorName + "_girder_pole";
		String poleSide = "block/" + colorName + "_girder_pole_side";
		String particle = "block/copper/" + colorName + "_copper_roof_top";

			builder.part()
//			.modelFile(AssetLookup.partialBaseModel(c, p, "pole"))
			.modelFile(p.models().withExistingParent(path + "/block_pole", Create.asResource("block/metal_girder/block_pole"))
					.texture("1", pole)
					.texture("2", poleSide)
					.texture("particle", particle))
			.addModel()
			.condition(GirderBlock.X, false)
			.condition(GirderBlock.Z, false)
			.end();

		builder.part()
//			.modelFile(AssetLookup.partialBaseModel(c, p, "x"))
			.modelFile(p.models().withExistingParent(path + "/block_x", Create.asResource("block/metal_girder/block_x"))
					.texture("0", base)
					.texture("particle", particle))
			.addModel()
			.condition(GirderBlock.X, true)
			.end();

		builder.part()
//			.modelFile(AssetLookup.partialBaseModel(c, p, "z"))
			.modelFile(p.models().withExistingParent(path + "/block_z", Create.asResource("block/metal_girder/block_z"))
					.texture("0", base)
					.texture("particle", particle))
			.addModel()
			.condition(GirderBlock.Z, true)
			.end();

		for (boolean x : Iterate.trueAndFalse)
			builder.part()
//				.modelFile(AssetLookup.partialBaseModel(c, p, "top"))
				.modelFile(p.models().withExistingParent(path + "/block_top", Create.asResource("block/metal_girder/block_top"))
						.texture("0", base)
						.texture("particle", particle))
				.addModel()
				.condition(GirderBlock.TOP, true)
				.condition(GirderBlock.X, x)
				.condition(GirderBlock.Z, !x)
				.end()
				.part()
//				.modelFile(AssetLookup.partialBaseModel(c, p, "bottom"))
				.modelFile(p.models().withExistingParent(path + "/block_bottom", Create.asResource("block/metal_girder/block_bottom"))
						.texture("0", base)
						.texture("particle", particle))
				.addModel()
				.condition(GirderBlock.BOTTOM, true)
				.condition(GirderBlock.X, x)
				.condition(GirderBlock.Z, !x)
				.end();

		builder.part()
//			.modelFile(AssetLookup.partialBaseModel(c, p, "cross"))
			.modelFile(p.models().withExistingParent(path + "/block_cross", Create.asResource("block/metal_girder/block_cross"))
					.texture("0", base)
					.texture("particle", particle))
			.addModel()
			.condition(GirderBlock.X, true)
			.condition(GirderBlock.Z, true)
			.end();

		p.models().withExistingParent(path + "/item", Create.asResource("block/metal_girder/item"))
				.texture("0", base);

		p.models().withExistingParent(path + "/bracket_north", Create.asResource("block/metal_girder/bracket_north"))
				.texture("0", base);
		p.models().withExistingParent(path + "/bracket_south", Create.asResource("block/metal_girder/bracket_south"))
				.texture("0", base);
		p.models().withExistingParent(path + "/bracket_east", Create.asResource("block/metal_girder/bracket_east"))
				.texture("0", base);
		p.models().withExistingParent(path + "/bracket_west", Create.asResource("block/metal_girder/bracket_west"))
				.texture("0", base);
		p.models().withExistingParent(path + "/segment_bottom", Create.asResource("block/metal_girder/segment_bottom"))
				.texture("0", base);
		p.models().withExistingParent(path + "/segment_middle", Create.asResource("block/metal_girder/segment_middle"))
				.texture("0", base);
		p.models().withExistingParent(path + "/segment_top", Create.asResource("block/metal_girder/segment_top"))
				.texture("0", base);

	}

}
