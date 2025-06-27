package net.theobl.createworldofcolor.decoration.steamWhistle;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.theobl.createworldofcolor.ModBlocks;

public class ColoredWhistleBlock extends WhistleBlock {
    private final DyeColor color;
    public ColoredWhistleBlock(Properties properties, DyeColor color) {
        super(properties);
        this.color = color;
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockPos = pos.relative(getAttachedDirection(state));
        return level.getBlockState(blockPos).getBlock().equals(AllBlocks.FLUID_TANK.get()) || // Regular fluid tank
                level.getBlockState(blockPos).getBlock().equals(AllBlocks.CREATIVE_FLUID_TANK.get()) || // Creative fluid tank
                level.getBlockState(blockPos).getBlock().equals(ModBlocks.FLUID_TANKS.get(color).get());
    }

    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player == null)
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        if (AllBlocks.STEAM_WHISTLE.isIn(stack) || ModBlocks.STEAM_WHISTLES.get(color).isIn(stack)) {
            incrementSize(level, pos);
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
