package net.ianfinity.cardecon.item.items;

import net.minecraft.item.Item;

public class Bill extends Item implements CashItem{
    public final long value = 1;

    public final Class<? extends CashItem> upgradesTo = BillStack.class;
    public final Class<? extends CashItem> downgradesTo = null;

    public Bill(Settings settings) {
        super(settings);
    }
}
