package net.ianfinity.cardecon;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.ianfinity.cardecon.block.ModBlocks;
import net.ianfinity.cardecon.networking.NetworkingIdentifiers;
import net.ianfinity.cardecon.screens.CardReaderGui;
import net.ianfinity.cardecon.screens.CardReaderScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CardEconClient implements ClientModInitializer {
    


    @Override
    public void onInitializeClient() {
        ModBlocks.clientBlockHandling();
        ClientPlayNetworking.registerGlobalReceiver(NetworkingIdentifiers.NOTIFY_OWNER, (client, handler, buf, responseSender) -> {
            Text message = Text.translatable("actionbar.card_econ.card_reader_owner").formatted(Formatting.DARK_GREEN);
            client.execute(() -> {
                client.inGameHud.setOverlayMessage(message, false);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(NetworkingIdentifiers.REQUEST_OWNER, (client, handler, buf, responseSender) -> {
            String username = buf.readString();
            if (username.isEmpty()) {
                username = "<player not found>";
            }

            String finalUsername = username;
            client.execute(() -> {
                MinecraftClient.getInstance().setScreen(new CardReaderScreen(new CardReaderGui(finalUsername))); // open the gui
            });
        });
    }
}
