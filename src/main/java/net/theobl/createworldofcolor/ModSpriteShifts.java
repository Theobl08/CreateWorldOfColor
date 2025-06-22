package net.theobl.createworldofcolor;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import net.createmod.catnip.lang.Lang;
import net.minecraft.world.item.DyeColor;

import java.util.EnumMap;
import java.util.Map;

public class ModSpriteShifts {
    public static final Map<DyeColor, CTSpriteShiftEntry> COLORED_COPPER_SHINGLES = new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, CTSpriteShiftEntry> COLORED_COPPER_TILES = new EnumMap<>(DyeColor.class);

    public static final Map<DyeColor, CTSpriteShiftEntry> COLORED_TANKS = new EnumMap<>(DyeColor.class),
    COLORED_TANKS_TOP = new EnumMap<>(DyeColor.class),
    COLORED_TANKS_INNER = new EnumMap<>(DyeColor.class);

    public static final Map<DyeColor, CTSpriteShiftEntry> DYED_SCAFFOLDS = new EnumMap<>(DyeColor.class),
            DYED_SCAFFOLDS_INSIDE = new EnumMap<>(DyeColor.class);

    public static final Map<DyeColor, CTSpriteShiftEntry> DYED_CASINGS = new EnumMap<>(DyeColor.class);

    static {
        populateMaps();
    }

    private static void populateMaps() {
        for(DyeColor color : DyeColor.values()) {
            String id = color.getSerializedName();
            DYED_SCAFFOLDS.put(color, horizontal("scaffold/"+ id + "_scaffold"));
            DYED_SCAFFOLDS_INSIDE.put(color, horizontal("scaffold/"+ id + "_scaffold_inside"));

            DYED_CASINGS.put(color, omni(id + "_casing"));

            COLORED_TANKS.put(color, getCT(AllCTTypes.RECTANGLE, id + "_fluid_tank"));
            COLORED_TANKS_TOP.put(color, getCT(AllCTTypes.RECTANGLE, id + "_fluid_tank_top"));
            COLORED_TANKS_INNER.put(color, getCT(AllCTTypes.RECTANGLE, id + "_fluid_tank_inner"));

            String pref = "copper/" + (Lang.asId(color.name()) + "_");
            COLORED_COPPER_SHINGLES.put(color, getCT(AllCTTypes.ROOF, pref + "copper_roof_top", pref + "copper_shingles_top"));
            COLORED_COPPER_TILES.put(color, getCT(AllCTTypes.ROOF, pref + "copper_roof_top", pref + "copper_tiles_top"));
        }
    }

    private static CTSpriteShiftEntry omni(String name) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, name);
    }

    private static CTSpriteShiftEntry horizontal(String name) {
        return getCT(AllCTTypes.HORIZONTAL, name);
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return CTSpriteShifter.getCT(type, CreateWorldOfColor.asResource("block/" + blockTextureName),
                CreateWorldOfColor.asResource("block/" + connectedTextureName + "_connected"));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }
}
