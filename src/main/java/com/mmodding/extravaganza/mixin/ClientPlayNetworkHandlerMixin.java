package com.mmodding.extravaganza.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.extravaganza.entity.MerryGoRoundEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin {

	@Shadow
	private ClientLevel level;

	@ModifyExpressionValue(method = "handleMoveEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ClientboundMoveEntityPacket;hasRotation()Z"))
	private boolean alwaysRotateIfMerryGoRound(boolean original, ClientboundMoveEntityPacket packet) {
		return packet.getEntity(this.level) instanceof MerryGoRoundEntity || original;
	}
}
