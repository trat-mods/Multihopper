package net.multihopper.initializers;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.multihopper.blocks.MultihopperBlock;
import net.multihopper.entities.MultihopperBlockEntity;
import net.multihopper.loaders.MultihopperLoader;

public final class Entities {
    public static BlockEntityType<MultihopperBlockEntity> MULTIHOPPER_BLOCK_ENTITY;

    public static void initialize() {
        MULTIHOPPER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, MultihopperLoader.MOD_ID + ":" + MultihopperBlock.ID.getPath(),
                                                     FabricBlockEntityTypeBuilder.create(MultihopperBlockEntity::new, Blocks.MULTIHOPPER_BLOCK).build(null));
    }
}
