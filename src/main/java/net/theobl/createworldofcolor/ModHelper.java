package net.theobl.createworldofcolor;

import com.simibubi.create.foundation.block.DyedBlockList;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;

public class ModHelper {
    public static boolean isColoredBlock(BlockState state, DyedBlockList<?> coloredBlocks) {
        for (DyeColor color : DyeColor.values()) {
            if(coloredBlocks.get(color).has(state))
                return true;
        }
        return false;
    }
}
