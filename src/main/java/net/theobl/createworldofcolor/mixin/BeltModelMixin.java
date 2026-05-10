package net.theobl.createworldofcolor.mixin;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import com.simibubi.create.content.kinetics.belt.BeltModel;
import com.simibubi.create.foundation.model.BakedQuadHelper;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import net.theobl.createworldofcolor.ModSpriteShifts;
import net.theobl.createworldofcolor.kinetics.belt.ModBeltCasingType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

//Credits: Inspired from "Create: Encased" belt casing mixin code, by Iglee42 (MIT Licensed)
@Mixin(BeltModel.class)
public abstract class BeltModelMixin {

    @Shadow
    @Final
    public static ModelProperty<BeltBlockEntity.CasingType> CASING_PROPERTY;

    @Inject(method = "getParticleIcon", at = @At(value = "RETURN", ordinal = 2),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private void encased$customParticle(ModelData data, CallbackInfoReturnable<TextureAtlasSprite> cir, BeltBlockEntity.CasingType type) {
        for (DyeColor color : DyeColor.values()) {
            if (type.equals(ModBeltCasingType.DYED_ANDESITE.get(color))) {
                cir.setReturnValue(ModSpriteShifts.DYED_ANDESITE_BELT_CASING.get(color).getOriginal());
            }
        }
    }


        @Inject(method = "getQuads",
            at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z", ordinal = 0, shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private void encased$customCasingCover(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType renderType, CallbackInfoReturnable<List<BakedQuad>> cir, List<BakedQuad> quads, boolean cover, BeltBlockEntity.CasingType type, boolean brassCasing, boolean alongX, BakedModel coverModel) {
        for (DyeColor color : DyeColor.values()) {
            if (type.equals(ModBeltCasingType.DYED_ANDESITE.get(color))) {
                quads.removeAll(coverModel.getQuads(state, side, rand, extraData, renderType));
                quads.addAll((alongX ? AllPartialModels.ANDESITE_BELT_COVER_X : AllPartialModels.ANDESITE_BELT_COVER_Z).get().getQuads(state, side, rand, extraData, renderType));
                return;
            }
        }
    }

    @Inject(method = "getQuads",
            at = @At(value = "INVOKE",target = "Ljava/util/List;size()I", ordinal = 0, shift = At.Shift.BEFORE),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            cancellable = true)
    private void encased$customCasingType(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType renderType, CallbackInfoReturnable<List<BakedQuad>> cir, List<BakedQuad> quads, boolean cover, BeltBlockEntity.CasingType type, boolean brassCasing, int i) {
        for (DyeColor color : DyeColor.values()) {
            if (type.equals(ModBeltCasingType.DYED_ANDESITE.get(color))) {
                cir.setReturnValue(encased$getQuadsForSprite(quads, ModSpriteShifts.DYED_ANDESITE_BELT_CASING.get(color)));
                return;
            }
        }
    }

    @Unique
    private static List<BakedQuad> encased$getQuadsForSprite(List<BakedQuad> quads, SpriteShiftEntry spriteShift) {
        for (int i = 0; i < quads.size(); i++) {
            BakedQuad quad = quads.get(i);
            TextureAtlasSprite original = quad.getSprite();
            if (original != spriteShift.getOriginal())
                continue;

            BakedQuad newQuad = BakedQuadHelper.clone(quad);
            int[] vertexData = newQuad.getVertices();

            for (int vertex = 0; vertex < 4; vertex++) {
                float u = BakedQuadHelper.getU(vertexData, vertex);
                float v = BakedQuadHelper.getV(vertexData, vertex);
                BakedQuadHelper.setU(vertexData, vertex, spriteShift.getTargetU(u));
                BakedQuadHelper.setV(vertexData, vertex, spriteShift.getTargetV(v));
            }

            quads.set(i, newQuad);
        }


        return quads;
    }

}
