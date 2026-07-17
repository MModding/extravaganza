package com.mmodding.extravaganza.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.extravaganza.block.CottonCandyMachineBlock;
import com.mmodding.extravaganza.block.TrashCanBlock;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {

	@ModifyExpressionValue(method = "performUseItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSecondaryUseActive()Z"))
	private boolean allowTrashCan(boolean original, LocalPlayer player, InteractionHand hand, BlockHitResult blockHit) {
		Block block = player.level().getBlockState(blockHit.getBlockPos()).getBlock();
		return original && !(block instanceof TrashCanBlock) && !(block instanceof CottonCandyMachineBlock);
	}
}
