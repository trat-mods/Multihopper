package net.multihopper.initializers;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.multihopper.blocks.MultihopperBlock;

public final class Blocks {
    public static final MultihopperBlock MULTIHOPPER_BLOCK = new MultihopperBlock();

    public static void initialize() {
        Registry.register(Registries.BLOCK, MultihopperBlock.ID, MULTIHOPPER_BLOCK);
    }
}
