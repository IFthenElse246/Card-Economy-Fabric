package net.ianfinity.cardecon;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.impl.screenhandler.Networking;
import net.ianfinity.cardecon.block.ModBlocks;
import net.ianfinity.cardecon.block.entity.CardReaderEntity;
import net.ianfinity.cardecon.command.ModCommands;
import net.ianfinity.cardecon.data.PlayerEconDat;
import net.ianfinity.cardecon.data.StateSaverAndLoader;
import net.ianfinity.cardecon.event.CurrencyChanged;
import net.ianfinity.cardecon.event.DataChanged;
import net.ianfinity.cardecon.event.PlayerCurrencyChanged;
import net.ianfinity.cardecon.item.ModItemGroups;
import net.ianfinity.cardecon.item.ModItems;
import net.ianfinity.cardecon.networking.NetworkingIdentifiers;
import net.ianfinity.cardecon.screens.CreditCard.CreditCardGui;
import net.ianfinity.cardecon.screens.ModGuis;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.SocialInteractionsManager;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.UUID;

public class CardEcon implements ModInitializer {
	public static final String MOD_ID = "card_econ";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModCommands.registerCommands();
		ModGuis.registerModGuis();

		ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
			CurrencyChanged.EVENT.register((uuid, currency, oldCurrency) -> {
				PlayerManager manager = server.getPlayerManager();
				ServerPlayerEntity player = manager.getPlayer(uuid);
				if (player != null) {
					PlayerCurrencyChanged.EVENT.invoker().interact(player, currency, oldCurrency);
				}
			});
			StateSaverAndLoader.getServerState(server);
		});

		PlayerCurrencyChanged.EVENT.register((player, currency, oldCurrency) -> {
			PacketByteBuf buf = PacketByteBufs.create();
			buf.writeLong(currency);
			ServerPlayNetworking.send(player, NetworkingIdentifiers.UPDATE_CURRENCY, buf);
		});

		ServerPlayNetworking.registerGlobalReceiver(NetworkingIdentifiers.CARD_READER_PAY, (server, player, handler, buf, responseSender) -> {
			UUID recipient = buf.readUuid();
			long amount = buf.readLong();

			server.execute(() -> {
				PlayerEconDat senderDat = PlayerEconDat.getOrCreatePlayerData(player.getUuid());
				PlayerEconDat recipientDat = PlayerEconDat.getOrCreatePlayerData(recipient);

				if (senderDat.getCurrency() < amount) {
					PacketByteBuf responseBuf = PacketByteBufs.create();
					responseBuf.writeBoolean(false);
					responseBuf.writeString("text.card_econ.card_reader.pay.fail.insufficient_balance");

					responseSender.sendPacket(NetworkingIdentifiers.CARD_READER_PAY, responseBuf);
					return;
				}

				senderDat.setCurrency(senderDat.getCurrency() - amount);
				recipientDat.setCurrency(recipientDat.getCurrency() + amount);

				PacketByteBuf responseBuf = PacketByteBufs.create();
				responseBuf.writeBoolean(true);
				responseBuf.writeString("text.card_econ.card_reader.pay.success");

				responseSender.sendPacket(NetworkingIdentifiers.CARD_READER_PAY, responseBuf);
			});
		});

		ServerPlayNetworking.registerGlobalReceiver(NetworkingIdentifiers.REQUEST_OWNER, (server, player, handler, buf, responseSender) -> {
			GlobalPos gPos = buf.readGlobalPos();

			server.execute(() -> {
				World world = server.getWorld(gPos.getDimension());

				if (world == null) {
					return;
				}

				BlockPos pos = gPos.getPos();
				BlockEntity be = world.getBlockEntity(pos);

				if (be instanceof CardReaderEntity readerEntity) {
					String username = "";
					UUID uuid = readerEntity.getOwnerUuid();

					if (PlayerEconDat.hasPlayerData(uuid)) {
						username = PlayerEconDat.getPlayerData(uuid).getName();
					}

					PacketByteBuf resultBuf = PacketByteBufs.create();
					resultBuf.writeUuid(uuid);
					resultBuf.writeString(username);

					responseSender.sendPacket(NetworkingIdentifiers.REQUEST_OWNER, resultBuf);
				}
			});
		});

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayerEntity player = handler.player;

			server.execute(() -> {
				UUID uuid = player.getUuid();
				String name = player.getName().getString();
				StateSaverAndLoader.getServerState(server);
				PlayerEconDat dat = PlayerEconDat.getOrCreatePlayerData(uuid);
				dat.updateName(name);
				PacketByteBuf buf = PacketByteBufs.create();
				buf.writeLong(dat.getCurrency());
				ServerPlayNetworking.send(player, NetworkingIdentifiers.UPDATE_CURRENCY, buf);
			});
		});


	}
}