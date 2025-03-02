package com.mmodding.extravaganza.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.WallBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class TraversableRubberWallBlock extends WallBlock {

	public TraversableRubberWallBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return !context.isDescending() ? super.getCollisionShape(state, world, pos, context) : VoxelShapes.empty();
	}
}
