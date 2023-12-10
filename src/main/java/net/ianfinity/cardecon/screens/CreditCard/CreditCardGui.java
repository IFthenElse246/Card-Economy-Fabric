package net.ianfinity.cardecon.screens.CreditCard;

import io.github.cottonmc.cotton.gui.ItemSyncedGuiDescription;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel;
import net.ianfinity.cardecon.CardEcon;
import net.ianfinity.cardecon.screens.ModGuis;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class CreditCardGui extends SyncedGuiDescription {
    public final CreditCardInputInventory iInventory;
    public final CreditCardOutputInventory oInventory;
    public final CreditCardDepositInventory dInventory;
    public final ScreenHandlerContext context;

    public final WGridPanel root;
    public final WPlayerInvPanel invPanel;
    public final int width = 175;
    public final int height = 100;

    public CreditCardGui(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModGuis.CREDIT_CARD_SCREEN_HANDLER , syncId, playerInventory);
        this.context = context;
        dInventory = new CreditCardDepositInventory(playerInventory.player);
        iInventory = new CreditCardInputInventory(playerInventory.player);
        oInventory = new CreditCardOutputInventory(playerInventory.player);

        root = new WGridPanel(1);
        root.setSize(width, height);
        setRootPanel(root);

        root.add(dInventory.slot, width/2 - 9, 10);
        root.add(iInventory.cSlot, width/3 - 9, 33);
        root.add(iInventory.dcSlot, 2*width/3 - 9, 33);
        root.add(oInventory.cSlot, width/3 - 9, 60);
        root.add(oInventory.dcSlot, 2*width/3 - 9, 60);

        invPanel = this.createPlayerInventoryPanel();
        root.add(invPanel, width/2-invPanel.getWidth()/2, height - 5);
        root.validate(this);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        this.dropInventory(player, dInventory);
        this.dropInventory(player, iInventory);
        super.onClosed(player);
    }
}
