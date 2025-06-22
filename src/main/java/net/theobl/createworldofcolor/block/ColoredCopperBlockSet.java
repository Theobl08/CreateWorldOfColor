package net.theobl.createworldofcolor.block;

import com.simibubi.create.foundation.block.CopperBlockSet;
import com.simibubi.create.foundation.block.CopperRegistries;
import com.simibubi.create.foundation.block.CreateCopperStairBlock;
import com.simibubi.create.foundation.block.CreateWeatheringCopperStairBlock;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ColoredCopperBlockSet {
    protected static final DyeColor[] DYE_COLORS = DyeColor.values();
    protected static final int DYE_COLOR_COUNT = DYE_COLORS.length;

    public static final Variant<?>[] DEFAULT_VARIANTS =
            new Variant<?>[]{BlockVariant.INSTANCE, SlabVariant.INSTANCE, StairVariant.INSTANCE};

    protected final String name;
    protected final String generalDirectory; // Leave empty for root folder
    protected final Variant<?>[] variants;
    protected final Map<Variant<?>, BlockEntry<?>[]> entries = new HashMap<>();
    protected final NonNullBiConsumer<DataGenContext<Block, ?>, RegistrateRecipeProvider> mainBlockRecipe;
    protected final String endTextureName;
    protected final NonNullBiConsumer<DyeColor, Block> onRegister;

    public ColoredCopperBlockSet(AbstractRegistrate<?> registrate, String name, String endTextureName, Variant<?>[] variants) {
        this(registrate, name, endTextureName, variants, NonNullBiConsumer.noop(), "copper/", NonNullBiConsumer.noop());
    }

    public ColoredCopperBlockSet(AbstractRegistrate<?> registrate, String name, String endTextureName, Variant<?>[] variants, String generalDirectory) {
        this(registrate, name, endTextureName, variants, NonNullBiConsumer.noop(), generalDirectory, NonNullBiConsumer.noop());
    }

    public ColoredCopperBlockSet(AbstractRegistrate<?> registrate, String name, String endTextureName, Variant<?>[] variants, NonNullBiConsumer<DataGenContext<Block, ?>, RegistrateRecipeProvider> mainBlockRecipe) {
        this(registrate, name, endTextureName, variants, mainBlockRecipe, "copper/", NonNullBiConsumer.noop());
    }

    public ColoredCopperBlockSet(AbstractRegistrate<?> registrate, String name, String endTextureName, Variant<?>[] variants, NonNullBiConsumer<DataGenContext<Block, ?>, RegistrateRecipeProvider> mainBlockRecipe, NonNullBiConsumer<DyeColor, Block> onRegister) {
        this(registrate, name, endTextureName, variants, mainBlockRecipe, "copper/", onRegister);
    }

    public ColoredCopperBlockSet(AbstractRegistrate<?> registrate, String name, String endTextureName, Variant<?>[] variants,
                          NonNullBiConsumer<DataGenContext<Block, ?>, RegistrateRecipeProvider> mainBlockRecipe, String generalDirectory, NonNullBiConsumer<DyeColor, Block> onRegister) {
        this.name = name;
        this.generalDirectory = generalDirectory;
        this.endTextureName = endTextureName;
        this.variants = variants;
        this.mainBlockRecipe = mainBlockRecipe;
        this.onRegister = onRegister;

        for (boolean waxed : Iterate.falseAndTrue) {
            for (Variant<?> variant : this.variants) {
                BlockEntry<?>[] entries =
                        waxed ? this.entries.get(variant) : new BlockEntry<?>[DYE_COLOR_COUNT * 2];
                for (DyeColor state : DYE_COLORS) {
                    int index = getIndex(state, waxed);
                    BlockEntry<?> entry = createEntry(registrate, variant, state, waxed);
                    entries[index] = entry;

                    if (waxed) {
                        ColoredCopperRegistries.addWaxable(() -> entries[getIndex(state, false)].get(), entry::get);
                    }
//                    else if (state != DyeColor.WHITE) {
//                        ColoredCopperRegistries.addWeathering(
//                                () -> entries[getIndex(DYE_COLORS[state.ordinal() - 1], false)].get(), () -> entry.get());
//                    }
                }
                if (!waxed)
                    this.entries.put(variant, entries);
            }
        }
    }

    protected <T extends Block> BlockEntry<?> createEntry(AbstractRegistrate<?> registrate, Variant<T> variant,
                                                          DyeColor state, boolean waxed) {
        String name = "";
        if (waxed) {
            name += "waxed_";
        }
        name += getDyeColorPrefix(state);
        name += this.name;

        String suffix = variant.getSuffix();
        if (!suffix.isEmpty())
            name = Lang.nonPluralId(name);

        name += suffix;

        Block baseBlock = Blocks.COPPER_BLOCK;
        BlockBuilder<T, ?> builder = registrate.block(name, variant.getFactory(this, state, waxed))
                .initialProperties(() -> baseBlock)
                .properties(p -> p.mapColor(state))
                .loot((lt, block) -> variant.generateLootTable(lt, block, this, state, waxed))
                .blockstate((ctx, prov) -> variant.generateBlockState(ctx, prov, this, state, waxed))
                .recipe((c, p) -> variant.generateRecipes(entries.get(BlockVariant.INSTANCE)[state.ordinal()], c, p))
                .transform(TagGen.pickaxeOnly())
                .onRegister(block -> onRegister.accept(state, block))
                .tag(BlockTags.NEEDS_STONE_TOOL)
                .simpleItem();

        if (variant == BlockVariant.INSTANCE && state == DyeColor.WHITE)
            builder.recipe(mainBlockRecipe::accept);

        if (variant == StairVariant.INSTANCE)
            builder.tag(BlockTags.STAIRS);

        if (variant == SlabVariant.INSTANCE)
            builder.tag(BlockTags.SLABS);

        if (waxed) {
            builder.recipe((ctx, prov) -> {
                Block unwaxed = get(variant, state, false).get();
                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ctx.get())
                        .requires(unwaxed)
                        .requires(Items.HONEYCOMB)
                        .unlockedBy("has_unwaxed", RegistrateRecipeProvider.has(unwaxed))
                        .save(prov, ResourceLocation.fromNamespaceAndPath(ctx.getId()
                                .getNamespace(), "crafting/" + generalDirectory + ctx.getName() + "_from_honeycomb"));
            });
        }

        return builder.register();
    }

    protected int getIndex(DyeColor state, boolean waxed) {
        return state.ordinal() + (waxed ? DYE_COLOR_COUNT : 0);
    }

    public String getName() {
        return name;
    }

    public String getEndTextureName() {
        return endTextureName;
    }

    public Variant<?>[] getVariants() {
        return variants;
    }

    public boolean hasVariant(Variant<?> variant) {
        return ArrayUtils.contains(variants, variant);
    }

    public BlockEntry<?> get(Variant<?> variant, DyeColor state, boolean waxed) {
        BlockEntry<?>[] entries = this.entries.get(variant);
        if (entries != null) {
            return entries[getIndex(state, waxed)];
        }
        return null;
    }

    public BlockEntry<?> getStandard() {
        return get(BlockVariant.INSTANCE, DyeColor.WHITE, false);
    }

    public static String getDyeColorPrefix(DyeColor state) {
            return state.name().toLowerCase(Locale.ROOT) + "_";
    }

    public interface Variant<T extends Block> {
        String getSuffix();

        NonNullFunction<BlockBehaviour.Properties, T> getFactory(ColoredCopperBlockSet blocks, DyeColor state, boolean waxed);

        default void generateLootTable(RegistrateBlockLootTables lootTable, T block, ColoredCopperBlockSet blocks,
                                       DyeColor state, boolean waxed) {
            lootTable.dropSelf(block);
        }

        void generateRecipes(BlockEntry<?> blockVariant, DataGenContext<Block, T> ctx, RegistrateRecipeProvider prov);

        void generateBlockState(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, ColoredCopperBlockSet blocks,
                                DyeColor state, boolean waxed);
    }

    public static class BlockVariant implements Variant<Block> {
        public static final BlockVariant INSTANCE = new BlockVariant();

        protected BlockVariant() {
        }

        @Override
        public String getSuffix() {
            return "";
        }

        @Override
        public NonNullFunction<BlockBehaviour.Properties, Block> getFactory(ColoredCopperBlockSet blocks, DyeColor state, boolean waxed) {
            if (waxed) {
                return Block::new;
            } else {
                return p -> new WeatheringCopperFullBlock(WeatheringCopper.WeatherState.UNAFFECTED, p);
            }
        }

        @Override
        public void generateBlockState(DataGenContext<Block, Block> ctx, RegistrateBlockstateProvider prov,
                                       ColoredCopperBlockSet blocks, DyeColor state, boolean waxed) {
            Block block = ctx.get();
            String path = RegisteredObjectsHelper.getKeyOrThrow(block)
                    .getPath();
            String baseLoc = ModelProvider.BLOCK_FOLDER + "/" + blocks.generalDirectory + getDyeColorPrefix(state);

            ResourceLocation texture = prov.modLoc(baseLoc + blocks.getName());
            if (Objects.equals(blocks.getName(), blocks.getEndTextureName())) {
                // End texture and base texture are equal, so we should use cube_all.
                prov.simpleBlock(block, prov.models().cubeAll(path, texture));
            } else {
                // End texture and base texture aren't equal, so we should use cube_column.
                ResourceLocation endTexture = prov.modLoc(baseLoc + blocks.getEndTextureName());
                prov.simpleBlock(block, prov.models()
                        .cubeColumn(path, texture, endTexture));
            }

        }

        @Override
        public void generateRecipes(BlockEntry<?> blockVariant, DataGenContext<Block, Block> ctx,
                                    RegistrateRecipeProvider prov) {
        }

    }

    public static class SlabVariant implements Variant<SlabBlock> {
        public static final SlabVariant INSTANCE = new SlabVariant();

        protected SlabVariant() {
        }

        @Override
        public String getSuffix() {
            return "_slab";
        }

        @Override
        public NonNullFunction<BlockBehaviour.Properties, SlabBlock> getFactory(ColoredCopperBlockSet blocks, DyeColor state, boolean waxed) {
            if (waxed) {
                return SlabBlock::new;
            } else {
                return p -> new WeatheringCopperSlabBlock(WeatheringCopper.WeatherState.UNAFFECTED, p);
            }
        }

        @Override
        public void generateLootTable(RegistrateBlockLootTables lootTable, SlabBlock block, ColoredCopperBlockSet blocks,
                                      DyeColor state, boolean waxed) {
            lootTable.add(block, lootTable.createSlabItemTable(block));
        }

        @Override
        public void generateBlockState(DataGenContext<Block, SlabBlock> ctx, RegistrateBlockstateProvider prov,
                                       ColoredCopperBlockSet blocks, DyeColor state, boolean waxed) {
            ResourceLocation fullModel =
                    prov.modLoc(ModelProvider.BLOCK_FOLDER + "/" + getDyeColorPrefix(state) + blocks.getName());

            String baseLoc = ModelProvider.BLOCK_FOLDER + "/" + blocks.generalDirectory + getDyeColorPrefix(state);
            ResourceLocation texture = prov.modLoc(baseLoc + blocks.getName());
            ResourceLocation endTexture = prov.modLoc(baseLoc + blocks.getEndTextureName());

            prov.slabBlock(ctx.get(), fullModel, texture, endTexture, endTexture);
        }

        @Override
        public void generateRecipes(BlockEntry<?> blockVariant, DataGenContext<Block, SlabBlock> ctx,
                                    RegistrateRecipeProvider prov) {
            prov.slab(DataIngredient.items(blockVariant.get()), RecipeCategory.BUILDING_BLOCKS, ctx, null, true);
        }
    }

    public static class StairVariant implements Variant<StairBlock> {
        public static final StairVariant INSTANCE = new StairVariant(BlockVariant.INSTANCE);

        protected final Variant<?> parent;

        protected StairVariant(Variant<?> parent) {
            this.parent = parent;
        }

        @Override
        public String getSuffix() {
            return "_stairs";
        }

        @Override
        public NonNullFunction<BlockBehaviour.Properties, StairBlock> getFactory(ColoredCopperBlockSet blocks, DyeColor state,
                                                                                 boolean waxed) {
            if (!blocks.hasVariant(parent)) {
                throw new IllegalStateException(
                        "Cannot add StairVariant '" + this + "' without parent Variant '" + parent.toString() + "'!");
            }
            if (waxed) {
                return CreateCopperStairBlock::new;
            } else {
                return p -> new CreateWeatheringCopperStairBlock(WeatheringCopper.WeatherState.UNAFFECTED, p);
            }
        }

        @Override
        public void generateBlockState(DataGenContext<Block, StairBlock> ctx, RegistrateBlockstateProvider prov,
                                       ColoredCopperBlockSet blocks, DyeColor state, boolean waxed) {
            String baseLoc = ModelProvider.BLOCK_FOLDER + "/" + blocks.generalDirectory + getDyeColorPrefix(state);
            ResourceLocation texture = prov.modLoc(baseLoc + blocks.getName());
            ResourceLocation endTexture = prov.modLoc(baseLoc + blocks.getEndTextureName());
            prov.stairsBlock(ctx.get(), texture, endTexture, endTexture);
        }

        @Override
        public void generateRecipes(BlockEntry<?> blockVariant, DataGenContext<Block, StairBlock> ctx,
                                    RegistrateRecipeProvider prov) {
            prov.stairs(DataIngredient.items(blockVariant.get()), RecipeCategory.BUILDING_BLOCKS, ctx, null, true);
        }
    }
}
