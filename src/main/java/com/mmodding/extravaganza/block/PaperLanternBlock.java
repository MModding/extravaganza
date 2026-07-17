package com.mmodding.extravaganza.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PaperLanternBlock extends LanternBlock {

	public static final VoxelShape STANDING_SHAPE = Shapes.or(
		Block.box(3.0, 0.0, 3.0, 13.0, 2.0, 13.0),
		Block.box(1.0, 2.0, 1.0, 15.0, 12.0, 15.0),
		Block.box(3.0, 12.0, 3.0, 13.0, 14.0, 13.0)
	);

	public static final VoxelShape HANGING_SHAPE = Shapes.or(
		Block.box(3.0, 2.0, 3.0, 13.0, 4.0, 13.0),
		Block.box(1.0, 4.0, 1.0, 15.0, 14.0, 15.0),
		Block.box(3.0, 14.0, 3.0, 13.0, 16.0, 13.0)
	);

	public PaperLanternBlock(Properties settings) {
		super(settings);
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return state.getValue(PaperLanternBlock.HANGING) ? PaperLanternBlock.HANGING_SHAPE : PaperLanternBlock.STANDING_SHAPE;
	}
}
