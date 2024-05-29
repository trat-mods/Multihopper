package net.multihopper.loaders;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.multihopper.gui.MultihopperScreen;
import net.multihopper.gui.MultihopperScreenHandler;
import net.multihopper.initializers.ScreenHandlers;

public class MultihopperClientLoader implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.<MultihopperScreenHandler, MultihopperScreen>register(ScreenHandlers.MULTIHOPPER_SCREEN_HANDLER, (gui, inventory, title) -> new MultihopperScreen(gui, inventory.player, title));
    }
}
