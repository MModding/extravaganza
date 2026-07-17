package com.mmodding.extravaganza.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DoublePlantBlock.class)
public interface TallPlantBlockAccessor {

	@Invoker("preventDropFromBottomPart")
	static void extravaganza$preventDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
		throw new IllegalStateException();
	}
}
