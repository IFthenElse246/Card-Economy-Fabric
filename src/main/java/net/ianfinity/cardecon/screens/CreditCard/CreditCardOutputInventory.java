package net.ianfinity.cardecon.screens.CreditCard;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class CreditCardOutputInventory extends CreditCardInventory {

    public final CompressionOutputSlot cSlot;
    public final DecompressionOutputSlot dcSlot;

    public CreditCardOutputInventory(PlayerEntity player, DefaultedList<ItemStack> stacks) {
        super(player, 2, stacks);

        cSlot = new CompressionOutputSlot(this, 0);
        dcSlot = new DecompressionOutputSlot(this, 1);
    }

    public CreditCardOutputInventory(PlayerEntity player) {
        this(player, DefaultedList.ofSize(2, ItemStack.EMPTY));
    }
}
