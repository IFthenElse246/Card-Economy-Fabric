package net.ianfinity.cardecon.screens.CreditCard;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.inventory.Inventory;

public class DecompressionOutputSlot extends WItemSlot {
    public final Inventory inventory;

    public DecompressionOutputSlot(Inventory inventory, int index) {
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
