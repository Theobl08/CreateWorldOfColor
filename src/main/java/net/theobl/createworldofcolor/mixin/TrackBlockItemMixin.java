package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.decoration.girder.GirderBlock;
import com.simibubi.create.content.trains.track.TrackBlockItem;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.item.ItemStack;
import net.theobl.createworldofcolor.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TrackBlockItem.class)
public abstract class TrackBlockItemMixin {
    @Definition(id = "METAL_GIRDER", field = "Lcom/simibubi/create/AllBlocks;METAL_GIRDER:Lcom/tterrag/registrate/util/entry/BlockEntry;")
    @Definition(id = "isIn", method = "Lcom/tterrag/registrate/util/entry/BlockEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z")
    @Expression("METAL_GIRDER.isIn(?)")
    @ModifyExpressionValue(method = "useOn", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean hasColoredGirder(boolean original, @Local(name = "offhandItem")ItemStack offhandItem) {
        for (BlockEntry<GirderBlock> block : ModBlocks.DYED_METAL_GIRDERS) {
            if(block.isIn(offhandItem)) {
                return true;
            }
        }
        return original;
    }
}
