package net.ianfinity.cardecon.screens;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.ianfinity.cardecon.CardEcon;
import net.ianfinity.cardecon.CardEconClient;
import net.ianfinity.cardecon.event.MyCurrencyChanged;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class CardReaderScreen extends CottonClientScreen {
    final CardReaderGui gui;

    public CardReaderScreen(CardReaderGui description) {
        super(description);
        gui = description;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        super.render(context, mouseX, mouseY, partialTicks);
        gui.onFrame(partialTicks/20f);
    }
}

