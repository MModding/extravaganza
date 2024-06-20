package com.mmodding.extravaganza.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.extravaganza.block.BallPoolContentBlock;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Shadow
	public abstract World getWorld();

	@Shadow
	public abstract BlockPos getBlockPos();

	@Shadow
	public abstract Random getRandom();

	@Shadow
	public abstract void addVelocity(double deltaX, double deltaY, double deltaZ);

	@Shadow
	public abstract boolean bypassesLandingEffects();

	@Inject(method = "baseTick", at = @At("HEAD"))
	private void applyBallPoolContentLogic(CallbackInfo ci) {
		BlockState underState = this.getWorld().getBlockState(this.getBlockPos().down());
		BlockState currentState = this.getWorld().getBlockState(this.getBlockPos());
		if (!underState.isOf(ExtravaganzaBlocks.BALL_POOL_CONTENT) && currentState.isOf(ExtravaganzaBlocks.BALL_POOL_CONTENT)) {
			if (!this.bypassesLandingEffects()) {
				this.addVelocity(
					this.getRandom().nextDouble() * (this.getRandom().nextBoolean() ? -1 : 1) / 10 * currentState.get(BallPoolContentBlock.POWER),
					this.getRandom().nextDouble() / 10 * currentState.get(BallPoolContentBlock.POWER),
					this.getRandom().nextDouble() * (this.getRandom().nextBoolean() ? -1 : 1) / 10 * currentState.get(BallPoolContentBlock.POWER)
				);
			}
		}
	}

	@WrapOperation(method = "fall", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onLandedUpon(Lnet/minecraft/world/World;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;F)V"))
	private void removeHandleFallDamageIfBallPollContent(Block instance, World world, BlockState state, BlockPos landedPosition, Entity entity, float fallDistance, Operation<Void> original) {
		if (!this.getWorld().getBlockState(this.getBlockPos()).isOf(ExtravaganzaBlocks.BALL_POOL_CONTENT)) {
			original.call(instance, world, state, landedPosition, entity, fallDistance);
		}
	}
}
