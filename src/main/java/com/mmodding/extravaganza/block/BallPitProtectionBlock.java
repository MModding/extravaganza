package com.mmodding.extravaganza.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class BallPitProtectionBlock extends Block {

	public BallPitProtectionBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	@Override
	protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}

	@Override
	protected int getOpacity(BlockState state, BlockView world, BlockPos pos) {
		return 0;
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.empty();
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		for (Direction direction : Direction.values()) {
			if (world.getBlockState(pos.offset(direction)).isAir()) {
				return VoxelShapes.cuboid(0.0, 0.1, 0.0, 1.0, 1.0, 1.0);
			}
		}
		return VoxelShapes.empty();
	}
}
