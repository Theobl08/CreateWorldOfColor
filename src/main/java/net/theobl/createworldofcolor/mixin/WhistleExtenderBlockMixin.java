package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.steamWhistle.WhistleExtenderBlock;
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
import net.theobl.createworldofcolor.decoration.steamWhistle.ColoredWhistleBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.simibubi.create.content.decoration.steamWhistle.WhistleExtenderBlock.SHAPE;
import static com.simibubi.create.content.decoration.steamWhistle.WhistleExtenderBlock.findRoot;

@Mixin(WhistleExtenderBlock.class)
public abstract class WhistleExtenderBlockMixin {
    @Inject(method = "useItemOn", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
    private void useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
                           BlockHitResult hitResult, CallbackInfoReturnable<ItemInteractionResult> cir) {
        if (player == null || !ModBlocks.STEAM_WHISTLES.get(DyeColor.BLACK).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.BLUE).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.BROWN).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.CYAN).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.GRAY).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.GREEN).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.LIGHT_BLUE).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.LIGHT_GRAY).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.LIME).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.MAGENTA).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.ORANGE).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.PINK).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.PURPLE).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.RED).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.WHITE).isIn(stack) &&
                !ModBlocks.STEAM_WHISTLES.get(DyeColor.YELLOW).isIn(stack)) {
            cir.setReturnValue(ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
            return; //Equivalent to: return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        BlockPos findRoot = findRoot(level, pos);
        BlockState blockState = level.getBlockState(findRoot);
        if (blockState.getBlock() instanceof ColoredWhistleBlock whistle) {
            cir.setReturnValue(whistle.useItemOn(stack, blockState, level, findRoot, player, hand,
                    new BlockHitResult(hitResult.getLocation(), hitResult.getDirection(), findRoot, hitResult.isInside())));
        }
    }

    @Inject(method = "canSurvive", at = @At("RETURN"), cancellable = true)
    private void canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {
        BlockState below = pLevel.getBlockState(pPos.below());
        boolean originalReturn = below.is((WhistleExtenderBlock) (Object) this) && below.getValue(SHAPE) != WhistleExtenderBlock.WhistleExtenderShape.SINGLE
                || AllBlocks.STEAM_WHISTLE.has(below);
        if(!originalReturn)
            cir.setReturnValue(originalReturn ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.BLACK).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.BLUE).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.BROWN).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.CYAN).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.GRAY).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.GREEN).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.LIGHT_BLUE).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.LIGHT_GRAY).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.LIME).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.MAGENTA).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.ORANGE).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.PINK).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.PURPLE).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.RED).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.WHITE).has(below) ||
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.YELLOW).has(below));
    }
}
