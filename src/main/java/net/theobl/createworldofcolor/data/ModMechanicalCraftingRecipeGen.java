package net.theobl.createworldofcolor.data;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.simibubi.create.api.data.recipe.MechanicalCraftingRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.theobl.createworldofcolor.CreateWorldOfColor;
import net.theobl.createworldofcolor.ModBlocks;
import net.theobl.createworldofcolor.ModItems;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModMechanicalCraftingRecipeGen extends MechanicalCraftingRecipeGen {
    List<BaseRecipeProvider.GeneratedRecipe> POTATO_CANNON = potatoCannon();

    private List<BaseRecipeProvider.GeneratedRecipe> potatoCannon() {
        List<BaseRecipeProvider.GeneratedRecipe> recipes = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            recipes.add(create(() -> ModItems.POTATO_CANNONS.get(color).get()).returns(1)
                    .recipe(b -> b.key('L', AllItems.ANDESITE_ALLOY.get())
                            .key('R', AllItems.PRECISION_MECHANISM.get())
                            .key('S', ModBlocks.FLUID_PIPES.get(color))
                            .key('C', Ingredient.of(Tags.Items.INGOTS_COPPER))
                            .patternLine("LRSSS")
                            .patternLine("CC   ")));
        }
        return recipes;
    }

    public ModMechanicalCraftingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateWorldOfColor.MODID);
    }
}
