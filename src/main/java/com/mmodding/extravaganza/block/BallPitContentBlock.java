package com.mmodding.extravaganza.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class BallPitContentBlock extends Block {

	public static final IntegerProperty POWER = BlockStateProperties.POWER;

	public BallPitContentBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(BallPitContentBlock.POWER, 3));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BallPitContentBlock.POWER);
	}
}
