package net.theobl.createworldofcolor.mixin;

import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;
import net.theobl.createworldofcolor.ModPartialModels;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PortableStorageInterfaceRenderer.class)
public class PortableStorageInterfaceRendererMixin {
    @Inject(method = "getMiddleForState", at = @At("HEAD"), cancellable = true)
    private static void getMiddleForState(BlockState state, boolean lit, CallbackInfoReturnable<PartialModel> cir) {
        for (DyeColor color : DyeColor.values()) {
            if (ModBlocks.PORTABLE_FLUID_INTERFACES.get(color).has(state)) {
                cir.setReturnValue(lit ? ModPartialModels.COLORED_PORTABLE_FLUID_INTERFACE_MIDDLE_POWERED.get(color)
                        : ModPartialModels.COLORED_PORTABLE_FLUID_INTERFACE_MIDDLE.get(color));
                return;
            }
        }
    }

    @Inject(method = "getTopForState", at = @At("HEAD"), cancellable = true)
    private static void getTopForState(BlockState state, CallbackInfoReturnable<PartialModel> cir) {
        for (DyeColor color : DyeColor.values()) {
            if (ModBlocks.PORTABLE_FLUID_INTERFACES.get(color).has(state)) {
                cir.setReturnValue(ModPartialModels.COLORED_PORTABLE_FLUID_INTERFACE_TOP.get(color));
                return;
            }
        }
    }
}
