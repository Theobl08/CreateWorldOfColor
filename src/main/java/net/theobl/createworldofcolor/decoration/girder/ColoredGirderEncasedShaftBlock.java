package net.theobl.createworldofcolor.decoration.girder;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.girder.GirderBlock;
import com.simibubi.create.content.decoration.girder.GirderEncasedShaftBlock;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;

import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;

public class ColoredGirderEncasedShaftBlock extends GirderEncasedShaftBlock {
	private final DyeColor color;

	public ColoredGirderEncasedShaftBlock(Properties properties, DyeColor color) {
		super(properties);
        this.color = color;
    }

	@Override
	public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
		return ModBlocks.DYED_METAL_GIRDERS.get(color).getDefaultState()
			.setValue(WATERLOGGED, originalState.getValue(WATERLOGGED))
			.setValue(GirderBlock.X, originalState.getValue(HORIZONTAL_AXIS) == Axis.Z)
			.setValue(GirderBlock.Z, originalState.getValue(HORIZONTAL_AXIS) == Axis.X)
			.setValue(GirderBlock.AXIS, originalState.getValue(HORIZONTAL_AXIS) == Axis.X ? Axis.Z : Axis.X)
			.setValue(GirderBlock.BOTTOM, originalState.getValue(BOTTOM))
			.setValue(GirderBlock.TOP, originalState.getValue(TOP));
	}

	@Override
	public ItemRequirement getRequiredItems(BlockState state, BlockEntity be) {
		return ItemRequirement.of(AllBlocks.SHAFT.getDefaultState(), be)
			.union(ItemRequirement.of(ModBlocks.DYED_METAL_GIRDERS.get(color).getDefaultState(), be));
	}

}
