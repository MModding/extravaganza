package com.mmodding.extravaganza.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;

public class CautionWetFloorSignBlock extends HorizontalFacingBlock {

	public static final MapCodec<CautionWetFloorSignBlock> CODEC = CautionWetFloorSignBlock.createCodec(CautionWetFloorSignBlock::new);

	public CautionWetFloorSignBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
		return CautionWetFloorSignBlock.CODEC;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
