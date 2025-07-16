package net.theobl.createworldofcolor;

import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static com.simibubi.create.AllTags.optionalTag;

public class ModTags {
    enum Mods {

        MOD(CreateWorldOfColor.MODID, false, true),
        COMMON("c");

        public final String id;
        public final boolean optionalDefault;
        public final boolean alwaysDatagenDefault;

        Mods(String id) {
            this(id, true, false);
        }

        Mods(String id, boolean optionalDefault, boolean alwaysDatagenDefault) {
            this.id = id;
            this.optionalDefault = optionalDefault;
            this.alwaysDatagenDefault = alwaysDatagenDefault;
        }
    }

    public enum Blocks {
        FLUID_TANKS;

        public final TagKey<Block> tag;
        public final boolean alwaysDatagen;

        Blocks() {
            this(Mods.MOD);
        }

        Blocks(Mods namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        Blocks(Mods namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        Blocks(Mods namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        Blocks(Mods namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace.id, path == null ? Lang.asId(name()) : path);
            if (optional) {
                tag = optionalTag(BuiltInRegistries.BLOCK, id);
            } else {
                tag = BlockTags.create(id);
            }
            this.alwaysDatagen = alwaysDatagen;
        }
    }

    public enum Items {
        DYEABLE_BARS,
        DYEABLE_CASINGS,
        DYEABLE_DOORS,
        DYEABLE_LADDERS,
        DYEABLE_SCAFFOLDS,
        DYEABLE_TABLE_CLOTHS;

        public final TagKey<Item> tag;
        public final boolean alwaysDatagen;

        Items() {
            this(Mods.MOD);
        }

        Items(Mods namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        Items(Mods namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        Items(Mods namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        Items(Mods namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace.id, path == null ? Lang.asId(name()) : path);
            if (optional) {
                tag = optionalTag(BuiltInRegistries.ITEM, id);
            } else {
                tag = ItemTags.create(id);
            }
            this.alwaysDatagen = alwaysDatagen;
        }
    }
}
