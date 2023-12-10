package net.ianfinity.cardecon.item.items;

import net.ianfinity.cardecon.CardEconClient;
import net.ianfinity.cardecon.screens.CreditCard.CreditCardGui;
import net.ianfinity.cardecon.screens.CreditCard.CreditCardScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.*;
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

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            user.openHandledScreen(new NamedScreenHandlerFactory() {
                @Override
                public Text getDisplayName() {
                    return Text.translatable("item.card_econ.credit_card");
                }

                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    return new CreditCardGui(syncId, playerInventory, ScreenHandlerContext.create(world, user.getBlockPos()));
                }
            });
        }

        return TypedActionResult.success(user.getStackInHand(hand), false);
    }


}
