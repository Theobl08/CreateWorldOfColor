package net.theobl.createworldofcolor.blockEntity;

import com.simibubi.create.foundation.blockEntity.IMultiBlockEntityContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

public interface IMultiBlockEntityContainerExtension extends IMultiBlockEntityContainer {
    boolean canConnectWith(BlockPos pos, BlockGetter getter);
}
