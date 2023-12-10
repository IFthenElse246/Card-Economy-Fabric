package net.ianfinity.cardecon.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.ianfinity.cardecon.CardEcon;
import net.ianfinity.cardecon.item.items.Bill;
import net.ianfinity.cardecon.item.items.BillStack;
import net.ianfinity.cardecon.item.items.BillWad;
import net.ianfinity.cardecon.item.items.CreditCard;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item CREDITCARD = registerItem("credit_card", new CreditCard(new FabricItemSettings()));
    public static final Item BILL = registerItem("bill", new Bill(new FabricItemSettings()));
    public static final Item BILLSTACK = registerItem("bill_stack", new BillStack(new FabricItemSettings()));
    public static final Item BILLWAD = registerItem("bill_wad", new BillWad(new FabricItemSettings()));

    private static void addItemsToItemGroup(FabricItemGroupEntries entries) {
        entries.add(CREDITCARD);
        entries.add(BILL);
        entries.add(BILLSTACK);
        entries.add(BILLWAD);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(CardEcon.MOD_ID, name), item);
    }

    public static void registerModItems() {
        CardEcon.LOGGER.info("Registering Items For " + CardEcon.MOD_ID);


    }
}
