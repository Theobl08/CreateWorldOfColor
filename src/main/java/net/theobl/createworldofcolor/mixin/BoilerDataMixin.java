package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.fluids.tank.BoilerData;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BoilerData.class)
public class BoilerDataMixin {
    @Redirect(method = "evaluate", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 0))
    private boolean evaluate(BlockEntry<SteamEngineBlock> instance, BlockState state, @Local(ordinal = 1) BlockState blockState) {
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
