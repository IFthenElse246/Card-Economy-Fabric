package net.ianfinity.cardecon.screens.CreditCard;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.ianfinity.cardecon.item.items.CashItem;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class DecompressionInputSlot extends WItemSlot {
    public DecompressionInputSlot(Inventory inventory, int index) {
        super(inventory, index, 1, 1, false);
        setInputFilter(new Predicate<ItemStack>() {
            @Override
            public boolean test(ItemStack itemStack) {
                if (!(itemStack.getItem() instanceof CashItem item)) {
                    return false;
                }
                return item.downgradesTo != null;
            }
        });
    }

    @Override
    public boolean isInsertingAllowed() {
        return true;
    }

    @Override
    public boolean isTakingAllowed() {
        return true;
    }


}
