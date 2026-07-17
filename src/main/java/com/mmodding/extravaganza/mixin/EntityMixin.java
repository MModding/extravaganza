package com.mmodding.extravaganza.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.extravaganza.block.BallPitContentBlock;
import com.mmodding.extravaganza.block.FlattenedBlock;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Shadow
	public abstract Level level();

	@Shadow
	public abstract BlockPos blockPosition();

	@Shadow
	public abstract boolean isSuppressingBounce();

	@Shadow
	public abstract RandomSource getRandom();

	@Shadow
	public abstract void push(double xa, double ya, double za);

	@Inject(method = "baseTick", at = @At("HEAD"))
	private void applyBallPoolContentLogic(CallbackInfo ci) {
		BlockState underState = this.level().getBlockState(this.blockPosition().below());
		BlockState currentState = this.level().getBlockState(this.blockPosition());
		if (!underState.is(ExtravaganzaBlocks.BALL_PIT_CONTENT) && currentState.is(ExtravaganzaBlocks.BALL_PIT_CONTENT)) {
			if (!this.isSuppressingBounce()) {
				this.push(
					this.getRandom().nextDouble() * (this.getRandom().nextBoolean() ? -1 : 1) / 10 * currentState.getValue(BallPitContentBlock.POWER),
					this.getRandom().nextDouble() / 10 * currentState.getValue(BallPitContentBlock.POWER),
					this.getRandom().nextDouble() * (this.getRandom().nextBoolean() ? -1 : 1) / 10 * currentState.getValue(BallPitContentBlock.POWER)
				);
			}
		}
	}

	@WrapOperation(method = "checkFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;fallOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;D)V"))
	private void removeHandleFallDamageIfBallPollContent(Block instance, Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance, Operation<Void> original) {
		if (!this.level().getBlockState(this.blockPosition()).is(ExtravaganzaBlocks.BALL_PIT_CONTENT)) {
			original.call(instance, level, state, pos, entity, fallDistance);
		}
	}

	@WrapMethod(method = "getBlockPosBelowThatAffectsMyMovement")
	private BlockPos modifyVelocityAffectingPos(Operation<BlockPos> original) {
		if (this.level().getBlockState(this.blockPosition()).getBlock() instanceof FlattenedBlock) {
			return this.blockPosition();
		}
		else {
			return original.call();
		}
	}
}
