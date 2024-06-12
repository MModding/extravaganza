package com.mmodding.extravaganza.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TallPlantBlock.class)
public interface TallPlantBlockAccessor {

	@Invoker("onBreakInCreative")
	static void extravaganza$onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		throw new IllegalStateException();
	}
}
