package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.decoration.girder.GirderBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GirderBlock.class)
public class GirderBlockMixin {
    @Definition(id = "getBlock", method = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;")
    @Definition(id = "sideState", local = @Local(type = BlockState.class, name = "sideState"))
    @Definition(id = "state", local = @Local(type = BlockState.class, ordinal = 0, argsOnly = true))
    @Expression("sideState.getBlock() == state.getBlock()")
    @ModifyExpressionValue(method = "updateState", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean test(boolean original, @Local(argsOnly = true)BlockState state, @Local(name = "sideState")BlockState sideState) {
        return original || (sideState.getBlock() instanceof GirderBlock && state.getBlock() instanceof GirderBlock);
    }
}
