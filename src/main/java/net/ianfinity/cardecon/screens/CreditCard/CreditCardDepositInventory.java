package net.ianfinity.cardecon.screens.CreditCard;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class CreditCardDepositInventory extends CreditCardInventory {
    public final DepositSlot slot;

    public CreditCardDepositInventory(PlayerEntity player, DefaultedList<ItemStack> stacks) {
        super(player, 1, stacks);

        slot = new DepositSlot(this, 0);
    }

    public CreditCardDepositInventory(PlayerEntity player) {
        this(player, DefaultedList.ofSize(1, ItemStack.EMPTY));
    }
}
