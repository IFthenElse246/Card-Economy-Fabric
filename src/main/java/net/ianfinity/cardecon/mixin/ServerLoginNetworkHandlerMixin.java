package net.ianfinity.cardecon.mixin;

import com.mojang.authlib.GameProfile;
import net.ianfinity.cardecon.data.PlayerEconDat;
import net.ianfinity.cardecon.data.StateSaverAndLoader;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ServerLoginNetworkHandler.class)
public class ServerLoginNetworkHandlerMixin {
	@Shadow private @Nullable GameProfile profile;

	@Shadow @Final private MinecraftServer server;

	@Inject(at = @At("RETURN"), method = "acceptPlayer")
	private void init(CallbackInfo info) {
		// KEPT AS EXAMPLE
	}
}