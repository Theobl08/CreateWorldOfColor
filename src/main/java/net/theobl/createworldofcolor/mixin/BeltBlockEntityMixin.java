package net.theobl.createworldofcolor.mixin;

import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.theobl.createworldofcolor.ModBlocks;
import net.theobl.createworldofcolor.kinetics.belt.ModBeltCasingType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//Credits: Inspired from "Create: Encased" belt casing mixin code, by Iglee42 (MIT Licensed)
@Mixin(BeltBlockEntity.class)
public abstract class BeltBlockEntityMixin {

    @Shadow(remap = false) public BeltBlockEntity.CasingType casing;
    @Unique
    public BeltBlockEntity.CasingType createCasing$newCasing;

    @Inject(method = "setCasingType",at = @At("HEAD"),remap = false)
    private void encased$saveNewCasing(BeltBlockEntity.CasingType type, CallbackInfo ci) {
        createCasing$newCasing = type;
    }

    @ModifyArg(method = "setCasingType",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;levelEvent(ILnet/minecraft/core/BlockPos;I)V"),
            index = 2,
            remap = false)
    private int encased$changeParticle(int old) {
        for (DyeColor color : DyeColor.values()) {
            if (createCasing$newCasing.equals(ModBeltCasingType.DYED_ANDESITE.get(color))) {
                return Block.getId(ModBlocks.DYED_ANDESITE_CASING.get(color).getDefaultState());
            }
        }
        return old;
    }
}
