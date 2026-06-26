package net.theobl.createworldofcolor;

import com.simibubi.create.Create;
import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonItem;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static net.theobl.createworldofcolor.CreateWorldOfColor.REGISTRATE;

public class ModItems {

    static {
        REGISTRATE.defaultCreativeTab("createworldofcolor");
    }

    public static final Map<DyeColor, ItemEntry<PotatoCannonItem>> POTATO_CANNONS = dyedItemList(color ->
            REGISTRATE.item(color.getName() + "_potato_cannon", PotatoCannonItem::new)
                    .properties(p -> p.durability(100))
                    .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "item.create.potato_cannon"))
                    .model((c, p) -> {
                        p.withExistingParent("item/" + c.getName(), Create.asResource("item/potato_cannon/item"))
                                .texture("1", p.modLoc("item/" + c.getName()))
                                .texture("particle", p.modLoc("block/" + color.getName() + "_casing"));
                    })
                    .tag(Tags.Items.ENCHANTABLES, ItemTags.DURABILITY_ENCHANTABLE, ItemTags.BOW_ENCHANTABLE)
                    .register()
    );

    private static <T extends Item> Map<DyeColor, ItemEntry<T>> dyedItemList(Function<DyeColor, ItemEntry<T>> filler) {
        Map<DyeColor, ItemEntry<T>> values = new HashMap<>();
        for (DyeColor dyeColor : DyeColor.values()) {
            values.put(dyeColor, filler.apply(dyeColor));
        }
        return values;
    }


    // Load this class

    public static void register() {
    }
}
