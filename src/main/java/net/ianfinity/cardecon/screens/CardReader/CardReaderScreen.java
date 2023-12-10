package net.ianfinity.cardecon.screens.CardReader;

import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.minecraft.client.gui.DrawContext;

public class CardReaderScreen extends CottonClientScreen {
    public final CardReaderGui gui;

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

