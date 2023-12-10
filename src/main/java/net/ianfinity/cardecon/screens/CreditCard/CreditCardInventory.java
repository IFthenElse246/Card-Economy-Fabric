package net.ianfinity.cardecon.screens.CreditCard;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class CreditCardInventory implements Inventory {
    protected int size = 0;
    protected DefaultedList<ItemStack> stacks;
    protected  PlayerEntity player;

    public CreditCardInventory(PlayerEntity player, int size, DefaultedList<ItemStack> stacks) {
        this.player = player;
        this.size = size;
        this.stacks = stacks;
    }

    public CreditCardInventory(PlayerEntity player, int size) {
        this(player, size, DefaultedList.ofSize(size, ItemStack.EMPTY));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.stacks) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot >= this.size()) {
            return ItemStack.EMPTY;
        }
        return this.stacks.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.stacks, slot, amount);
        if (!itemStack.isEmpty()) {

        }
        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.stacks, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.stacks.set(slot, stack);
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return player == this.player;
    }

    @Override
    public void clear() {
        this.stacks.clear();
    }
}
