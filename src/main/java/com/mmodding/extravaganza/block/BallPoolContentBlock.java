package com.mmodding.extravaganza.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;

public class BallPoolContentBlock extends Block {

	public static final IntProperty POWER = Properties.POWER;

	public BallPoolContentBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(BallPoolContentBlock.POWER, 3));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(BallPoolContentBlock.POWER);
	}
}
