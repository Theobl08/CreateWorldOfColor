package net.theobl.createworldofcolor.trains.track;

import com.simibubi.create.content.trains.track.TrackMaterial;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.item.DyeColor;
import net.theobl.createworldofcolor.CreateWorldOfColor;
import net.theobl.createworldofcolor.ModBlocks;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumMap;

import static com.simibubi.create.content.trains.track.TrackMaterialFactory.make;

public class ModTrackMaterial {
    public static final EnumMap<DyeColor, TrackMaterial> DYED = new EnumMap<>(DyeColor.class);

    static {
        for(DyeColor color : DyeColor.values()) {
            DYED.put(color, make(CreateWorldOfColor.asResource(color.getName()))
                    .lang(StringUtils.capitalize(color.getName()))
                    .block(NonNullSupplier.lazy(() -> ModBlocks.TRACKS.get(color)))
                    .particle(CreateWorldOfColor.asResource("block/copper/" + color.getName() + "_copper_roof_top"))
                    .standardModels()
                    .build());
        }
    }

    public static void register() {}
}
