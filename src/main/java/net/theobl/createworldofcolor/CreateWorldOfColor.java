package net.theobl.createworldofcolor;

import com.mojang.logging.LogUtils;
import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.theobl.createworldofcolor.config.Config;
import net.theobl.createworldofcolor.data.ModDataMapProvider;
import net.theobl.createworldofcolor.data.ModRecipeProvider;
import net.theobl.createworldofcolor.data.ModStandardRecipeGen;
import net.theobl.createworldofcolor.data.ModTagsProvider;
import net.theobl.createworldofcolor.fluids.spout.ColoredSpoutBlockEntity;
import net.theobl.createworldofcolor.fluids.tank.ColoredFluidTankBlockEntity;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CreateWorldOfColor.MODID)
public class CreateWorldOfColor {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "createworldofcolor";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public CreateWorldOfColor(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        REGISTRATE.registerEventListeners(modEventBus);

        ModCreativeModeTabs.register(modEventBus);
        ModBlocks.register();
        ModBlockEntityTypes.register();

        Config.register(modLoadingContext, modContainer);

        modEventBus.addListener(this::onBlockEntityValidBlocks);
        modEventBus.addListener(EventPriority.HIGHEST, this::gatherRegistrateData);
        modEventBus.addListener(EventPriority.LOWEST, this::gatherData);
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (CreateWorldOfColor) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
    }

    public void onBlockEntityValidBlocks(BlockEntityTypeAddBlocksEvent event) {
        ModBlocks.DYED_DOORS.forEach(block -> event.modify(AllBlockEntityTypes.SLIDING_DOOR.get(), block.get()));
        //ModBlocks.FLUID_TANKS.forEach(block -> event.modify(AllBlockEntityTypes.FLUID_TANK.get(), block.get()));
        //ModBlocks.FLUID_PIPES.forEach(block -> event.modify(AllBlockEntityTypes.FLUID_PIPE.get(), block.get()));
        ModBlocks.GLASS_FLUID_PIPES.forEach(block -> event.modify(AllBlockEntityTypes.GLASS_FLUID_PIPE.get(), block.get()));
        ModBlocks.ENCASED_FLUID_PIPES.forEach(block -> event.modify(AllBlockEntityTypes.ENCASED_FLUID_PIPE.get(), block.get()));
        ModBlocks.MECHANICAL_PUMPS.forEach(block -> event.modify(AllBlockEntityTypes.MECHANICAL_PUMP.get(), block.get()));
        ModBlocks.SMART_FLUID_PIPES.forEach(block -> event.modify(AllBlockEntityTypes.SMART_FLUID_PIPE.get(), block.get()));
        ModBlocks.FLUID_VALVE.forEach(block -> event.modify(AllBlockEntityTypes.FLUID_VALVE.get(), block.get()));
        ModBlocks.ITEM_DRAINS.forEach(block -> event.modify(AllBlockEntityTypes.ITEM_DRAIN.get(), block.get()));
//        ModBlocks.SPOUTS.forEach(block -> event.modify(AllBlockEntityTypes.SPOUT.get(), block.get()));
        ModBlocks.PORTABLE_FLUID_INTERFACES.forEach(block -> event.modify(AllBlockEntityTypes.PORTABLE_FLUID_INTERFACE.get(), block.get()));
        ModBlocks.HOSE_PULLEYS.forEach(block -> event.modify(AllBlockEntityTypes.HOSE_PULLEY.get(), block.get()));
        ModBlocks.STEAM_ENGINES.forEach(block -> event.modify(AllBlockEntityTypes.STEAM_ENGINE.get(), block.get()));
        ModBlocks.STEAM_WHISTLES.forEach(block -> event.modify(AllBlockEntityTypes.STEAM_WHISTLE.get(), block.get()));
    }

    public void gatherRegistrateData(GatherDataEvent event) {
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, ModTagsProvider::genItemTags);
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, ModTagsProvider::genBlockTags);
    }

    public void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new ModStandardRecipeGen(output, lookupProvider));
        generator.addProvider(event.includeServer(), new ModDataMapProvider(output, lookupProvider));

        if (event.includeServer()) {
            ModRecipeProvider.registerAllProcessing(generator, output, lookupProvider);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModPartialModels.init();
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class CommonModEvents {
        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event) {
            ColoredFluidTankBlockEntity.registerCapabilities(event);
            ColoredSpoutBlockEntity.registerCapabilities(event);
        }
    }
}
