package com.mmodding.extravaganza.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.extravaganza.block.CottonCandyMachineBlock;
import com.mmodding.extravaganza.block.TrashCanBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {

	@ModifyExpressionValue(method = "interactBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;shouldCancelInteraction()Z"))
	private boolean allowTrashCan(boolean original, ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult) {
		Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
		return original && !(block instanceof TrashCanBlock) && !(block instanceof CottonCandyMachineBlock);
	}
}
