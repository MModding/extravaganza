package com.mmodding.extravaganza.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mmodding.extravaganza.block.FlattenedBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends EntityMixin {

	@ModifyReturnValue(method = "getVelocityAffectingPos", at = @At("RETURN"))
	private BlockPos modifyVelocityAffectingPos(BlockPos original) {
		if (this.getWorld().getBlockState(this.getBlockPos()).getBlock() instanceof FlattenedBlock) {
			return this.getBlockPos();
		}
		else {
			return original;
		}
	}
}
