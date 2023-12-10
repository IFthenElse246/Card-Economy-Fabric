package net.ianfinity.cardecon.screens.CreditCard;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.ianfinity.cardecon.item.items.CashItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class CompressionOutputSlot extends WItemSlot {
    public final Inventory inventory;

    public CompressionOutputSlot(Inventory inventory, int index) {
        super(inventory, index, 1, 1, false);
        this.inventory = inventory;
    }

    @Override
    public boolean isInsertingAllowed() {
        return false;
    }

    @Override
    public boolean isTakingAllowed() {
        return true;
    }
}
