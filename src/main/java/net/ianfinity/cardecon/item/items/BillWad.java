package net.ianfinity.cardecon.item.items;

import net.minecraft.item.Item;

public class BillWad extends Item implements CashItem{
    public final long value = 100;
    public final Class<? extends CashItem> upgradesTo = null;
    public final Class<? extends CashItem> downgradesTo = BillStack.class;

    public BillWad(Settings settings) {
        super(settings);
    }
}
