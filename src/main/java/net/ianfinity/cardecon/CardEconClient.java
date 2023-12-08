package net.ianfinity.cardecon;

import net.fabricmc.api.ClientModInitializer;
import net.ianfinity.cardecon.block.ModBlocks;

public class CardEconClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModBlocks.clientBlockHandling();
    }
}
