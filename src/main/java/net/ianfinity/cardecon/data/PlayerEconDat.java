package net.ianfinity.cardecon.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.PersistentStateManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PlayerEconDat {
    private final UUID uuid;
    private final NbtCompound nbt = new NbtCompound();
    private static final Map<UUID, PlayerEconDat> playerDats = new HashMap<>();

    public PlayerEconDat(UUID uuid){
        this(uuid, new NbtCompound());
    }

    public PlayerEconDat(UUID uuid, NbtCompound data) {
        this.uuid = uuid;

        if (data.contains("Currency", NbtElement.NUMBER_TYPE)) {
            nbt.putInt("Currency", data.getInt("Currency"));
        } else {
            nbt.putInt("Currency", 0);
        }

        playerDats.put(uuid, this);
    }

    public static PlayerEconDat getPlayerData(UUID uuid) {
        return playerDats.get(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public NbtCompound getNbt() {
        return nbt;
    }

    public Integer getCurrency() {
        return nbt.getInt("Currency");
    }

    public void setCurrency(Integer currency) {
        if (currency < 0) {
            throw new IllegalArgumentException("currency parameter of setCurrency (PlayerEconDat) must be >= 0.");
        }

        nbt.putInt("Currency", currency);
    }

    public static Set<UUID> getUuids() {
        return playerDats.keySet();
    }
}
