package com.mmodding.extravaganza.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RubberLadderBlock extends LadderBlock {

	public RubberLadderBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.above()).getBlock() instanceof RubberLadderBlock || super.canSurvive(state, level, pos);
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
		BlockState downState = level.getBlockState(pos.below());
		if (downState.getBlock() instanceof RubberLadderBlock && !state.canSurvive(level, pos.below())) {
			level.removeBlock(pos.below(), movedByPiston);
		}
	}
}
