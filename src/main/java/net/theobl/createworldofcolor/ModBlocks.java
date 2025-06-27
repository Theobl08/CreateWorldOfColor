package net.theobl.createworldofcolor;

import com.simibubi.create.*;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceBlock;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.simibubi.create.content.contraptions.actors.seat.SeatInteractionBehaviour;
import com.simibubi.create.content.contraptions.actors.seat.SeatMovementBehaviour;
import com.simibubi.create.content.decoration.MetalLadderBlock;
import com.simibubi.create.content.decoration.MetalScaffoldingBlock;
import com.simibubi.create.content.decoration.RoofBlockCTBehaviour;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorBlock;
import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;
import com.simibubi.create.content.fluids.drain.ItemDrainBlock;
import com.simibubi.create.content.fluids.hosePulley.HosePulleyBlock;
import com.simibubi.create.content.fluids.pipes.*;
import com.simibubi.create.content.fluids.pipes.valve.FluidValveBlock;
import com.simibubi.create.content.fluids.pump.PumpBlock;
import com.simibubi.create.content.fluids.spout.SpoutBlock;
import com.simibubi.create.content.fluids.tank.*;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.block.CopperBlockSet;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.createmod.catnip.platform.CatnipServices;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.theobl.createworldofcolor.block.ColoredCopperBlockSet;
import net.theobl.createworldofcolor.config.ModCStress;
import net.theobl.createworldofcolor.contraptions.actors.psi.ColoredPortableStorageInterfaceMovement;
import net.theobl.createworldofcolor.data.MetalBars;
import net.theobl.createworldofcolor.data.ModBlockStateGen;
import net.theobl.createworldofcolor.data.ModBuilderTransformers;
import net.theobl.createworldofcolor.decoration.steamWhistle.ColoredWhistleBlock;
import net.theobl.createworldofcolor.decoration.steamWhistle.ModWhistleGenerator;
import net.theobl.createworldofcolor.fluids.ColoredPipeAttachmentModel;
import net.theobl.createworldofcolor.fluids.pipes.ColoredEncasedPipeBlock;
import net.theobl.createworldofcolor.fluids.pipes.ColoredFluidPipeBlock;
import net.theobl.createworldofcolor.fluids.pipes.ColoredGlassFluidPipeBlock;
import net.theobl.createworldofcolor.fluids.pipes.ModSmartFluidPipeGenerator;
import net.theobl.createworldofcolor.fluids.tank.ColoredFluidTankBlock;
import net.theobl.createworldofcolor.fluids.tank.ColoredFluidTankItem;
import net.theobl.createworldofcolor.fluids.tank.ColoredFluidTankModel;
import net.theobl.createworldofcolor.fluids.tank.ModFluidTankGenerator;
import net.theobl.createworldofcolor.kinetics.steamEngine.ColoredSteamEngineBlock;

import java.util.function.Supplier;

import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour.interactionBehaviour;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType.mountedFluidStorage;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.*;
import static net.theobl.createworldofcolor.CreateWorldOfColor.REGISTRATE;

public class ModBlocks {

    static {
        REGISTRATE.defaultCreativeTab("createworldofcolor");
    }

//    public static final DyedBlockList<CasingBlock> CASINGS = new DyedBlockList<>(color -> {
//        String colorName = color.getName();
//        return REGISTRATE.block(colorName + "_casing", CasingBlock::new)
//                .properties(p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
//                        .sound(SoundType.COPPER))
//                .transform(BuilderTransformers.casing(() -> ModSpriteShifts.DYED_CASINGS.get(color)))
//                .register();
//    });

    public static final DyedBlockList<FluidPipeBlock> FLUID_PIPES = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REGISTRATE.block(colorName + "_fluid_pipe", p -> new ColoredFluidPipeBlock(p, color))
                .initialProperties(SharedProperties::copperMetal)
                .properties(p -> p.forceSolidOff().mapColor(color))
                .transform(pickaxeOnly())
                .blockstate(ModBlockStateGen.pipe(color))
                .onRegister(blockModel(() -> ColoredPipeAttachmentModel::withAO, color))
                .item()
                .model((c, p) -> p.withExistingParent(c.getName(),
                        CreateWorldOfColor.asResource("block/" + c.getName() + "/item")))
                .build()
                .register();
    });

    public static final DyedBlockList<EncasedPipeBlock> ENCASED_FLUID_PIPES = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REGISTRATE.block("encased_" + colorName + "_fluid_pipe", p -> new ColoredEncasedPipeBlock(p, AllBlocks.COPPER_CASING::get, color))
                .initialProperties(SharedProperties::copperMetal)
                .properties(p -> p.noOcclusion()
                        .mapColor(MapColor.TERRACOTTA_LIGHT_GRAY))
                .transform(axeOrPickaxe())
                .blockstate(ModBlockStateGen.encasedPipe(color))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(ModSpriteShifts.DYED_CASINGS.get(color))))
                .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, AllSpriteShifts.COPPER_CASING,
