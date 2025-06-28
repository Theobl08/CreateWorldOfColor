package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlockEntity;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlockEntity;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.theobl.createworldofcolor.ModBlocks;
import net.theobl.createworldofcolor.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SteamEngineBlockEntity.class)
public abstract class SteamEngineBlockEntityMixin extends BlockEntity {

    public SteamEngineBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
    super(type, pos, blockState);
    }

    @Shadow
    protected ScrollOptionBehaviour<WindmillBearingBlockEntity.RotationDirection> movementDirection;

    @Shadow
    @OnlyIn(Dist.CLIENT)
    protected abstract void spawnParticles();

    @Inject(method = "isValid", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private void isValid(CallbackInfoReturnable<Boolean> cir, @Local Direction dir, @Local Level level) {
        if (level == null) {
            cir.setReturnValue(false);
            return;
        }

        cir.setReturnValue(level.getBlockState(((SteamEngineBlockEntity)(Object) this).getBlockPos().relative(dir)).is(ModTags.Blocks.FLUID_TANKS.tag));
    }

    @OnlyIn(Dist.CLIENT)
    @ModifyExpressionValue(method = "getTargetAngle", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean getTargetAngle(boolean original, @Local(name = "blockState") BlockState blockState) {
        return original || (ModBlocks.STEAM_ENGINES.get(DyeColor.BLACK).has(blockState) ||
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

    @Inject(method = "tick", at = @At(value = "RETURN", ordinal = 5))
    private void test(CallbackInfo ci, @Local FluidTankBlockEntity tank, @Local(ordinal = 1) BlockState blockState,
                      @Local boolean verticalTarget, @Local Direction.Axis targetAxis, @Local PoweredShaftBlockEntity shaft) {
        if(!(AllBlocks.STEAM_ENGINE.has(blockState) ||
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
                ModBlocks.STEAM_ENGINES.get(DyeColor.YELLOW).has(blockState)))
            return;
        Direction facing = SteamEngineBlock.getFacing(blockState);
        if (facing.getAxis() == Direction.Axis.Y)
            facing = blockState.getValue(SteamEngineBlock.FACING);

        float efficiency = Mth.clamp(tank.boiler.getEngineEfficiency(tank.getTotalTankSize()), 0, 1);
        if (efficiency > 0)

            ((SteamEngineBlockEntity)(Object)this).award(AllAdvancements.STEAM_ENGINE);

        int conveyedSpeedLevel =
                efficiency == 0 ? 1 : verticalTarget ? 1 : (int) GeneratingKineticBlockEntity.convertToDirection(1, facing);
        if (targetAxis == Direction.Axis.Z)
            conveyedSpeedLevel *= -1;
        if (movementDirection.get() == WindmillBearingBlockEntity.RotationDirection.COUNTER_CLOCKWISE)
            conveyedSpeedLevel *= -1;

        float shaftSpeed = shaft.getTheoreticalSpeed();
        if (shaft.hasSource() && shaftSpeed != 0 && conveyedSpeedLevel != 0
                && (shaftSpeed > 0) != (conveyedSpeedLevel > 0)) {
            movementDirection.setValue(1 - movementDirection.get()
                    .ordinal());
            conveyedSpeedLevel *= -1;
        }

        shaft.update(worldPosition, conveyedSpeedLevel, efficiency);

        if (!level.isClientSide)
            return;

        CatnipServices.PLATFORM.executeOnClientOnly(() -> this::spawnParticles);
    }
}
