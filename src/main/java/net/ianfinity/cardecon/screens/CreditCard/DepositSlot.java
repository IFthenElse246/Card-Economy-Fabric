package net.ianfinity.cardecon.screens.CreditCard;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.inventory.Inventory;

public class DepositSlot extends WItemSlot {
    public DepositSlot(Inventory inventory, int index) {
        super(inventory, index, 1, 1, false);
    }

    @Override
    public boolean isInsertingAllowed() {
        return true;
    }

    @Override
    public boolean isTakingAllowed() {
        return false;
    }
}
