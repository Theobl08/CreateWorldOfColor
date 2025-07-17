package net.theobl.createworldofcolor.fluids.hosePulley;

import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlock;
import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlockEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.theobl.createworldofcolor.ModBlockEntityTypes;

public class ColoredHosePulleyBlock extends HosePulleyBlock {
    public final DyeColor color;

    public ColoredHosePulleyBlock(Properties properties, DyeColor color) {
        super(properties);
        this.color = color;
    }

    @Override
    public BlockEntityType<? extends HosePulleyBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.COLORED_HOSE_PULLEYS.get(color).get();
    }
}
