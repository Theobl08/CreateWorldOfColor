package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.decoration.girder.GirderBlock;
import com.simibubi.create.content.decoration.girder.GirderEncasedShaftBlock;
import com.simibubi.create.content.decoration.girder.GirderPlacementHelper;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Predicate;

@Mixin(GirderPlacementHelper.class)
public abstract class GirderPlacementHelperMixin {
    @ModifyReturnValue(method = "getItemPredicate", at = @At("RETURN"))
    private Predicate<ItemStack> getColoredItemPredicate(Predicate<ItemStack> original) {
        Predicate<ItemStack> predicate = stack -> {
            for (BlockEntry<GirderBlock> block : ModBlocks.DYED_METAL_GIRDERS) {
                if(block.isIn(stack)) {
                    return true;
                }
            }
            return false;
        };
        return original.or(predicate);
    }

    @ModifyReturnValue(method = "getStatePredicate", at =  @At("RETURN"))
    private Predicate<BlockState> getColoredStatePredicate(Predicate<BlockState> original) {
        Predicate<BlockState> predicate = state -> state.getBlock() instanceof GirderBlock || state.getBlock() instanceof GirderEncasedShaftBlock;
        return original.or(predicate);
    }
}
