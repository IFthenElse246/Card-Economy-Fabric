package net.ianfinity.cardecon.screens.CreditCard;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;

public class CreditCardScreen extends CottonInventoryScreen<CreditCardGui> {
    public final CreditCardGui gui;


    public CreditCardScreen(CreditCardGui description, PlayerEntity player) {
        super(description, player);
        gui = description;
    }
}
