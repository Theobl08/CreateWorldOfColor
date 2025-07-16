package net.theobl.createworldofcolor.data;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.behaviour.DoorMovingInteraction;
import com.simibubi.create.content.decoration.MetalScaffoldingBlock;
import com.simibubi.create.content.decoration.MetalScaffoldingBlockItem;
import com.simibubi.create.content.decoration.MetalScaffoldingCTBehaviour;
import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorBlock;
import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorMovementBehaviour;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockItem;
import com.simibubi.create.content.logistics.tableCloth.TableClothModel;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.theobl.createworldofcolor.CreateWorldOfColor;
import net.theobl.createworldofcolor.ModTags;

import static com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour.interactionBehaviour;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class ModBuilderTransformers {

    public static <B extends SlidingDoorBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> slidingDoor(String type, Ingredient ingredient) {
        return b -> b.initialProperties(() -> Blocks.IRON_DOOR)
                .properties(p -> p.requiresCorrectToolForDrops()
                        .strength(3.0F, 6.0F))
                .blockstate(ModBlockStateGen.slidingDoor(type))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(pickaxeOnly())
                .onRegister(interactionBehaviour(new DoorMovingInteraction()))
                .onRegister(movementBehaviour(new SlidingDoorMovementBehaviour()))
                .tag(BlockTags.DOORS)
                .tag(BlockTags.WOODEN_DOORS) // for villager AI
                .tag(AllTags.AllBlockTags.NON_DOUBLE_DOOR.tag)
                .loot((lr, block) -> lr.add(block, lr.createDoorTable(block)))
                .item()
                .tag(ItemTags.DOORS)
                .tag(AllTags.AllItemTags.CONTRAPTION_CONTROLLED.tag)
                .tag(ModTags.Items.DYEABLE_DOORS.tag)
                .model((c, p) -> p.blockSprite(c, p.modLoc("item/" + type + "_door")))
                .build();
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> ladder(String name, Ingredient ingredient, MapColor color) {
        return b -> b.initialProperties(() -> Blocks.LADDER)
                .properties(p -> p.mapColor(color))
                //.addLayer(() -> RenderType::cutout)
                .blockstate((c, p) -> p.horizontalBlock(c.get(), p.models()
                        .withExistingParent(c.getName(), Create.asResource("block/ladder"))
                        .texture("0", p.modLoc("block/ladder_" + name + "_hoop"))
                        .texture("1", p.modLoc("block/ladder_" + name))
                        .texture("particle", p.modLoc("block/ladder_" + name))
                        .renderType("cutout")))
                .properties(p -> p.sound(SoundType.COPPER))
                .transform(pickaxeOnly())
                .tag(BlockTags.CLIMBABLE)
                .item()
                .tag(ModTags.Items.DYEABLE_LADDERS.tag)
                .model((c, p) -> p.blockSprite(c::get, p.modLoc("block/ladder_" + name)))
                .build();
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> scaffold(String name,
                                                                                         MapColor color, CTSpriteShiftEntry scaffoldShift,
                                                                                         CTSpriteShiftEntry scaffoldInsideShift, CTSpriteShiftEntry casingShift) {
        return b -> b.initialProperties(() -> Blocks.SCAFFOLDING)
                .properties(p -> p.sound(SoundType.COPPER)
                        .mapColor(color))
                //.addLayer(() -> RenderType::cutout)
                .blockstate(ModBlockStateGen.scaffold(name))
                .onRegister(connectedTextures(
                        () -> new MetalScaffoldingCTBehaviour(scaffoldShift, scaffoldInsideShift, casingShift)))
                .transform(pickaxeOnly())
                .tag(BlockTags.CLIMBABLE)
                .item(MetalScaffoldingBlockItem::new)
                .tag(ModTags.Items.DYEABLE_SCAFFOLDS.tag)
                .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/" + c.getName())))
                .build();
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> tableCloth(String name,
                                                                                           NonNullSupplier<? extends Block> initialProps, boolean dyed) {
        return b -> {
            TagKey<Block> soundTag = dyed ? BlockTags.COMBINATION_STEP_SOUND_BLOCKS : BlockTags.INSIDE_STEP_SOUND_BLOCKS;

            ItemBuilder<TableClothBlockItem, BlockBuilder<B, P>> item = b.initialProperties(initialProps)
                    .addLayer(() -> RenderType::cutoutMipped)
                    .blockstate((c, p) -> p.simpleBlock(c.get(), p.models()
                            .withExistingParent(name + "_copper_table_cloth", Create.asResource("block/table_cloth/block"))
                            .texture("0", p.modLoc("block/table_cloth/" + name))))
                    .onRegister(CreateRegistrate.blockModel(() -> TableClothModel::new))
                    .tag(AllTags.AllBlockTags.TABLE_CLOTHS.tag, soundTag)
                    .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.create.table_cloth"))
                    .item(TableClothBlockItem::new);

            return item.model((c, p) ->
                            p.withExistingParent(name + "_copper_table_cloth", Create.asResource("block/table_cloth/item"))
                            .texture("0", p.modLoc("block/table_cloth/" + name)))
                    .tag(AllTags.AllItemTags.TABLE_CLOTHS.tag, ModTags.Items.DYEABLE_TABLE_CLOTHS.tag)
                    .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
                            .requires(c.get())
                            .unlockedBy("has_" + c.getName(), RegistrateRecipeProvider.has(c.get()))
                            .save(p, CreateWorldOfColor.asResource("crafting/logistics/" + c.getName() + "_clear")))
                    .build();
        };
    }
}
