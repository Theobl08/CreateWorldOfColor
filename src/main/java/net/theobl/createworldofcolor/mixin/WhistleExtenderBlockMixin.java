package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.decoration.steamWhistle.WhistleExtenderBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WhistleExtenderBlock.class)
public abstract class WhistleExtenderBlockMixin {
    @ModifyExpressionValue(method = "useItemOn", at = @At(value = "INVOKE",
            target = "Lcom/tterrag/registrate/util/entry/BlockEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z"))
    private boolean useItemOn(boolean original, @Local(argsOnly = true) ItemStack stack) {
        return original || ModBlocks.STEAM_WHISTLES.get(DyeColor.BLACK).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.BLUE).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.BROWN).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.CYAN).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.GRAY).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.GREEN).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.LIGHT_BLUE).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.LIGHT_GRAY).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.LIME).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.MAGENTA).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.ORANGE).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.PINK).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.PURPLE).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.RED).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.WHITE).isIn(stack) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.YELLOW).isIn(stack);
    }

    @ModifyExpressionValue(method = "canSurvive", at = @At(value = "INVOKE",
            target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean canSurvive(boolean original, @Local(ordinal = 1) BlockState below) {
            return original || ModBlocks.STEAM_WHISTLES.get(DyeColor.BLACK).has(below) ||
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
                    ModBlocks.STEAM_WHISTLES.get(DyeColor.YELLOW).has(below);
    }
}
