package com.mmodding.extravaganza.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.extravaganza.block.TrashCanBlock;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

	@ModifyExpressionValue(method = "interactBlockInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;shouldCancelInteraction()Z"))
	private boolean allowTrashCan(boolean original, ClientPlayerEntity player, Hand hand, BlockHitResult hitResult) {
		return original && !(player.getWorld().getBlockState(hitResult.getBlockPos()).getBlock() instanceof TrashCanBlock) && !player.getWorld().getBlockState(hitResult.getBlockPos()).isOf(ExtravaganzaBlocks.BALL_POOL_REGISTRATION_TABLE);
	}
}
