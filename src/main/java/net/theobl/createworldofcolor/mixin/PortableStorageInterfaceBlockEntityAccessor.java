package net.theobl.createworldofcolor.mixin;

import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceBlockEntity;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PortableStorageInterfaceBlockEntity.class)
public interface PortableStorageInterfaceBlockEntityAccessor {
    @Accessor("connectedEntity")
    Entity createworldofcolor$getConnectedEntity();

    @Accessor("distance")
    float createworldofcolor$getDistance();

    @Accessor("transferTimer")
    int createworldofcolor$getTransferTimer();
}
