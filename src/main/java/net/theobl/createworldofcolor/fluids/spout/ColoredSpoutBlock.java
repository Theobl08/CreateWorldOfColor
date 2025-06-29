package net.theobl.createworldofcolor.fluids.spout;

import com.simibubi.create.content.fluids.spout.SpoutBlock;
import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.theobl.createworldofcolor.ModBlockEntityTypes;

public class ColoredSpoutBlock extends SpoutBlock {
    protected final DyeColor color;
    public ColoredSpoutBlock(Properties properties, DyeColor color) {
        super(properties);
        this.color = color;
    }

    @Override
    public BlockEntityType<? extends SpoutBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.COLORED_SPOUTS.get(color).get();
    }
}
