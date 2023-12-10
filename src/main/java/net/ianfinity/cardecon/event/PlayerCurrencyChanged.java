package net.ianfinity.cardecon.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public interface PlayerCurrencyChanged {
    Event<PlayerCurrencyChanged> EVENT = EventFactory.createArrayBacked(PlayerCurrencyChanged.class,
            (listeners) -> (player, currency, oldCurrency) -> {
                for (PlayerCurrencyChanged listener : listeners) {
                    listener.interact(player, currency, oldCurrency);
                }
            });

    void interact(ServerPlayerEntity player, Long currency, Long oldCurrency);
}
