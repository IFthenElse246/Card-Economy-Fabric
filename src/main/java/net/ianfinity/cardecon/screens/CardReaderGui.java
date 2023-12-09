package net.ianfinity.cardecon.screens;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import net.minecraft.text.Text;

public class CardReaderGui extends LightweightGuiDescription {
    public WTextField reasonField;

    public CardReaderGui(String ownerName) {
        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        root.setSize(200, 150);

        WLabel ownerLabel = new WLabel(Text.translatable("text.card_econ.card_reader_owner_label", ownerName));
        root.add(ownerLabel, 10, 5, 180, 15);
        ownerLabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
        ownerLabel.setVerticalAlignment(VerticalAlignment.CENTER);

        reasonField = new WTextField(Text.translatable("text.card_econ.payment_reason"));
        root.add(reasonField, 10, 23, 180, 15);
    }
}
