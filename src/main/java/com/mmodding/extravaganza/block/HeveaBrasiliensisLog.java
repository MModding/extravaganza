package com.mmodding.extravaganza.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class HeveaBrasiliensisLog extends PillarBlock {

	public static final BooleanProperty RUBBER = BooleanProperty.of("rubber");

	public HeveaBrasiliensisLog(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(HeveaBrasiliensisLog.RUBBER, false));
	}

	@Override
	protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!state.get(HeveaBrasiliensisLog.RUBBER) && world.getRandom().nextFloat() < 0.25f) {
			world.setBlockState(pos, state.with(HeveaBrasiliensisLog.RUBBER, true));
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(HeveaBrasiliensisLog.RUBBER);
	}
}
