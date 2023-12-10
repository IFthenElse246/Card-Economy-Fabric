package net.ianfinity.cardecon.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.UUID;

public interface MyCurrencyChanged {
    Event<MyCurrencyChanged> EVENT = EventFactory.createArrayBacked(MyCurrencyChanged.class,
            (listeners) -> (currency) -> {
                for (MyCurrencyChanged listener : listeners) {
                    listener.interact(currency);
                }
            });

    void interact(long currency);
}
