package net.ianfinity.cardecon.item.items;

import org.jetbrains.annotations.Nullable;

public interface CashItem {
    public final long value = 1;

    @Nullable
    public final Class<? extends CashItem> upgradesTo = null;
    @Nullable
    public final Class<? extends CashItem> downgradesTo = null;
}
