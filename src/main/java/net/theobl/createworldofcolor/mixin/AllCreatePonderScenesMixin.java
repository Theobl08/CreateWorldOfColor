package net.theobl.createworldofcolor.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderScenes;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.simibubi.create.infrastructure.ponder.scenes.BeltScenes;
import com.simibubi.create.infrastructure.ponder.scenes.KineticsScenes;
import com.simibubi.create.infrastructure.ponder.scenes.MovementActorScenes;
import com.simibubi.create.infrastructure.ponder.scenes.SteamScenes;
import com.simibubi.create.infrastructure.ponder.scenes.fluid.*;
import com.simibubi.create.infrastructure.ponder.scenes.highLogistics.TableClothScenes;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.theobl.createworldofcolor.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AllCreatePonderScenes.class)
public abstract class AllCreatePonderScenesMixin {
    @Definition(id = "withKeyFunction", method = "Lnet/createmod/ponder/api/registration/PonderSceneRegistrationHelper;withKeyFunction(Ljava/util/function/Function;)Lnet/createmod/ponder/api/registration/PonderSceneRegistrationHelper;")
    @Expression("?.withKeyFunction(?)")
    @ModifyExpressionValue(method = "register", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private static PonderSceneRegistrationHelper<ItemProviderEntry<?, ?>> createWorldOfColor$addPonderScenes(PonderSceneRegistrationHelper<ItemProviderEntry<?, ?>> original) {
        // Link our blocks to their vanilla Create counterpart ponder scenes
        original.forComponents(ModBlocks.DYED_ANDESITE_ENCASED_SHAFT.toArray())
                .addStoryBoard("shaft/encasing", KineticsScenes::shaftsCanBeEncased);

        original.forComponents(ModBlocks.DYED_ANDESITE_CASING.toArray())
                .addStoryBoard("shaft/encasing", KineticsScenes::shaftsCanBeEncased)
                .addStoryBoard("belt/encasing", BeltScenes::beltsCanBeEncased);

        original.forComponents(ModBlocks.FLUID_PIPES.toArray())
                .addStoryBoard("fluid_pipe/flow", PipeScenes::flow, AllCreatePonderTags.FLUIDS)
                .addStoryBoard("fluid_pipe/interaction", PipeScenes::interaction)
                .addStoryBoard("fluid_pipe/encasing", PipeScenes::encasing);

        original.forComponents(ModBlocks.MECHANICAL_PUMPS.toArray())
                .addStoryBoard("mechanical_pump/flow", PumpScenes::flow, AllCreatePonderTags.FLUIDS, AllCreatePonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("mechanical_pump/speed", PumpScenes::speed);

        original.forComponents(ModBlocks.SMART_FLUID_PIPES.toArray())
                .addStoryBoard("smart_pipe", PipeScenes::smart, AllCreatePonderTags.FLUIDS);

        original.forComponents(ModBlocks.FLUID_TANKS.toArray())
                .addStoryBoard("fluid_tank/storage", FluidTankScenes::storage, AllCreatePonderTags.FLUIDS)
                .addStoryBoard("fluid_tank/sizes", FluidTankScenes::sizes);

        original.forComponents(ModBlocks.HOSE_PULLEYS.toArray())
                .addStoryBoard("hose_pulley/intro", HosePulleyScenes::intro, AllCreatePonderTags.FLUIDS, AllCreatePonderTags.KINETIC_APPLIANCES)
                .addStoryBoard("hose_pulley/level", HosePulleyScenes::level)
                .addStoryBoard("hose_pulley/infinite", HosePulleyScenes::infinite);

        original.forComponents(ModBlocks.SPOUTS.toArray())
                .addStoryBoard("spout", SpoutScenes::filling, AllCreatePonderTags.FLUIDS);

        original.forComponents(ModBlocks.ITEM_DRAINS.toArray())
                .addStoryBoard("item_drain", DrainScenes::emptying, AllCreatePonderTags.FLUIDS);

        original.forComponents(ModBlocks.PORTABLE_FLUID_INTERFACES.toArray())
                .addStoryBoard("portable_interface/transfer_fluid", FluidMovementActorScenes::transfer, AllCreatePonderTags.FLUIDS, AllCreatePonderTags.CONTRAPTION_ACTOR)
                .addStoryBoard("portable_interface/redstone_fluid", MovementActorScenes::psiRedstone);

        original.forComponents(ModBlocks.TABLE_CLOTHS.toArray())
                .addStoryBoard("high_logistics/table_cloth", TableClothScenes::tableCloth);

        original.forComponents(ModBlocks.STEAM_WHISTLES.toArray())
                .addStoryBoard("steam_whistle", SteamScenes::whistle);

        original.forComponents(ModBlocks.STEAM_ENGINES.toArray())
                .addStoryBoard("steam_engine", SteamScenes::engine);

        return original;
    }
}
