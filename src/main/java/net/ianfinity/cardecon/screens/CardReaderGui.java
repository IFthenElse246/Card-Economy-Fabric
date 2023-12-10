package net.ianfinity.cardecon.screens;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import net.ianfinity.cardecon.CardEcon;
import net.ianfinity.cardecon.CardEconClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CardReaderGui extends LightweightGuiDescription {
    public final WGridPanel root;
    public final WTextField reasonField;
    public final NumberInput amountField;
    public final WButton pay;

    public WSprite card;
    private float card_depth = 0;
    private float card_position = 2;
    private short card_anim_stage = 0;
    public final int width = 200;
    public final int height = 150;

    public CardReaderGui(String ownerName) {
        root = new WGridPanel(1);
        setRootPanel(root);
        root.setSize(width, height);

        card = new WSprite(new Texture(new Identifier(CardEcon.MOD_ID, "textures/gui/swipe_card.png")));

        WLabel ownerLabel = new WLabel(Text.translatable("text.card_econ.card_reader_owner_label", ownerName));
        root.add(ownerLabel, 10, 3, width-20, 18);
        ownerLabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
        ownerLabel.setVerticalAlignment(VerticalAlignment.CENTER);

        reasonField = new WTextField(Text.translatable("text.card_econ.payment_reason"));
        root.add(reasonField, 10, 21, width-20, 18);

        amountField = new NumberInput(Text.translatable("text.card_econ.payment_amount"));
        root.add(amountField, 10, 44, width/2 - 15, 18);

        pay = new WButton(Text.translatable("text.card_econ.pay_button"));
        root.add(pay, width/2 + 5, 44, width/2 - 15, 18);

        WDynamicLabel balanceLabel = new WDynamicLabel(() -> {
            return I18n.translate("text.card_econ.current_balance", CardEconClient.currency);
        });
        root.add(balanceLabel, 13, 70, width - 26, 9);
    }

    public void onFrame(float deltaTime) {
        long value = 0L;
        try {
            value = Long.parseLong(amountField.getText());
        } catch (NumberFormatException nfe) {

        }
        pay.setEnabled(card_anim_stage == 0 && value != 0 && value <= CardEconClient.currency);
        switch (card_anim_stage) {
            case 1:
                card_position = 0;
                card_depth = Math.min(card_depth + deltaTime * 4, 1);
                if (card_depth == 1) {
                    card_anim_stage ++;
                }
                break;
            case 2:
                card_depth = 1;
                card_position = Math.min(card_position + deltaTime * 3, 1);
                if (card_position == 1) {
                    card_anim_stage ++;
                }
                break;
            case 3:
                card_position = 1;
                card_depth = Math.max(card_depth - deltaTime * 4, 0);
                if (card_depth == 0) {
                    card_anim_stage = 1;
                }
                break;
            default:
                break;
        }
        updateCard();
    }

    private void updateCard() {
        if (card_anim_stage == 0) {
            root.remove(card);
        } else {
            card.setImage(new Texture(new Identifier(CardEcon.MOD_ID, "textures/gui/swipe_card.png")).withUv(0, 0, 1, (21f - ((int)(10*card_depth)))/21));
            root.add(card, (int) (card_position*(width - 4 - card.getWidth()) + 2), -42 + 2*((int) (10*card_depth)), 70, 42-2*((int)(10*card_depth)));
        }
    }
}
