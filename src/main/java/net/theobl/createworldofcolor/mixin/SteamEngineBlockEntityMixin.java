package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.theobl.createworldofcolor.ModBlocks;
import net.theobl.createworldofcolor.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SteamEngineBlockEntity.class)
public abstract class SteamEngineBlockEntityMixin {
    @Inject(method = "isValid", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private void isValid(CallbackInfoReturnable<Boolean> cir, @Local Direction dir, @Local Level level) {
        if (level == null)
            cir.setReturnValue(false);

        cir.setReturnValue(level.getBlockState(((SteamEngineBlockEntity)(Object) this).getBlockPos().relative(dir)).is(ModTags.Blocks.FLUID_TANKS.tag));
    }

    @OnlyIn(Dist.CLIENT)
    @Redirect(method = "getTargetAngle", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean getTargetAngle(BlockEntry<SteamEngineBlock> instance, BlockState state, @Local BlockState blockState) {
        return (AllBlocks.STEAM_ENGINE.has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.BLACK).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.BLUE).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.BROWN).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.CYAN).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.GRAY).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.GREEN).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.LIGHT_BLUE).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.LIGHT_GRAY).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.LIME).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.MAGENTA).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.ORANGE).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.PINK).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.PURPLE).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.RED).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.WHITE).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.YELLOW).has(blockState));
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean tick(BlockEntry<SteamEngineBlock> instance, BlockState state, @Local(ordinal = 1) BlockState blockState) {
        return (AllBlocks.STEAM_ENGINE.has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.BLACK).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.BLUE).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.BROWN).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.CYAN).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.GRAY).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.GREEN).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.LIGHT_BLUE).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.LIGHT_GRAY).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.LIME).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.MAGENTA).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.ORANGE).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.PINK).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.PURPLE).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.RED).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.WHITE).has(blockState) ||
                ModBlocks.STEAM_ENGINES.get(DyeColor.YELLOW).has(blockState));
    }
}
