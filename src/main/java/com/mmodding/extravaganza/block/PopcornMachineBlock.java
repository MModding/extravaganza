package com.mmodding.extravaganza.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class PopcornMachineBlock extends HorizontalFacingBlock {

	public static final MapCodec<PopcornMachineBlock> CODEC = PopcornMachineBlock.createCodec(PopcornMachineBlock::new);

	public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

	public PopcornMachineBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
		return PopcornMachineBlock.CODEC;
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(PopcornMachineBlock.FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(PopcornMachineBlock.FACING);
	}
}
