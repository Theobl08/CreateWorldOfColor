package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.fluids.tank.BoilerData;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BoilerData.class)
public abstract class BoilerDataMixin {
    @ModifyExpressionValue(method = "evaluate", at = @At(value = "INVOKE",
            target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 0))
    private boolean evaluateEngines(boolean original, @Local(ordinal = 1) BlockState attachedState) {
        return original || (ModBlocks.STEAM_ENGINES.get(DyeColor.BLACK).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.BLUE).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.BROWN).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.CYAN).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.GRAY).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.GREEN).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.LIGHT_BLUE).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.LIGHT_GRAY).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.LIME).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.MAGENTA).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.ORANGE).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.PINK).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.PURPLE).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.RED).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.WHITE).has(attachedState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.YELLOW).has(attachedState));
    }

    @ModifyExpressionValue(method = "evaluate", at = @At(value = "INVOKE",
            target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 1))
    private boolean evaluateWhistle(boolean original, @Local(ordinal = 1) BlockState attachedState) {
        return original || (ModBlocks.STEAM_WHISTLES.get(DyeColor.BLACK).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.BLUE).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.BROWN).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.CYAN).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.GRAY).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.GREEN).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.LIGHT_BLUE).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.LIGHT_GRAY).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.LIME).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.MAGENTA).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.ORANGE).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.PINK).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.PURPLE).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.RED).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.WHITE).has(attachedState) ||
                ModBlocks.STEAM_WHISTLES.get(DyeColor.YELLOW).has(attachedState));
    }
}
