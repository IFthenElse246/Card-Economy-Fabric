package net.ianfinity.cardecon;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.ianfinity.cardecon.block.ModBlocks;
import net.ianfinity.cardecon.event.MyCurrencyChanged;
import net.ianfinity.cardecon.item.ModItems;
import net.ianfinity.cardecon.networking.NetworkingIdentifiers;
import net.ianfinity.cardecon.screens.CardReader.CardReaderGui;
import net.ianfinity.cardecon.screens.CardReader.CardReaderScreen;
import net.ianfinity.cardecon.screens.CreditCard.CreditCardGui;
import net.ianfinity.cardecon.screens.CreditCard.CreditCardScreen;
import net.ianfinity.cardecon.screens.ModGuis;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class CardEconClient implements ClientModInitializer {
    public static long currency = 0;


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
            UUID uuid = buf.readUuid();
            String username = buf.readString();
            if (username.isEmpty()) {
                username = "<player not found>";
            }

            String finalUsername = username;
            client.execute(() -> {
                MinecraftClient.getInstance().setScreen(new CardReaderScreen(new CardReaderGui(finalUsername, uuid))); // open the gui
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(NetworkingIdentifiers.CARD_READER_PAY, (client, handler, buf, responseSender) -> {
            boolean success = buf.readBoolean();
            String message = buf.readString();

            Screen currentScreen = MinecraftClient.getInstance().currentScreen;
            if (currentScreen instanceof CardReaderScreen readerScreen) {
                readerScreen.gui.showMessage(success, message);
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(NetworkingIdentifiers.UPDATE_CURRENCY, (client, handler, buf, responseSender) -> {

            currency = buf.readLong();
            MyCurrencyChanged.EVENT.invoker().interact(currency);
        });

        HandledScreens.<CreditCardGui, CreditCardScreen>register(ModGuis.CREDIT_CARD_SCREEN_HANDLER, (gui, inventory, title) -> new CreditCardScreen(gui, inventory.player));
    }
}
