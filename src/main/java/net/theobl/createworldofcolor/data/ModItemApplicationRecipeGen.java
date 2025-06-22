package net.theobl.createworldofcolor.data;

import com.simibubi.create.api.data.recipe.ItemApplicationRecipeGen;
import com.simibubi.create.foundation.utility.DyeHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.theobl.createworldofcolor.CreateWorldOfColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemApplicationRecipeGen extends ItemApplicationRecipeGen {
    List<GeneratedRecipe> WOOLS = generateList();

    private List<GeneratedRecipe> generateList() {
        List<GeneratedRecipe> recipes = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String id = color.getSerializedName();
            recipes.add(create(id + "_wool", b -> b.require(ItemTags.WOOL)
                    .require(DyeItem.byColor(color))
                    .output(DyeHelper.getWoolOfDye(color))));
        }
        return recipes;
    }

    public ModItemApplicationRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateWorldOfColor.MODID);
    }
}
