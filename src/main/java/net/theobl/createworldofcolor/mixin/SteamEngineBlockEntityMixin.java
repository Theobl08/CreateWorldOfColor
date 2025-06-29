package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.theobl.createworldofcolor.ModBlocks;
import net.theobl.createworldofcolor.ModHelper;
import net.theobl.createworldofcolor.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SteamEngineBlockEntity.class)
public abstract class SteamEngineBlockEntityMixin {

    @Inject(method = "isValid", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private void isValid(CallbackInfoReturnable<Boolean> cir, @Local Direction dir, @Local Level level) {
        if (level == null) {
            cir.setReturnValue(false);
            return;
        }

        cir.setReturnValue(level.getBlockState(((SteamEngineBlockEntity)(Object) this).getBlockPos().relative(dir)).is(ModTags.Blocks.FLUID_TANKS.tag));
    }

    @OnlyIn(Dist.CLIENT)
    @ModifyExpressionValue(method = "getTargetAngle", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean getTargetAngle(boolean original, @Local(name = "blockState") BlockState blockState) {
        return original || ModHelper.isColoredBlock(blockState, ModBlocks.STEAM_ENGINES);
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean tick(boolean original, @Local(ordinal = 1) BlockState blockState) {
        return original || ModHelper.isColoredBlock(blockState, ModBlocks.STEAM_ENGINES);
    }
}
