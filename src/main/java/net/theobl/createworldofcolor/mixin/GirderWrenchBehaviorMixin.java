package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.decoration.girder.GirderBlock;
import com.simibubi.create.content.decoration.girder.GirderWrenchBehavior;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GirderWrenchBehavior.class)
public class GirderWrenchBehaviorMixin {
    @Definition(id = "has", method = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z")
    @Definition(id = "METAL_GIRDER", field = "Lcom/simibubi/create/AllBlocks;METAL_GIRDER:Lcom/tterrag/registrate/util/entry/BlockEntry;")
    @Expression("METAL_GIRDER.has(?)")
    @ModifyExpressionValue(method = "tick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean hasColoredMetalGirder(boolean original, @Local(name = "world") ClientLevel world, @Local(name = "pos") BlockPos pos) {
        return original || world.getBlockState(pos).getBlock() instanceof GirderBlock;
    }

    @Definition(id = "has", method = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z")
    @Definition(id = "METAL_GIRDER", field = "Lcom/simibubi/create/AllBlocks;METAL_GIRDER:Lcom/tterrag/registrate/util/entry/BlockEntry;")
    @Definition(id = "blockState", local = @Local(type = BlockState.class, name = "blockState"))
    @Expression("METAL_GIRDER.has(blockState)")
    @ModifyExpressionValue(method = "getValidDirections", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean getValidDirectionsColoredBlockState(boolean original, @Local(name = "blockState") BlockState blockState) {
        return original || blockState.getBlock() instanceof GirderBlock;
    }

    @Definition(id = "has", method = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z")
    @Definition(id = "METAL_GIRDER", field = "Lcom/simibubi/create/AllBlocks;METAL_GIRDER:Lcom/tterrag/registrate/util/entry/BlockEntry;")
    @Definition(id = "other", local = @Local(type = BlockState.class, name = "other"))
    @Expression("METAL_GIRDER.has(other)")
    @ModifyExpressionValue(method = "lambda$getValidDirections$4(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Ljava/util/function/Consumer;)V", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean getValidDirectionsColoredOther(boolean original, @Local(name = "other") BlockState other) {
        return original || other.getBlock() instanceof GirderBlock;
    }

    @Definition(id = "has", method = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z")
    @Definition(id = "METAL_GIRDER", field = "Lcom/simibubi/create/AllBlocks;METAL_GIRDER:Lcom/tterrag/registrate/util/entry/BlockEntry;")
    @Definition(id = "other", local = @Local(type = BlockState.class, name = "other"))
    @Expression("METAL_GIRDER.has(other)")
    @ModifyExpressionValue(method = "handleClick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean handleClickColored(boolean original, @Local(name = "other") BlockState other) {
        return original || other.getBlock() instanceof GirderBlock;
    }
}
