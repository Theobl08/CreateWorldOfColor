package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.decoration.girder.GirderBlock;
import com.simibubi.create.content.trains.track.TrackPaver;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TrackPaver.class)
public abstract class TrackPaverMixin {
    @Definition(id = "METAL_GIRDER", field = "Lcom/simibubi/create/AllBlocks;METAL_GIRDER:Lcom/tterrag/registrate/util/entry/BlockEntry;")
    @Definition(id = "has", method = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z")
    @Expression("METAL_GIRDER.has(?)")
    @ModifyExpressionValue(method = "paveCurve", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean paveCurveColored(boolean original, @Local(name = "defaultBlockState")BlockState defaultBlockState) {
        return original || defaultBlockState.getBlock() instanceof GirderBlock;
    }

    @ModifyReturnValue(method = "isWallLike", at= @At("RETURN"))
    private static boolean isGirderLike(boolean original, @Local(argsOnly = true)BlockState defaultBlockState) {
        return original || defaultBlockState.getBlock() instanceof GirderBlock;
    }
}
