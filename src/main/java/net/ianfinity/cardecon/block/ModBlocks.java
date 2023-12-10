package net.ianfinity.cardecon.block;

import net.fabricmc.fabric.api.block.v1.FabricBlock;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.ianfinity.cardecon.CardEcon;
import net.ianfinity.cardecon.block.blocks.CardReader;
import net.ianfinity.cardecon.block.entity.CardReaderEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block CARD_READER = registerBlock("card_reader", new CardReader(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.STONE)) {
    });
    public static final BlockEntityType<CardReaderEntity> CARD_READER_ENTITY = registerBlockEntity("card_reader", FabricBlockEntityTypeBuilder.create(CardReaderEntity::new, CARD_READER).build());
    public static final Item CARD_READER_ITEM = registerBlockItem("card_reader", new BlockItem(CARD_READER, new FabricItemSettings()));

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, BlockEntityType<T> be) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CardEcon.MOD_ID, name), be);
    }

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(CardEcon.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, BlockItem blockItem) {
        return Registry.register(Registries.ITEM, new Identifier(CardEcon.MOD_ID, name),
                blockItem);
    }

    public static void registerModBlocks() {
        CardEcon.LOGGER.info("Registering Blocks For " + CardEcon.MOD_ID);
    }

    public static void clientBlockHandling() {
        BlockRenderLayerMap.INSTANCE.putBlock(CARD_READER, RenderLayer.getTranslucent());
    }
}
