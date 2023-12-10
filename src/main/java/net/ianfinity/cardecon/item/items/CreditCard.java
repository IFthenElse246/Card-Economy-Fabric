package net.ianfinity.cardecon.item.items;

import net.ianfinity.cardecon.CardEconClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CreditCard extends Item {
    public CreditCard(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.card_econ.card_balance", CardEconClient.currency).formatted(Formatting.GRAY));
        } else {
            tooltip.add(Text.translatable("tooltip.card_econ.credit_card").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("tooltip.card_econ.press_shift_for_info").formatted(Formatting.DARK_GRAY));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
