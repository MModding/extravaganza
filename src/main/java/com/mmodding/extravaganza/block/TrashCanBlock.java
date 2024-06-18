package com.mmodding.extravaganza.block;

import com.mmodding.extravaganza.init.ExtravaganzaGameRules;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class TrashCanBlock extends HorizontalFacingBlock {

	public static final MapCodec<TrashCanBlock> CODEC = TrashCanBlock.createCodec(TrashCanBlock::new);

	public static final BooleanProperty OPEN = Properties.OPEN;

	public TrashCanBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(TrashCanBlock.FACING, Direction.NORTH).with(TrashCanBlock.OPEN, false));
	}

	@Override
	protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
		return TrashCanBlock.CODEC;
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		world.setBlockState(pos, state.with(TrashCanBlock.OPEN, !state.get(TrashCanBlock.OPEN)));
		return ActionResult.SUCCESS;
	}

	@Override
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!player.getStackInHand(hand).isEmpty() && player.isSneaking()) {
			stack.decrement(1);
			player.setStackInHand(hand, stack);
			return ItemActionResult.SUCCESS;
		}
		else {
			return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		}
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (world.getBlockState(pos).get(TrashCanBlock.OPEN) && (!(entity instanceof PlayerEntity) || world.getGameRules().getBoolean(ExtravaganzaGameRules.PLAYERS_INTO_TRASH))) {
			entity.damage(world.getDamageSources().outOfWorld(), 1000000.0f);
		}
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(TrashCanBlock.FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(TrashCanBlock.FACING);
		builder.add(TrashCanBlock.OPEN);
	}
}
