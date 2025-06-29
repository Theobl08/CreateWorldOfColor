package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.fluids.tank.BoilerData;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;
import net.theobl.createworldofcolor.ModHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BoilerData.class)
public abstract class BoilerDataMixin {
    @ModifyExpressionValue(method = "evaluate", at = @At(value = "INVOKE",
            target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 0))
    private boolean evaluateEngines(boolean original, @Local(ordinal = 1) BlockState attachedState) {
        return original || ModHelper.isColoredBlock(attachedState, ModBlocks.STEAM_ENGINES);
    }

    @ModifyExpressionValue(method = "evaluate", at = @At(value = "INVOKE",
            target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 1))
    private boolean evaluateWhistle(boolean original, @Local(ordinal = 1) BlockState attachedState) {
        return original || ModHelper.isColoredBlock(attachedState, ModBlocks.STEAM_WHISTLES);
    }
}
