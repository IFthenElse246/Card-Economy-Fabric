package net.ianfinity.cardecon.screens;

import net.ianfinity.cardecon.CardEcon;
import net.ianfinity.cardecon.screens.CreditCard.CreditCardGui;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModGuis {
    public static ScreenHandlerType<CreditCardGui> CREDIT_CARD_SCREEN_HANDLER;

    public static <T extends  ScreenHandler> ScreenHandlerType<T> registerGui(String name, ScreenHandlerType<T> type) {
        return Registry.register(Registries.SCREEN_HANDLER, new Identifier(CardEcon.MOD_ID, name), type);
    }

    public static void registerModGuis() {
        CREDIT_CARD_SCREEN_HANDLER = (ScreenHandlerType<CreditCardGui>) registerGui("credit_card_screen",
                new ScreenHandlerType<>(
                        (syncId, inventory) ->
                                new CreditCardGui(syncId, inventory, ScreenHandlerContext.EMPTY),
                FeatureFlags.VANILLA_FEATURES));
    }
}
