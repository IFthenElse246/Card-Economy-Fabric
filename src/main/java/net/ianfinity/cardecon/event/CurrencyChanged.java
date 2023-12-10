package net.ianfinity.cardecon.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface CurrencyChanged {
    Event<CurrencyChanged> EVENT = EventFactory.createArrayBacked(CurrencyChanged.class,
            (listeners) -> (uuid, currency, oldCurrency) -> {
                for (CurrencyChanged listener : listeners) {
                    listener.interact(uuid, currency, oldCurrency);
                }
            });

    void interact(UUID uuid, Long currency, @Nullable Long oldCurrency);
}
