package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.theobl.createworldofcolor.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SteamEngineBlockEntity.class)
public abstract class SteamEngineBlockEntityMixin {
    @Inject(method = "isValid", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private void isValid(CallbackInfoReturnable<Boolean> cir, @Local Direction dir, @Local Level level) {
        if (level == null)
            cir.setReturnValue(false);

        cir.setReturnValue(level.getBlockState(((SteamEngineBlockEntity)(Object) this).getBlockPos().relative(dir)).is(ModTags.Blocks.FLUID_TANKS.tag));
    }
}
