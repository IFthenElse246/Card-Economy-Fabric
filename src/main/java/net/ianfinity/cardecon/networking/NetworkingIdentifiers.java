package net.ianfinity.cardecon.networking;

import net.ianfinity.cardecon.CardEcon;
import net.minecraft.util.Identifier;

public class NetworkingIdentifiers {
    public static final Identifier NOTIFY_OWNER = new Identifier(CardEcon.MOD_ID, "notify_owner");
    public static final Identifier REQUEST_OWNER = new Identifier(CardEcon.MOD_ID, "request_owner");
    public static final Identifier UPDATE_CURRENCY = new Identifier(CardEcon.MOD_ID, "update_currency");
    public static final Identifier CARD_READER_PAY = new Identifier(CardEcon.MOD_ID, "card_reader_pay");
}
