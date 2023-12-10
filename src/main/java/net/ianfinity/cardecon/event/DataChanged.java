package net.ianfinity.cardecon.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface DataChanged {
    Event<DataChanged> EVENT = EventFactory.createArrayBacked(DataChanged.class,
            (listeners) -> () -> {
                for (DataChanged listener : listeners) {
                    listener.interact();
                }
            });

    void interact();
}
