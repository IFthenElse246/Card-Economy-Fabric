package net.ianfinity.cardecon.data;

import net.ianfinity.cardecon.CardEcon;
import net.ianfinity.cardecon.CardEconClient;
import net.ianfinity.cardecon.event.CurrencyChanged;
import net.ianfinity.cardecon.event.DataChanged;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.PersistentStateManager;

import java.util.*;

public class PlayerEconDat {
    private final UUID uuid;
    private final NbtCompound nbt = new NbtCompound();
    private static final Map<UUID, PlayerEconDat> playerDats = new HashMap<>();

    private PlayerEconDat(UUID uuid){
        this(uuid, new NbtCompound());
    }

    private PlayerEconDat(UUID uuid, NbtCompound data) {
        this.uuid = uuid;

        loadNbt(data);

        playerDats.put(uuid, this);
    }

    private void loadNbt(NbtCompound data) {
        Long oldCurrency = nbt.contains("Currency", NbtElement.LONG_TYPE) ? nbt.getLong("Currency") : null;

        if (data.contains("Currency", NbtElement.LONG_TYPE)) {
            nbt.putLong("Currency", data.getLong("Currency"));
        } else {
            nbt.putLong("Currency", 0);
        }

        final Long newCurrency = nbt.getLong("Currency");
        if (!newCurrency.equals(oldCurrency)) {
            CurrencyChanged.EVENT.invoker().interact(uuid, newCurrency, oldCurrency);
        }

        if (data.contains("Name", NbtElement.STRING_TYPE)) {
            nbt.putString("Name", data.getString("Name"));
        } else {
            nbt.putString("Name", "");
        }
        DataChanged.EVENT.invoker().interact();
    }

    public static PlayerEconDat getOrCreatePlayerData(UUID uuid) {
        if (playerDats.containsKey(uuid)) {
            return playerDats.get(uuid);
        } else {
            return new PlayerEconDat(uuid);
        }
    }

    public static PlayerEconDat getOrCreatePlayerData(UUID uuid, NbtCompound data) {
        if (playerDats.containsKey(uuid)) {
            PlayerEconDat dat = playerDats.get(uuid);
            dat.loadNbt(data);
            return dat;
        } else {
            return new PlayerEconDat(uuid, data);
        }
    }

    public static PlayerEconDat getPlayerData(UUID uuid) {
        return playerDats.get(uuid);
    }

    public static boolean hasPlayerData(UUID uuid) {
        return playerDats.containsKey(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public NbtCompound getNbt() {
        return nbt;
    }

    public Long getCurrency() {
        return nbt.getLong("Currency");
    }

    public String getName() {
        return nbt.getString("Name");
    }

    public void updateName(String name) {
        nbt.putString("Name", name);
        DataChanged.EVENT.invoker().interact();
    }

    public void setCurrency(Long currency) {
        if (currency < 0) {
            throw new IllegalArgumentException("currency parameter of setCurrency (PlayerEconDat) must be >= 0.");
        }

        Long oldCurrency = nbt.contains("Currency") ? nbt.getLong("Currency") : null;
        nbt.putLong("Currency", currency);

        if (!currency.equals(oldCurrency)) {
            CurrencyChanged.EVENT.invoker().interact(uuid, currency, oldCurrency);
            DataChanged.EVENT.invoker().interact();
        }
    }

    public void destroy() {
        playerDats.remove(uuid);
    }

    public static void clear() {
        playerDats.clear();
    }

    public static Set<UUID> getUuids() {
        return playerDats.keySet();
    }
}
