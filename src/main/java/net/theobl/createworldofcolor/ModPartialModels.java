package net.theobl.createworldofcolor;

import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import com.simibubi.create.content.fluids.FluidTransportBehaviour.AttachmentTypes.ComponentPartials;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static com.simibubi.create.AllPartialModels.FOLDING_DOORS;

public class ModPartialModels {
    //Create casings map
    public static final Map<DyeColor, PartialModel> COLORED_FLUID_PIPE_CASINGS = new EnumMap<>(DyeColor.class);
    //Create attachment map
    public static final Map<ComponentPartials, Map<DyeColor, Map<Direction, PartialModel>>> COLORED_PIPE_ATTACHMENTS = new EnumMap<>(ComponentPartials.class);
    static {
        for(DyeColor color : DyeColor.values()) {
            String id = color.getSerializedName();
            putFoldingDoor(id + "_door");

            COLORED_FLUID_PIPE_CASINGS.put(color, block(color.getName() + "_fluid_pipe/casing"));
        }

        for (ComponentPartials type : ComponentPartials.values()) {
            Map<DyeColor, Map<Direction, PartialModel>> colorMap = new EnumMap<>(DyeColor.class);
            for (DyeColor color : DyeColor.values()) {
                Map<Direction, PartialModel> map = new HashMap<>();
                for (Direction d : Iterate.directions) {
                    String asId = Lang.asId(type.name());
                    map.put(d, block(color.getName() + "_fluid_pipe/" + asId + "/" + Lang.asId(d.getName())));
                }
                colorMap.put(color, map);
            }
            COLORED_PIPE_ATTACHMENTS.put(type, colorMap);
        }
    }

    private static void putFoldingDoor(String path) {
        FOLDING_DOORS.put(CreateWorldOfColor.asResource(path),
                Couple.create(block(path + "/fold_left"), block(path + "/fold_right")));
    }

    private static PartialModel block(String path) {
        return PartialModel.of(CreateWorldOfColor.asResource("block/" + path));
    }

    public static void init() {
        // init static fields
    }
}