//                .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, ModSpriteShifts.DYED_CASINGS.get(color),
                        (s, f) -> !s.getValue(EncasedPipeBlock.FACING_TO_PROPERTY_MAP.get(f)))))
                .onRegister(blockModel(() -> ColoredPipeAttachmentModel::withAO, color))
                .loot((p, b) -> p.dropOther(b, FLUID_PIPES.get(color)))
                .transform(EncasingRegistry.addVariantTo(FLUID_PIPES.get(color)))
                .register();
    });

    public static final DyedBlockList<GlassFluidPipeBlock> GLASS_FLUID_PIPES = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REGISTRATE.block("glass_" + colorName + "_fluid_pipe", p -> new ColoredGlassFluidPipeBlock(p, color))
                .initialProperties(SharedProperties::copperMetal)
                .properties(p -> p.noOcclusion().mapColor(color))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(pickaxeOnly())
                .blockstate((c, p) -> {
                    p.getVariantBuilder(c.getEntry())
                            .forAllStatesExcept(state -> {
                                Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);
                                return ConfiguredModel.builder()
                                        .modelFile(p.models()
                                                .getExistingFile(p.modLoc("block/" + colorName + "_fluid_pipe/window")))
                                        .uvLock(false)
                                        .rotationX(axis == Direction.Axis.Y ? 0 : 90)
                                        .rotationY(axis == Direction.Axis.X ? 90 : 0)
                                        .build();
                            }, BlockStateProperties.WATERLOGGED);
                })
                .onRegister(blockModel(() -> ColoredPipeAttachmentModel::withAO, color))
                .loot((p, b) -> p.dropOther(b, FLUID_PIPES.get(color).get()))
                .register();
    });

    public static final DyedBlockList<PumpBlock> MECHANICAL_PUMPS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REGISTRATE.block(colorName + "_mechanical_pump", PumpBlock::new)
                .initialProperties(SharedProperties::copperMetal)
                .properties(p -> p.mapColor(MapColor.STONE))
                .transform(pickaxeOnly())
                .blockstate(ModBlockStateGen.pump(color))
                .onRegister(blockModel(() -> ColoredPipeAttachmentModel::withAO, color))
                .transform(ModCStress.setImpact(4.0))
                .item()
                .transform(customItemModel())
                .register();
    });

    public static final DyedBlockList<SmartFluidPipeBlock> SMART_FLUID_PIPES = new DyedBlockList<>( color -> {
        String colorName = color.getSerializedName();
        return REGISTRATE.block(colorName + "_smart_fluid_pipe", SmartFluidPipeBlock::new)
                .initialProperties(SharedProperties::copperMetal)
                .properties(p -> p.mapColor(MapColor.TERRACOTTA_YELLOW))
                .transform(pickaxeOnly())
                .blockstate(new ModSmartFluidPipeGenerator(colorName + "_")::generate)
                .onRegister(blockModel(() -> ColoredPipeAttachmentModel::withAO, color))
                .item()
                .transform(customItemModel())
                .register();
    });

    public static final DyedBlockList<FluidValveBlock> FLUID_VALVE = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REGISTRATE.block(colorName + "_fluid_valve", FluidValveBlock::new)
                .initialProperties(SharedProperties::copperMetal)
                .transform(pickaxeOnly())
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(ModBlockStateGen.fluidValve(color))
                .onRegister(blockModel(() -> ColoredPipeAttachmentModel::withAO, color))
                .item()
                .transform(customItemModel())
                .register();
    });

    public static final DyedBlockList<FluidTankBlock> FLUID_TANKS = new DyedBlockList<>(color -> {
        String colorName = color.getName();
        return REGISTRATE.block(colorName + "_fluid_tank", p ->  new ColoredFluidTankBlock(p, color))
                .initialProperties(SharedProperties::copperMetal)
                .properties(p -> p.noOcclusion()
                        .isRedstoneConductor((p1, p2, p3) -> true))
                .transform(pickaxeOnly())
                .blockstate(new ModFluidTankGenerator(colorName + "_")::generate)
                .tag(ModTags.Blocks.FLUID_TANKS.tag)
                .onRegister(blockModel(() -> ColoredFluidTankModel::standard, color))
                .transform(displaySource(AllDisplaySources.BOILER))
                .transform(mountedFluidStorage(AllMountedStorageTypes.FLUID_TANK))
                .onRegister(movementBehaviour(new FluidTankMovementBehavior()))
                .addLayer(() -> RenderType::cutoutMipped)
                .item(ColoredFluidTankItem::new)
                .model(AssetLookup.customBlockItemModel("_", "block_single_window"))
                .build()
                .register();
    });

    public static final DyedBlockList<HosePulleyBlock> HOSE_PULLEYS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REGISTRATE.block(colorName + "_hose_pulley", HosePulleyBlock::new)
                .initialProperties(SharedProperties::copperMetal)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(pickaxeOnly())
                .blockstate(ModBlockStateGen.hosePulley(color, true))
                .transform(ModCStress.setImpact(4.0))
                .item()
                .transform(customItemModel())
                .register();
    });

    public static final DyedBlockList<ItemDrainBlock> ITEM_DRAINS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return  REGISTRATE.block(colorName + "_item_drain", ItemDrainBlock::new)
                .initialProperties(SharedProperties::copperMetal)
                .transform(pickaxeOnly())
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate((c, p) -> {
                    p.models().withExistingParent(c.getName(), Create.asResource("block/item_drain"))
                            .texture("0", "block/" + c.getName() + "_side")
                            .texture("3", "block/" + colorName + "_pump")
                            .texture("4", "block/" + colorName + "_copper_underside");
                    p.simpleBlock(c.get(), AssetLookup.standardModel(c, p));
                })
                .simpleItem()
                .register();
    });

    public static final DyedBlockList<SpoutBlock> SPOUTS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REGISTRATE.block(colorName + "_spout", SpoutBlock::new)
                .initialProperties(SharedProperties::copperMetal)
                .transform(pickaxeOnly())
                .blockstate((ctx, prov) -> {
                    String path = "block/" + ctx.getName();
                    String parentPath = "block/spout";

                    String encasedPipe = "block/encased_" + colorName +"_pipe";
                    String copperUnderside = "block/" + colorName +"_copper_underside";
                    String spout = "block/" + colorName + "_spout";
                    String spoutNozzle = "block/" + colorName + "_spout_nozzle";

                    prov.models().withExistingParent(path + "/block", Create.asResource(parentPath + "/block"))
                            .texture("0", spout)
                            .texture("3", encasedPipe)
                            .texture("particle", copperUnderside);
                    prov.models().withExistingParent(path + "/bottom", Create.asResource(parentPath + "/bottom"))
                            .texture("2", spoutNozzle);
                    prov.models().withExistingParent(path + "/item", Create.asResource(parentPath + "/item"))
                            .texture("0", spout)
                            .texture("3", spoutNozzle)
                            .texture("4", encasedPipe)
                            .texture("particle", copperUnderside);
                    prov.models().withExistingParent(path + "/middle", Create.asResource(parentPath + "/middle"))
                            .texture("0", spoutNozzle)
                            .texture("particle", spoutNozzle);
                    prov.models().withExistingParent(path + "/top", Create.asResource(parentPath + "/top"))
                            .texture("0", spoutNozzle)
                            .texture("particle", spoutNozzle);

                    prov.simpleBlock(ctx.getEntry(), AssetLookup.partialBaseModel(ctx, prov));
                })
                .addLayer(() -> RenderType::cutoutMipped)
                .item(AssemblyOperatorBlockItem::new)
                .transform(customItemModel())
                .register();
    });

    public static final DyedBlockList<PortableStorageInterfaceBlock> PORTABLE_FLUID_INTERFACES = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REGISTRATE.block(colorName + "_portable_fluid_interface", PortableStorageInterfaceBlock::forFluids)
                .initialProperties(SharedProperties::copperMetal)
                .properties(p -> p.mapColor(color))
                .transform(axeOrPickaxe())
                .blockstate((c, p) -> {
                    String path = "block/" + c.getName();
                    String parentPath = "block/portable_fluid_interface";

                    String copperUnderside = "block/" + colorName + "_copper_underside";
                    String portableFluidInterface = "block/" + colorName + "_portable_fluid_interface";

                    p.models().withExistingParent(path + "/block", Create.asResource(parentPath + "/block"))
                            .texture("0", portableFluidInterface)
                            .texture("2", copperUnderside)
                            .texture("particle", copperUnderside);
                    p.models().withExistingParent(path + "/block_middle", Create.asResource(parentPath + "/block_middle"))
                            .texture("particle", copperUnderside);
                    p.models().withExistingParent(path + "/block_middle_powered", Create.asResource(parentPath + "/block_middle_powered"))
                            .texture("particle", copperUnderside);
                    p.models().withExistingParent(path + "/block_top", Create.asResource(parentPath + "/block_top"))
                            .texture("particle", copperUnderside);
                    p.models().withExistingParent(path + "/item", Create.asResource(parentPath + "/item"))
                            .texture("0", portableFluidInterface)
                            .texture("2", copperUnderside)
                            .texture("particle", copperUnderside);

                    p.directionalBlock(c.get(), AssetLookup.partialBaseModel(c, p));
                })
                .onRegister(movementBehaviour(new ColoredPortableStorageInterfaceMovement()))
                .item()
                .tag(AllTags.AllItemTags.CONTRAPTION_CONTROLLED.tag)
                .transform(customItemModel())
                .register();
    });

    public static final DyedBlockList<SteamEngineBlock> STEAM_ENGINES = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REGISTRATE.block(colorName + "_steam_engine", p -> new ColoredSteamEngineBlock(p, color))
                .initialProperties(SharedProperties::copperMetal)
                .transform(pickaxeOnly())
                .blockstate((c, p) -> {
                    String path = "block/" + c.getName();
                    String parentPath = "block/steam_engine";

                    String copperUnderside = "block/" + colorName + "_copper_underside";
                    String engine = "block/" + colorName + "_engine";

                    p.models().withExistingParent(path + "/block", Create.asResource(parentPath + "/block"))
                            .texture("1", engine)
                            .texture("particle", copperUnderside);
                    p.models().withExistingParent(path + "/item", Create.asResource(parentPath + "/item"))
                            .texture("1", engine)
                            .texture("particle", copperUnderside);
                    p.horizontalFaceBlock(c.get(), AssetLookup.partialBaseModel(c, p));
                })
                .transform(ModCStress.setCapacity(1024.0))
                .onRegister(BlockStressValues.setGeneratorSpeed(64, true))
                .item()
                .transform(customItemModel())
                .register();
    });

    public static final DyedBlockList<WhistleBlock> STEAM_WHISTLES = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REGISTRATE.block(colorName + "_steam_whistle", p -> new ColoredWhistleBlock(p, color))
                .initialProperties(SharedProperties::copperMetal)
                .properties(p -> p.mapColor(MapColor.GOLD))
                .transform(pickaxeOnly())
                .blockstate(new ModWhistleGenerator(colorName + "_")::generate)
                .item()
                .transform(customItemModel())
                .register();
    });

    public static final DyedBlockList<MetalLadderBlock> LADDERS = new DyedBlockList<>(color -> {
        String colorName = color.getName();
        return REGISTRATE.block(colorName + "_ladder", MetalLadderBlock::new)
                .transform(ModBuilderTransformers.ladder(colorName, Ingredient.of(DyeItem.byColor(color)), color.getMapColor()))
                .register();
    });

    public static final DyedBlockList<IronBarsBlock> DYED_BARS = new DyedBlockList<>(color -> {
        String colorName = color.getName();
        return MetalBars.createBars(colorName, true, () -> DataIngredient.items(DyeItem.byColor(color)), color.getMapColor());
    });

    public static final DyedBlockList<MetalScaffoldingBlock> DYED_SCAFFOLDS = new DyedBlockList<>(color -> {
        String colorName = color.getName();
        return REGISTRATE.block(colorName + "_scaffolding", MetalScaffoldingBlock::new)
                .transform(ModBuilderTransformers.scaffold(colorName,
                        Ingredient.of(DyeItem.byColor(color)), color.getMapColor(),
                        ModSpriteShifts.DYED_SCAFFOLDS.get(color), ModSpriteShifts.DYED_SCAFFOLDS_INSIDE.get(color), ModSpriteShifts.DYED_CASINGS.get(color)))
                .register();
    });

    public static final DyedBlockList<SeatBlock> SEATS = new DyedBlockList<>(color -> {
        String colourName = color.getSerializedName();
        SeatMovementBehaviour movementBehaviour = new SeatMovementBehaviour();
        SeatInteractionBehaviour interactionBehaviour = new SeatInteractionBehaviour();
        return REGISTRATE.block("vanilla_" + colourName + "_seat", p -> new SeatBlock(p, color))
                .initialProperties(SharedProperties::wooden)
                .properties(p -> p.mapColor(color))
                .transform(axeOnly())
                .onRegister(movementBehaviour(movementBehaviour))
                .onRegister(interactionBehaviour(interactionBehaviour))
                .transform(displaySource(AllDisplaySources.ENTITY_NAME))
                .blockstate((c, p) -> {
                    p.simpleBlock(c.get(), p.models()
                            .withExistingParent("vanilla_" + colourName + "_seat", Create.asResource("block/seat"))
                            .texture("1", p.modLoc("block/seat/top_vanilla_" + colourName))
                            .texture("2", p.modLoc("block/seat/side_vanilla_" + colourName)));
                })
                .recipe((c, p) -> {
                    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get())
                            .requires(AllBlocks.SEATS.get(color))
                            .unlockedBy("has_seat", RegistrateRecipeProvider.has(AllTags.AllItemTags.SEATS.tag))
                            .save(p, CreateWorldOfColor.asResource("crafting/kinetics/" + c.getName()));
                    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, AllBlocks.SEATS.get(color))
                            .requires(c.get())
                            .unlockedBy("has_seat", RegistrateRecipeProvider.has(AllTags.AllItemTags.SEATS.tag))
                            .save(p, CreateWorldOfColor.asResource("crafting/kinetics/" + c.getName() + "_from_vanilla_seat"));
                })
                .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.create.seat"))
                .tag(AllTags.AllBlockTags.SEATS.tag)
                .item()
                .tag(AllTags.AllItemTags.SEATS.tag)
                .build()
                .register();
    });

    public static final DyedBlockList<SlidingDoorBlock> DYED_DOORS = new DyedBlockList<>(color -> {
        String colorName = color.getName();
        return REGISTRATE.block(colorName + "_door", p -> SlidingDoorBlock.metal(p, true))
                .transform(ModBuilderTransformers.slidingDoor(colorName, Ingredient.of(DyeItem.byColor(color))))
                .properties(p -> p.mapColor(color)
                        .sound(SoundType.STONE)
                        .noOcclusion())
                .register();
    });

    public static final ColoredCopperBlockSet SHINGLES = new ColoredCopperBlockSet(REGISTRATE, "copper_shingles",
            "copper_roof_top", ColoredCopperBlockSet.DEFAULT_VARIANTS, (c, p) -> {
        ItemLike shingle = AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.UNAFFECTED, false);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                .requires(shingle)
                .requires(Items.WHITE_DYE)
                .unlockedBy(RegistrateRecipeProvider.getHasName(shingle), RegistrateRecipeProvider.has(shingle))
                .save(p);
    }, (color, block) -> connectedTextures(() -> new RoofBlockCTBehaviour(ModSpriteShifts.COLORED_COPPER_SHINGLES.get(color)))
            .accept(block));

    public static final ColoredCopperBlockSet TILES = new ColoredCopperBlockSet(REGISTRATE, "copper_tiles",
            "copper_roof_top", ColoredCopperBlockSet.DEFAULT_VARIANTS, (c, p) -> {
        ItemLike shingle = AllBlocks.COPPER_TILES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.UNAFFECTED, false);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                .requires(shingle)
                .requires(Items.WHITE_DYE)
                .unlockedBy(RegistrateRecipeProvider.getHasName(shingle), RegistrateRecipeProvider.has(shingle))
                .save(p);
    }, (color, block) -> connectedTextures(() -> new RoofBlockCTBehaviour(ModSpriteShifts.COLORED_COPPER_TILES.get(color)))
            .accept(block));

    public static void register() {
    }

    public static <T extends Block> NonNullConsumer<? super T> blockModel(
            Supplier<NonNullBiFunction<BakedModel, DyeColor, ? extends BakedModel>> func, DyeColor color) {
        return entry -> CatnipServices.PLATFORM.executeOnClientOnly(() -> () -> registerBlockModel(entry, func, color));
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerBlockModel(Block entry,
                                           Supplier<NonNullBiFunction<BakedModel, DyeColor, ? extends BakedModel>> func, DyeColor color) {
        CreateClient.MODEL_SWAPPER.getCustomBlockModels()
                .register(RegisteredObjectsHelper.getKeyOrThrow(entry), model -> func.get().apply(model, color));
    }
}
