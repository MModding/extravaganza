package com.mmodding.extravaganza.block;

import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class GarlandBlock extends ConnectingBlock {

	public static final MapCodec<GarlandBlock> CODEC = GarlandBlock.createCodec(GarlandBlock::new);

	public GarlandBlock(Settings settings) {
		super(0.3125F, settings);
		this.setDefaultState(
			this.getDefaultState()
				.with(NORTH, false)
				.with(EAST, false)
				.with(SOUTH, false)
				.with(WEST, false)
				.with(UP, false)
				.with(DOWN, false)
		);
	}

	@Override
	public MapCodec<GarlandBlock> getCodec() {
		return GarlandBlock.CODEC;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return withConnectionProperties(ctx.getWorld(), ctx.getBlockPos(), this.getDefaultState());
	}

	public static BlockState withConnectionProperties(BlockView world, BlockPos pos, BlockState state) {
		BlockState downState = world.getBlockState(pos.down());
		BlockState upState = world.getBlockState(pos.up());
		BlockState northState = world.getBlockState(pos.north());
		BlockState eastState = world.getBlockState(pos.east());
		BlockState southState = world.getBlockState(pos.south());
		BlockState westState = world.getBlockState(pos.west());
		Block block = state.getBlock();
		return state.withIfExists(DOWN, downState.isOf(block) || downState.isOf(ExtravaganzaBlocks.PINATA))
			.withIfExists(UP, upState.isOf(block))
			.withIfExists(NORTH, northState.isOf(block))
			.withIfExists(EAST, eastState.isOf(block))
			.withIfExists(SOUTH, southState.isOf(block))
			.withIfExists(WEST, westState.isOf(block));
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return state.with(
			GarlandBlock.FACING_PROPERTIES.get(direction),
			neighborState.isOf(this) || direction == Direction.DOWN && neighborState.isOf(ExtravaganzaBlocks.PINATA)
		);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
	}

	@Override
	protected boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}
}
