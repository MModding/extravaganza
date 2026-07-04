package com.mmodding.extravaganza.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class HeveaBrasiliensisLog extends RotatedPillarBlock {

	public static final BooleanProperty RUBBER = BooleanProperty.create("rubber");

	public HeveaBrasiliensisLog(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(HeveaBrasiliensisLog.RUBBER, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HeveaBrasiliensisLog.RUBBER);
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!state.getValue(HeveaBrasiliensisLog.RUBBER) && level.getRandom().nextFloat() < 0.25f) {
			level.setBlock(pos, state.setValue(HeveaBrasiliensisLog.RUBBER, true), Block.UPDATE_ALL);
		}
	}
}
