package com.mmodding.extravaganza.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TraversableRubberStairsBlock extends StairBlock {

	public TraversableRubberStairsBlock(BlockState baseBlockState, Properties properties) {
		super(baseBlockState, properties);
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return !context.isDescending() ? super.getCollisionShape(state, level, pos, context) : Shapes.empty();
	}
}
