package net.theobl.createworldofcolor;

import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class ModTags {
    enum Mods {
        MOD(CreateWorldOfColor.MODID),
        COMMON("c");

        public final String id;

        Mods(String id) {
            this.id = id;
        }

        public ResourceLocation id(String path) {
            return ResourceLocation.fromNamespaceAndPath(this.id, path);
        }

        public ResourceLocation id(Enum<?> entry, @Nullable String pathOverride) {
            return this.id(pathOverride != null ? pathOverride : Lang.asId(entry.name()));
        }
    }

    public enum Blocks {
        FLUID_TANKS;

        public final TagKey<Block> tag;

        Blocks() {
            this(Mods.MOD);
        }

        Blocks(Mods namespace) {
            this(namespace, null);
        }

        Blocks(Mods namespace, String path) {
            tag = BlockTags.create(namespace.id(this, path));
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

        Items() {
            this(Mods.MOD);
        }

        Items(Mods namespace) {
            this(namespace, null);
        }

        Items(Mods namespace, String path) {
            tag = ItemTags.create(namespace.id(this, path));
        }
    }
}
