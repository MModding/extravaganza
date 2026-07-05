package com.mmodding.extravaganza.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.extravaganza.block.CottonCandyMachineBlock;
import com.mmodding.extravaganza.block.TrashCanBlock;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

	@ModifyExpressionValue(method = "useItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isSecondaryUseActive()Z"))
	private boolean allowTrashCan(boolean original, final ServerPlayer player, Level level, final ItemStack itemStack, final InteractionHand hand, final BlockHitResult hitResult) {
		Block block = level.getBlockState(hitResult.getBlockPos()).getBlock();
		return original && !(block instanceof TrashCanBlock) && !(block instanceof CottonCandyMachineBlock);
	}
}
