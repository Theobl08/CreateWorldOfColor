package net.theobl.createworldofcolor.data;

import com.google.common.base.Supplier;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.theobl.createworldofcolor.CreateWorldOfColor;
import net.theobl.createworldofcolor.ModTags;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

public class ModStandardRecipeGen extends BaseRecipeProvider {
    final List<GeneratedRecipe> all = new ArrayList<>();

    private Marker PALETTES = enterFolder("palettes");

    GeneratedRecipe COPPER_BARS_FROM_OTHER_BARS = create(AllBlocks.COPPER_BARS).withSuffix("_from_others")
            .unlockedByTag(() -> Tags.Items.INGOTS_COPPER)
            .viaShapeless(b -> b.requires(ModTags.Items.DYEABLE_BARS.tag)),

    COPPER_LADDER_FROM_OTHER_LADDERS = create(AllBlocks.COPPER_LADDER).withSuffix("_from_others")
            .unlockedByTag(() -> Tags.Items.INGOTS_COPPER)
            .viaShapeless(b -> b.requires(ModTags.Items.DYEABLE_LADDERS.tag)),

    COPPER_SCAFFOLD_FROM_OTHER_SCAFFOLDS = create(AllBlocks.COPPER_SCAFFOLD).withSuffix("_from_others")
            .unlockedByTag(() -> Tags.Items.INGOTS_COPPER)
            .viaShapeless(b -> b.requires(ModTags.Items.DYEABLE_SCAFFOLDS.tag)),

    COPPER_DOOR_FROM_OTHER_DOORS = create(AllBlocks.COPPER_DOOR).withSuffix("_from_others")
            .unlockedByTag(() -> Tags.Items.INGOTS_COPPER)
            .viaShapeless(b -> b.requires(ModTags.Items.DYEABLE_DOORS.tag));

    static class Marker {
    }

    String currentFolder = "";

    Marker enterFolder(String folder) {
        currentFolder = folder;
        return new Marker();
    }

    GeneratedRecipeBuilder create(Supplier<ItemLike> result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }

    GeneratedRecipeBuilder create(ItemProviderEntry<? extends ItemLike, ? extends ItemLike> result) {
        return create(result::get);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        all.forEach(c -> c.register(output));
        CreateWorldOfColor.LOGGER.info("{} registered {} recipe{}", getName(), all.size(), all.size() == 1 ? "" : "s");
    }

    protected GeneratedRecipe register(GeneratedRecipe recipe) {
        all.add(recipe);
        return recipe;
    }

    class GeneratedRecipeBuilder {
        private String path;
        private String suffix;
        private Supplier<? extends ItemLike> result;
        private ResourceLocation compatDatagenOutput;
        List<ICondition> recipeConditions;

        private Supplier<ItemPredicate> unlockedBy;
        private int amount;

        private GeneratedRecipeBuilder(String path) {
            this.path = path;
            this.recipeConditions = new ArrayList<>();
            this.suffix = "";
            this.amount = 1;
        }

        public GeneratedRecipeBuilder(String path, Supplier<? extends ItemLike> result) {
            this(path);
            this.result = result;
        }

        GeneratedRecipeBuilder unlockedByTag(Supplier<TagKey<Item>> tag) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(tag.get())
                    .build();
            return this;
        }

        GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        GeneratedRecipe viaShapeless(UnaryOperator<ShapelessRecipeBuilder> builder) {
            return register(recipeOutput -> {
                ShapelessRecipeBuilder b =
                        builder.apply(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.get(), amount));
                if (unlockedBy != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));

                RecipeOutput conditionalOutput = recipeOutput.withConditions(recipeConditions.toArray(new ICondition[0]));

                b.save(recipeOutput, createLocation("crafting"));
            });
        }

        private ResourceLocation createLocation(String recipeType) {
            return CreateWorldOfColor.asResource(recipeType + "/" + path + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation getRegistryName() {
            return compatDatagenOutput == null ? RegisteredObjectsHelper.getKeyOrThrow(result.get()
                    .asItem()) : compatDatagenOutput;
        }
    }

    public ModStandardRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateWorldOfColor.MODID);
    }


}
