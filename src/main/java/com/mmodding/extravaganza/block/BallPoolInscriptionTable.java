package com.mmodding.extravaganza.block;

import com.mmodding.extravaganza.block.entity.BallPoolCoreBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

// This block will manage the full Ball Pool, including changing the power of the velocity in example.
public class BallPoolInscriptionTable extends BlockWithEntity {

	public static final MapCodec<BallPoolInscriptionTable> CODEC = BallPoolInscriptionTable.createCodec(BallPoolInscriptionTable::new);

	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public BallPoolInscriptionTable(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return BallPoolInscriptionTable.CODEC;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new BallPoolCoreBlockEntity(pos, state);
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(BallPoolInscriptionTable.FACING, rotation.rotate(state.get(BallPoolInscriptionTable.FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(BallPoolInscriptionTable.FACING)));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(BallPoolInscriptionTable.FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(BallPoolInscriptionTable.FACING);
	}
}
