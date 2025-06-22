package net.theobl.createworldofcolor.fluids.tank;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.createworldofcolor.ModBlockEntityTypes;

public class ColoredFluidTankBlock extends FluidTankBlock {
    protected final DyeColor color;
    public ColoredFluidTankBlock(Properties properties, DyeColor color) {
        super(properties, false);
        this.color = color;
    }

    public static boolean isTank(BlockState state) {
        return (state.getBlock() instanceof ColoredFluidTankBlock);
    }

    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasBlockEntity() && (state.getBlock() != newState.getBlock() || !newState.hasBlockEntity())) {
            BlockEntity be = world.getBlockEntity(pos);
            if (!(be instanceof FluidTankBlockEntity))
                return;
            ColoredFluidTankBlockEntity tankBE = (ColoredFluidTankBlockEntity) be;
            world.removeBlockEntity(pos);
            ConnectivityHandler.splitMulti(tankBE);
        }
    }

    @Override
    public BlockEntityType<? extends FluidTankBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.COLORED_TANKS.get(color).get();
    }
}
