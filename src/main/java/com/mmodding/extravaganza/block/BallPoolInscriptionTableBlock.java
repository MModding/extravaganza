package com.mmodding.extravaganza.block;

import com.mmodding.extravaganza.block.entity.BallPoolInscriptionTableBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

// This block will manage the full Ball Pool, including changing the power of the velocity in example.
public class BallPoolInscriptionTableBlock extends BlockWithEntity {

	public static final MapCodec<BallPoolInscriptionTableBlock> CODEC = BallPoolInscriptionTableBlock.createCodec(BallPoolInscriptionTableBlock::new);

	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public BallPoolInscriptionTableBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return BallPoolInscriptionTableBlock.CODEC;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new BallPoolInscriptionTableBlockEntity(pos, state);
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (world.getBlockEntity(pos) instanceof BallPoolInscriptionTableBlockEntity bpitbe) {
			bpitbe.setScannedStart(pos);
			bpitbe.setScannedEnd(pos);
		}
		super.onPlaced(world, pos, state, placer, itemStack);
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(BallPoolInscriptionTableBlock.FACING, rotation.rotate(state.get(BallPoolInscriptionTableBlock.FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(BallPoolInscriptionTableBlock.FACING)));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(BallPoolInscriptionTableBlock.FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(BallPoolInscriptionTableBlock.FACING);
	}
}
