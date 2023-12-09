package net.ianfinity.cardecon.data;

import net.ianfinity.cardecon.CardEcon;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.UUID;

public class StateSaverAndLoader extends PersistentState {

    public static StateSaverAndLoader createFromNbt(NbtCompound tag) {
        StateSaverAndLoader state = new StateSaverAndLoader();
        final NbtCompound playerDatas;
        if (tag.contains("economyData", NbtElement.COMPOUND_TYPE))
            playerDatas = tag.getCompound("economyData");
        else
            playerDatas = new NbtCompound();

        for (String strUuid : playerDatas.getKeys()) {
            UUID uuid = UUID.fromString(strUuid);
            if (playerDatas.contains(strUuid, NbtElement.COMPOUND_TYPE))
                PlayerEconDat.getOrCreatePlayerData(uuid, playerDatas.getCompound(strUuid));
            else
                PlayerEconDat.getOrCreatePlayerData(uuid);
        }

        return state;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt){
        final NbtCompound playerDatas = new NbtCompound();

        for (UUID uuid : PlayerEconDat.getUuids()) {
            String strUuid = uuid.toString();

            playerDatas.put(strUuid, PlayerEconDat.getPlayerData(uuid).getNbt());
        }

        nbt.put("economyData", playerDatas);
        return nbt;
    }

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        StateSaverAndLoader state = persistentStateManager.getOrCreate(StateSaverAndLoader::createFromNbt, StateSaverAndLoader::new, CardEcon.MOD_ID);

        // this means the data should be written to disk. For marginally better performance on save/load,
        // only mark dirty if changes were made, but it's not important
        state.markDirty();

        return state;
    }
}
