package com.mmodding.extravaganza.block;

import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.entity.FestiveBallEntity;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import com.mmodding.extravaganza.mixin.TallPlantBlockAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BallDistributorBlock extends Block {

	public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

	public BallDistributorBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(BallDistributorBlock.FACING, Direction.NORTH).setValue(BallDistributorBlock.HALF, DoubleBlockHalf.LOWER));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HALF);
		builder.add(FACING);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		int number = 0;
		boolean remove = false;
		if (stack.is(ExtravaganzaItems.COMMON_FESTIVE_COIN)) {
			number = 1;
			remove = true;
		}
		else if (stack.is(ExtravaganzaItems.UNCOMMON_FESTIVE_COIN)) {
			number = 2;
			remove = true;
		}
		else if (stack.is(ExtravaganzaItems.GOLDEN_FESTIVE_COIN)) {
			number = 4;
			remove = true;
		}
		for (int i = 0; i < number; i++) {
			FestiveBallEntity entity = ExtravaganzaColor.values()[level.getRandom().nextInt(ExtravaganzaColor.values().length)].createBallEntity(level, player);
			Vec3 position = level.getBlockState(pos.above()).is(ExtravaganzaBlocks.BALL_DISTRIBUTOR) ? Vec3.atCenterOf(pos.above()) : Vec3.atCenterOf(pos);
			entity.setPos(position.add(0.0, 0.5, 0.0));
			entity.setDeltaMovement(
				level.getRandom().nextDouble() * (level.getRandom().nextBoolean() ? -0.25 : 0.25),
				0.6,
				level.getRandom().nextDouble() * (level.getRandom().nextBoolean() ? -0.25 : 0.25)
			);
			level.addFreshEntity(entity);
		}
		if (remove) {
			if (!player.getAbilities().invulnerable) {
				stack.shrink(1);
				player.setItemInHand(hand, stack);
			}
			return InteractionResult.SUCCESS;
		}
		else {
			return super.useItemOn(stack, state, level, pos, player, hand, hit);
		}
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
		DoubleBlockHalf half = state.getValue(BallDistributorBlock.HALF);
		if (directionToNeighbour.getAxis() != Direction.Axis.Y || half == DoubleBlockHalf.LOWER != (directionToNeighbour == Direction.UP)) {
			return half == DoubleBlockHalf.LOWER && directionToNeighbour == Direction.DOWN && !state.canSurvive(level, pos)
				? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
		}
		else {
			return neighbourState.getBlock() instanceof BallDistributorBlock && neighbourState.getValue(BallDistributorBlock.HALF) != half
				? neighbourState.setValue(BallDistributorBlock.HALF, half)
				: Blocks.AIR.defaultBlockState();
		}
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (!level.isClientSide() && (player.isCreative() || !player.hasCorrectToolForDrops(state))) {
			TallPlantBlockAccessor.extravaganza$preventDropFromBottomPart(level, pos, state, player);
		}
		return super.playerWillDestroy(level, pos, state, player);
	}

	@Override
	protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
		return state.getValue(HALF).equals(DoubleBlockHalf.LOWER) ? super.getDrops(state, params) : List.of();
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos blockPos = context.getClickedPos();
		Level level = context.getLevel();
		if (blockPos.getY() < level.getMaxY() - 1 && level.getBlockState(blockPos.above()).canBeReplaced(context)) {
			return this.defaultBlockState()
				.setValue(BallDistributorBlock.FACING, context.getHorizontalDirection().getOpposite())
				.setValue(BallDistributorBlock.HALF, DoubleBlockHalf.LOWER);
		}
		else {
			return null;
		}
	}

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), Block.UPDATE_ALL);
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos blockPos = pos.below();
		BlockState blockState = level.getBlockState(blockPos);
		return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockState.isFaceSturdy(level, blockPos, Direction.UP) : blockState.is(this);
	}

	@Override
	protected BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(BallDistributorBlock.FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, Mirror mirror) {
		return mirror == Mirror.NONE ? state : state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
}
