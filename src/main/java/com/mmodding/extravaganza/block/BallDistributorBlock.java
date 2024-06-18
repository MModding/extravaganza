package com.mmodding.extravaganza.block;

import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.entity.FestiveBallEntity;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import com.mmodding.extravaganza.mixin.TallPlantBlockAccessor;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class BallDistributorBlock extends Block {

	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

	public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;

	public BallDistributorBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(BallDistributorBlock.FACING, Direction.NORTH).with(BallDistributorBlock.HALF, DoubleBlockHalf.LOWER));
	}

	@Override
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		int number = 0;
		boolean remove = false;
		if (stack.isOf(ExtravaganzaItems.COMMON_FESTIVE_COIN)) {
			number = 1;
			remove = true;
		}
		else if (stack.isOf(ExtravaganzaItems.UNCOMMON_FESTIVE_COIN)) {
			number = 2;
			remove = true;
		}
		else if (stack.isOf(ExtravaganzaItems.GOLDEN_FESTIVE_COIN)) {
			number = 3;
			remove = true;
		}
		for (int i = 0; i < number; i++) {
			FestiveBallEntity entity = ExtravaganzaColor.values()[world.getRandom().nextInt(ExtravaganzaColor.values().length)].createBallEntity(world, player);
			Vec3d position = world.getBlockState(pos.up()).isOf(ExtravaganzaBlocks.BALL_DISTRIBUTOR) ? Vec3d.ofCenter(pos.up()) : Vec3d.ofCenter(pos);
			entity.setPosition(position.add(0.0, 0.5, 0.0));
			entity.setVelocity(
				world.getRandom().nextDouble() * (world.getRandom().nextBoolean() ? -0.25 : 0.25),
				0.6,
				world.getRandom().nextDouble() * (world.getRandom().nextBoolean() ? -0.25 : 0.25)
			);
			world.spawnEntity(entity);
		}
		if (remove) {
			if (!player.getAbilities().creativeMode) {
				stack.decrement(1);
				player.setStackInHand(hand, stack);
			}
			return ItemActionResult.SUCCESS;
		}
		else {
			return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
		}
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		DoubleBlockHalf half = state.get(BallDistributorBlock.HALF);
		if (direction.getAxis() != Direction.Axis.Y || half == DoubleBlockHalf.LOWER != (direction == Direction.UP)) {
			return half == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)
				? Blocks.AIR.getDefaultState()
				: super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
		}
		else {
			return neighborState.getBlock() instanceof BallDistributorBlock && neighborState.get(BallDistributorBlock.HALF) != half
				? neighborState.with(BallDistributorBlock.HALF, half)
				: Blocks.AIR.getDefaultState();
		}
	}

	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!world.isClient && (player.isCreative() || !player.canHarvest(state))) {
			TallPlantBlockAccessor.extravaganza$onBreakInCreative(world, pos, state, player);
		}
		return super.onBreak(world, pos, state, player);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockPos blockPos = ctx.getBlockPos();
		World world = ctx.getWorld();
		if (blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx)) {
			return this.getDefaultState()
				.with(BallDistributorBlock.FACING, ctx.getHorizontalPlayerFacing().getOpposite())
				.with(BallDistributorBlock.HALF, DoubleBlockHalf.LOWER);
		}
		else {
			return null;
		}
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return super.getRenderType(state);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockPos blockPos = pos.down();
		BlockState blockState = world.getBlockState(blockPos);
		return state.get(HALF) == DoubleBlockHalf.LOWER ? blockState.isSideSolidFullSquare(world, blockPos, Direction.UP) : blockState.isOf(this);
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(BallDistributorBlock.FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return mirror == BlockMirror.NONE ? state : state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(BallDistributorBlock.HALF);
		builder.add(FACING);
	}
}
