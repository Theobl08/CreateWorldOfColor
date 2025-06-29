package net.theobl.createworldofcolor.mixin;

import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SpoutBlockEntity.class)
public interface SpoutBlockEntityAccessor {
    @Accessor("tank")
    SmartFluidTankBehaviour getTank();
}
