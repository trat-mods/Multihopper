package net.multihopper.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.multihopper.entities.MultihopperBlockEntity;
import net.multihopper.initializers.ScreenHandlers;

import java.util.ArrayList;
import java.util.List;

public class MultihopperScreenHandler extends SyncedGuiDescription {
    public MultihopperScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ScreenHandlers.MULTIHOPPER_SCREEN_HANDLER, syncId, playerInventory, getBlockInventory(context, MultihopperBlockEntity.INVENTORY_SIZE), getBlockPropertyDelegate(context));
        WPlainPanel root = new WPlainPanel();
        root.setSize(178, 134);

        setRootPanel(root);
        List<WItemSlot> slots = new ArrayList<>();
        for (int i = 0; i < blockInventory.size(); i++) {
            slots.add(WItemSlot.of(blockInventory, i));
        }
        for (int i = 0; i < slots.size(); i++) {
            root.add(slots.get(i), 34 + (i * 30), 20);
        }
        root.add(this.createPlayerInventoryPanel(), 8, 40);
        root.validate(this);
    }
}
