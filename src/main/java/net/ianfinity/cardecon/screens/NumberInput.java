package net.ianfinity.cardecon.screens;

import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.minecraft.text.Text;

public class NumberInput extends WTextField {
    public NumberInput() {
        super();
    }

    public NumberInput(Text suggestion) {
        super(suggestion);
    }

    @Override
    public InputResult onCharTyped(char ch) {
        return (ch < '9' && ch > '0') ? super.onCharTyped(ch) : InputResult.IGNORED;
    }
}
