package com.mmodding.extravaganza.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.extravaganza.entity.MerryGoRoundEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

	@Shadow
	private ClientWorld world;

	@ModifyExpressionValue(method = "onEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/s2c/play/EntityS2CPacket;hasRotation()Z"))
	private boolean alwaysRotateIfMerryGoRound(boolean original, EntityS2CPacket packet) {
		return packet.getEntity(this.world) instanceof MerryGoRoundEntity || original;
	}
}
