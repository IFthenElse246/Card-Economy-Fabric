package net.ianfinity.cardecon.item.items;

import net.minecraft.item.Item;

public class BillStack extends Item implements CashItem{
    public final long value = 10;
    public final Class<? extends CashItem> upgradesTo = BillWad.class;
    public final Class<? extends CashItem> downgradesTo = BillStack.class;

    public BillStack(Settings settings) {
        super(settings);
    }
}
