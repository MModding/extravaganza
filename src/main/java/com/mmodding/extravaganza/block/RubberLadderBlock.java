package com.mmodding.extravaganza.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class RubberLadderBlock extends LadderBlock {

	public RubberLadderBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos.up()).getBlock() instanceof RubberLadderBlock || super.canPlaceAt(state, world, pos);
	}

	@Override
	protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		BlockState downState = world.getBlockState(pos.down());
		if (!(newState.getBlock() instanceof RubberLadderBlock) && downState.getBlock() instanceof RubberLadderBlock && !state.canPlaceAt(world, pos.down())) {
			world.breakBlock(pos.down(), true);
		}
	}
}
