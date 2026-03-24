package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShaftBlock.class)
public abstract class ShaftBlockMixin {
    @Definition(id = "METAL_GIRDER", field = "Lcom/simibubi/create/AllBlocks;METAL_GIRDER:Lcom/tterrag/registrate/util/entry/BlockEntry;")
    @Definition(id = "isIn", method = "Lcom/tterrag/registrate/util/entry/BlockEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z")
    @Expression("METAL_GIRDER.isIn(?)")
    @WrapOperation(method = "useItemOn", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean coloredGirderIsIn(BlockEntry instance, ItemStack stack, Operation<Boolean> original) {
        for(var block : ModBlocks.DYED_METAL_GIRDERS) {
            if (block.isIn(stack)) {
                return true;
            }
        }
        return original.call(instance, stack);
    }

    @Definition(id = "METAL_GIRDER_ENCASED_SHAFT", field = "Lcom/simibubi/create/AllBlocks;METAL_GIRDER_ENCASED_SHAFT:Lcom/tterrag/registrate/util/entry/BlockEntry;")
    @Definition(id = "getDefaultState", method = "Lcom/tterrag/registrate/util/entry/BlockEntry;getDefaultState()Lnet/minecraft/world/level/block/state/BlockState;")
    @Expression("METAL_GIRDER_ENCASED_SHAFT.getDefaultState()")
    @ModifyExpressionValue(method = "useItemOn", at = @At("MIXINEXTRAS:EXPRESSION"))
    private BlockState getColoredDefaultState(BlockState original, @Local(argsOnly = true)ItemStack stack) {
        for (DyeColor color : DyeColor.values()) {
            if(ModBlocks.DYED_METAL_GIRDERS.get(color).isIn(stack)) {
                return ModBlocks.DYED_METAL_GIRDERS_ENCASED_SHAFT.get(color).getDefaultState();
            }
        }
        return original;
    }
}
