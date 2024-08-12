package net.multihopper.initializers;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.multihopper.blocks.MultihopperBlock;

public final class Items {
    public static Item MULTIHOPPER_ITEM;

    public static void initialize() {
        MULTIHOPPER_ITEM = Registry.register(Registries.ITEM, MultihopperBlock.ID, new BlockItem(Blocks.MULTIHOPPER_BLOCK, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> content.add(MULTIHOPPER_ITEM));
    }
}
