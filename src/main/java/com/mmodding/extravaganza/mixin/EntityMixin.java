package com.mmodding.extravaganza.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
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

	@Inject(method = "baseTick", at = @At("HEAD"))
	private void applyBallPoolContentLogic(CallbackInfo ci) {
		BlockState state = this.getWorld().getBlockState(this.getBlockPos());
		if (state.isOf(ExtravaganzaBlocks.BALL_POOL_CONTENT)) {
			this.addVelocity(
				this.getRandom().nextDouble() * (this.getRandom().nextBoolean() ? -1 : 1),
				this.getRandom().nextDouble(),
				this.getRandom().nextDouble() * (this.getRandom().nextBoolean() ? -1 : 1)
			);
		}
	}

	@WrapOperation(method = "fall", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onLandedUpon(Lnet/minecraft/world/World;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;F)V"))
	private void removeHandleFallDamageIfBallPollContent(Block instance, World world, BlockState state, BlockPos landedPosition, Entity entity, float fallDistance, Operation<Void> original) {
		if (!this.getWorld().getBlockState(this.getBlockPos()).isOf(ExtravaganzaBlocks.BALL_POOL_CONTENT)) {
			original.call(instance, world, state, landedPosition, entity, fallDistance);
		}
	}
}
