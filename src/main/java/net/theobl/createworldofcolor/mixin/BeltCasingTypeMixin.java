package net.theobl.createworldofcolor.mixin;

import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import net.minecraft.world.item.DyeColor;
import net.theobl.createworldofcolor.kinetics.belt.ModBeltCasingType;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

//Credits: Inspired from "Create: Encased" belt casing mixin code, by Iglee42 (MIT Licensed)
@Mixin(BeltBlockEntity.CasingType.class)
public abstract class BeltCasingTypeMixin {
    @Shadow
    @Final
    @Mutable
    private static BeltBlockEntity.CasingType[] $VALUES;

    @Invoker("<init>")
    public static BeltBlockEntity.CasingType encased$initInvoker(String internalName, int internalId) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>",at = @At("TAIL"))
    private static void cmr$clinit(CallbackInfo ci) {
        for(DyeColor color : DyeColor.values())
            ModBeltCasingType.DYED_ANDESITE.put(color, encased$addVariant(color.getName().toUpperCase(Locale.ROOT) + "_ANDESITE"));
    }

    @Unique
    private static BeltBlockEntity.CasingType encased$addVariant(String internalName) {
        ArrayList<BeltBlockEntity.CasingType> variants = new ArrayList<>(Arrays.asList($VALUES));
        BeltBlockEntity.CasingType casing = encased$initInvoker(internalName, variants.get(variants.size() - 1).ordinal() + 1);
        variants.add(casing);
        $VALUES = variants.toArray(new BeltBlockEntity.CasingType[0]);
        return casing;
    }
}
