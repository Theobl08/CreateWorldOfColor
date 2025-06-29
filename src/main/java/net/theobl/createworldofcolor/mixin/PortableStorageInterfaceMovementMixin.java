package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceBlock;
import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceBlockEntity;
import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceMovement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PortableStorageInterfaceMovement.class)
public class PortableStorageInterfaceMovementMixin {
    @Inject(method = "getStationaryInterfaceAt", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private void getStationaryInterfaceAt(Level world, BlockPos pos, BlockState state, Direction facing, CallbackInfoReturnable<PortableStorageInterfaceBlockEntity> cir,
                                          @Local(ordinal = 1) BlockState blockState, @Local PortableStorageInterfaceBlockEntity psi) {
        if(AllBlocks.PORTABLE_FLUID_INTERFACE.has(state) && !(ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.BLACK).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.BLUE).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.BROWN).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.CYAN).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.GRAY).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.GREEN).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.LIGHT_BLUE).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.LIGHT_GRAY).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.LIME).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.MAGENTA).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.ORANGE).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.PINK).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.PURPLE).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.RED).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.WHITE).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.YELLOW).has(blockState))) {
            return;
        }
        if (blockState.getValue(PortableStorageInterfaceBlock.FACING) != facing.getOpposite())
            return;
        if (psi.isPowered())
            return;
        if ((ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.BLACK).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.BLUE).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.BROWN).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.CYAN).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.GRAY).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.GREEN).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.LIGHT_BLUE).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.LIGHT_GRAY).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.LIME).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.MAGENTA).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.ORANGE).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.PINK).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.PURPLE).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.RED).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.WHITE).has(blockState) ||
                ModBlocks.PORTABLE_FLUID_INTERFACES.get(DyeColor.YELLOW).has(blockState))) {
            cir.setReturnValue(psi);
        }
    }
}
