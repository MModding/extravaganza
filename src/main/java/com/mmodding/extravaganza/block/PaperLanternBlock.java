package com.mmodding.extravaganza.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PaperLanternBlock extends LanternBlock {

	public static final MapCodec<LanternBlock> CODEC = PaperLanternBlock.createCodec(PaperLanternBlock::new);

	public static final VoxelShape STANDING_SHAPE = VoxelShapes.union(
		Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 2.0, 13.0),
		Block.createCuboidShape(1.0, 2.0, 1.0, 15.0, 12.0, 15.0),
		Block.createCuboidShape(3.0, 12.0, 3.0, 13.0, 14.0, 13.0)
	);

	public static final VoxelShape HANGING_SHAPE = VoxelShapes.union(
		Block.createCuboidShape(3.0, 2.0, 3.0, 13.0, 4.0, 13.0),
		Block.createCuboidShape(1.0, 4.0, 1.0, 15.0, 14.0, 15.0),
		Block.createCuboidShape(3.0, 14.0, 3.0, 13.0, 16.0, 13.0)
	);

	public PaperLanternBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<LanternBlock> getCodec() {
		return PaperLanternBlock.CODEC;
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(PaperLanternBlock.HANGING) ? PaperLanternBlock.HANGING_SHAPE : PaperLanternBlock.STANDING_SHAPE;
	}
}
