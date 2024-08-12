package net.multihopper.initializers;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.multihopper.blocks.MultihopperBlock;
import net.multihopper.gui.MultihopperScreenHandler;

public final class ScreenHandlers {
    public static ScreenHandlerType<MultihopperScreenHandler> MULTIHOPPER_SCREEN_HANDLER;

    public static void initialize() {
        MULTIHOPPER_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, MultihopperBlock.ID,
                                                       new ScreenHandlerType<>((syncId, inventory) -> new MultihopperScreenHandler(syncId, inventory, ScreenHandlerContext.EMPTY), FeatureFlags.VANILLA_FEATURES));
    }
}
