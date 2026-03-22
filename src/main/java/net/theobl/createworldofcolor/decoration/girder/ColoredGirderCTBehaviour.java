package net.theobl.createworldofcolor.decoration.girder;

import com.simibubi.create.content.decoration.girder.GirderBlock;
import net.minecraft.world.item.DyeColor;
import net.theobl.createworldofcolor.ModSpriteShifts;
import org.jetbrains.annotations.Nullable;

import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public class ColoredGirderCTBehaviour extends ConnectedTextureBehaviour.Base {
	private final DyeColor color;

    public ColoredGirderCTBehaviour(DyeColor color) {
        this.color = color;
    }

    @Override
	public CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {
		if (!state.hasProperty(GirderBlock.X))
			return null;
		return !state.getValue(GirderBlock.X) && !state.getValue(GirderBlock.Z) && direction.getAxis() != Axis.Y
			? ModSpriteShifts.COLORED_GIRDER_POLE.get(color)
			: null;
	}

	@Override
	public boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos,
		BlockPos otherPos, Direction face) {
		if (other.getBlock() != state.getBlock())
			return false;
		return !other.getValue(GirderBlock.X) && !other.getValue(GirderBlock.Z);
	}

}