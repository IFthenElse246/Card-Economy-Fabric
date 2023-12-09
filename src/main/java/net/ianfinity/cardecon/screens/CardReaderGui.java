package net.ianfinity.cardecon.screens;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;

public class CardReaderGui extends LightweightGuiDescription {
    public CardReaderGui() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(200, 100);
    }
}
