package net.ianfinity.cardecon.screens.CreditCard;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class CreditCardInputInventory extends CreditCardInventory {
    public final CompressionInputSlot cSlot;
    public final DecompressionInputSlot dcSlot;

    public CreditCardInputInventory(PlayerEntity player, DefaultedList<ItemStack> stacks) {
        super(player, 2, stacks);

        cSlot = new CompressionInputSlot(this, 0);
        dcSlot = new DecompressionInputSlot(this, 1);
    }

    public CreditCardInputInventory(PlayerEntity player) {
        this(player, DefaultedList.ofSize(2, ItemStack.EMPTY));
    }
}
