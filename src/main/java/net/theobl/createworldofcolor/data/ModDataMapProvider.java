package net.theobl.createworldofcolor.data;

import com.simibubi.create.foundation.block.CopperRegistries;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Oxidizable;
import net.neoforged.neoforge.registries.datamaps.builtin.Waxable;
import net.theobl.createworldofcolor.ModBlocks;
import net.theobl.createworldofcolor.block.ColoredCopperBlockSet;
import net.theobl.createworldofcolor.block.ColoredCopperRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModDataMapProvider extends DataMapProvider {
    public static final List<DyeColor> COLORS = new ArrayList<>(Arrays.asList(
            DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
            DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
            DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE,
            DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK));

    /**
     * Create a new provider.
     *
     * @param packOutput     the output location
     * @param lookupProvider a {@linkplain CompletableFuture} supplying the registries
     */
    public ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        final var oxidizables = builder(NeoForgeDataMaps.OXIDIZABLES);
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            int colorId = color.getId();
            if(index == COLORS.size() - 1) {
                break;
            }
            oxidizables.add(ModBlocks.SHINGLES.get(ColoredCopperBlockSet.BlockVariant.INSTANCE, color, false),
                    new Oxidizable(ModBlocks.SHINGLES.get(ColoredCopperBlockSet.BlockVariant.INSTANCE, COLORS.get(index + 1), false).get()), false);
            oxidizables.add(ModBlocks.SHINGLES.get(ColoredCopperBlockSet.StairVariant.INSTANCE, color, false),
                    new Oxidizable(ModBlocks.SHINGLES.get(ColoredCopperBlockSet.StairVariant.INSTANCE, COLORS.get(index + 1), false).get()), false);
            oxidizables.add(ModBlocks.SHINGLES.get(ColoredCopperBlockSet.SlabVariant.INSTANCE, color, false),
                    new Oxidizable(ModBlocks.SHINGLES.get(ColoredCopperBlockSet.SlabVariant.INSTANCE, COLORS.get(index + 1), false).get()), false);

            oxidizables.add(ModBlocks.TILES.get(ColoredCopperBlockSet.BlockVariant.INSTANCE, color, false),
                    new Oxidizable(ModBlocks.TILES.get(ColoredCopperBlockSet.BlockVariant.INSTANCE, COLORS.get(index + 1), false).get()), false);
            oxidizables.add(ModBlocks.TILES.get(ColoredCopperBlockSet.StairVariant.INSTANCE, color, false),
                    new Oxidizable(ModBlocks.TILES.get(ColoredCopperBlockSet.StairVariant.INSTANCE, COLORS.get(index + 1), false).get()), false);
            oxidizables.add(ModBlocks.TILES.get(ColoredCopperBlockSet.SlabVariant.INSTANCE, color, false),
                    new Oxidizable(ModBlocks.TILES.get(ColoredCopperBlockSet.SlabVariant.INSTANCE, COLORS.get(index + 1), false).get()), false);
        }

        final Builder<Waxable, Block> waxables = builder(NeoForgeDataMaps.WAXABLES);
        ColoredCopperRegistries.getWaxableView().forEach((now, after) -> add(waxables, now, new Waxable(after.get())));
    }

    public static <T> void add(Builder<T, Block> b, Supplier<Block> now, T after) {
        b.add(now.get().builtInRegistryHolder(), after, false);
    }
}
