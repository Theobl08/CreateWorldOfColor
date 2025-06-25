package net.theobl.createworldofcolor.data;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.theobl.createworldofcolor.ModTags;

public class ModTagsProvider {
    public static void genBlockTags(RegistrateTagsProvider<Block> provIn) {
        TagGen.CreateTagsProvider<Block> prov = new TagGen.CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);

        prov.tag(ModTags.Blocks.FLUID_TANKS.tag).add(AllBlocks.FLUID_TANK.get(), AllBlocks.CREATIVE_FLUID_TANK.get());
    }

    public static void genItemTags(RegistrateTagsProvider<Item> provIn) {
        TagGen.CreateTagsProvider<Item> prov = new TagGen.CreateTagsProvider<>(provIn, Item::builtInRegistryHolder);

        prov.tag(ModTags.Items.DYEABLE_BARS.tag).add(AllBlocks.COPPER_BARS.asItem());
        prov.tag(ModTags.Items.DYEABLE_SCAFFOLDS.tag).add(AllBlocks.COPPER_SCAFFOLD.asItem());
        prov.tag(ModTags.Items.DYEABLE_DOORS.tag).add(AllBlocks.COPPER_DOOR.asItem());
        prov.tag(ModTags.Items.DYEABLE_LADDERS.tag).add(AllBlocks.COPPER_LADDER.asItem());
    }
}
