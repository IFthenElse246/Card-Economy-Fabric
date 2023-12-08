package net.ianfinity.cardecon.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.ianfinity.cardecon.CardEcon;
import net.ianfinity.cardecon.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup EconomyGroup = Registry.register(Registries.ITEM_GROUP,
            new Identifier(CardEcon.MOD_ID, "economy"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.economy"))
                    .icon(() -> new ItemStack(ModItems.CREDITCARD)).entries((displayContext, entries) -> {
                        entries.add(ModItems.CREDITCARD);
                        entries.add(ModBlocks.CARD_READER);
                    }).build());

    public static void registerItemGroups() {
        CardEcon.LOGGER.info("Registering Item Groups For " + CardEcon.MOD_ID);
    }
}
