package net.ianfinity.cardecon;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.ianfinity.cardecon.block.ModBlocks;
import net.ianfinity.cardecon.data.StateSaverAndLoader;
import net.ianfinity.cardecon.item.ModItemGroups;
import net.ianfinity.cardecon.item.ModItems;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CardEcon implements ModInitializer {
	public static final String MOD_ID = "card_econ";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ServerLifecycleEvents.SERVER_STARTED.register(StateSaverAndLoader::getServerState);
	}
}