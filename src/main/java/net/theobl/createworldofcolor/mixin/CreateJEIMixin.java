package net.theobl.createworldofcolor.mixin;

import com.simibubi.create.compat.jei.CreateJEI;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.SpoutCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.world.item.DyeColor;
import net.theobl.createworldofcolor.ModBlocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CreateJEI.class)
public class CreateJEIMixin {
    @Shadow
    @Final
    private List<CreateRecipeCategory<?>> allCategories;

    @Inject(method = "registerRecipeCatalysts",at = @At("RETURN"))
    private void inject(IRecipeCatalystRegistration registration, CallbackInfo ci) {
        for(CreateRecipeCategory<?> category : allCategories) {
            if (category instanceof SpoutCategory) {
                for (DyeColor color : DyeColor.values()) {
                    registration.addRecipeCatalyst(ModBlocks.SPOUTS.get(color).asStack(), category.getRecipeType());
                }
            }
        }
    }
}
