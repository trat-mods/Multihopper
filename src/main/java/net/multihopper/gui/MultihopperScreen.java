package net.multihopper.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class MultihopperScreen extends CottonInventoryScreen<MultihopperScreenHandler> {
    public MultihopperScreen(MultihopperScreenHandler container, PlayerEntity player, Text title) {
        super(container, player, title);
    }
}