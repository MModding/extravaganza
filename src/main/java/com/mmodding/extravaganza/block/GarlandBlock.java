package com.mmodding.extravaganza.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class GarlandBlock extends PipeBlock {

	public static final MapCodec<GarlandBlock> CODEC = simpleCodec(GarlandBlock::new);

	public static final BooleanProperty ATTACHED_NORTH = BooleanProperty.create("attached_north");
	public static final BooleanProperty ATTACHED_EAST = BooleanProperty.create("attached_east");
	public static final BooleanProperty ATTACHED_SOUTH = BooleanProperty.create("attached_south");
	public static final BooleanProperty ATTACHED_WEST = BooleanProperty.create("attached_west");
	public static final BooleanProperty ATTACHED_UP = BooleanProperty.create("attached_up");
	public static final BooleanProperty ATTACHED_DOWN = BooleanProperty.create("attached_down");

	public static final Map<Direction, BooleanProperty> ATTACHED_FACING_PROPERTIES = ImmutableMap.copyOf(Util.make(Maps.newEnumMap(Direction.class), directions -> {
		directions.put(Direction.NORTH, ATTACHED_NORTH);
		directions.put(Direction.EAST, ATTACHED_EAST);
		directions.put(Direction.SOUTH, ATTACHED_SOUTH);
		directions.put(Direction.WEST, ATTACHED_WEST);
		directions.put(Direction.UP, ATTACHED_UP);
		directions.put(Direction.DOWN, ATTACHED_DOWN);
	}));

	public GarlandBlock(Properties settings) {
		super(0.15f, settings);
		this.registerDefaultState(
			this.defaultBlockState()
				.setValue(NORTH, false).setValue(EAST, false)
				.setValue(SOUTH, false).setValue(WEST, false)
				.setValue(UP, false).setValue(DOWN, false)
				.setValue(ATTACHED_NORTH, false).setValue(ATTACHED_EAST, false)
				.setValue(ATTACHED_SOUTH, false).setValue(ATTACHED_WEST, false)
				.setValue(ATTACHED_UP, false).setValue(ATTACHED_DOWN, false)
		);
	}

	@Override
	protected MapCodec<? extends PipeBlock> codec() {
		return CODEC;
	}

	private Direction determineDirection(BlockHitResult hit) {
		double x = hit.getLocation().x + -1 * Math.floor(hit.getLocation().x);
		double y = hit.getLocation().y + -1 * Math.floor(hit.getLocation().y);
		double z = hit.getLocation().z + -1 * Math.floor(hit.getLocation().z);
		Vec3 vector = new Vec3(x >= 0 ? x - 0.5 : x + 0.5, y >= 0 ? y - 0.5 : y + 0.5, z >= 0 ? z - 0.5 : z + 0.5);
		if (Math.abs(vector.x) == 0.5) {
			return hit.getDirection();
		}
		else if (Math.abs(vector.x) >= Math.abs(vector.y) && Math.abs(vector.x) >= Math.abs(vector.z)) {
			if (vector.x > 0.0) {
				return Direction.EAST;
			}
			else if (vector.x < -0.0) {
				return Direction.WEST;
			}
		}
		if (Math.abs(vector.y) == 0.5) {
			return hit.getDirection();
		}
		else if (Math.abs(vector.y) >= Math.abs(vector.x) && Math.abs(vector.y) >= Math.abs(vector.z)) {
			if (vector.y > 0.0) {
				return Direction.UP;
			}
			else if (vector.y < -0.0) {
				return Direction.DOWN;
			}
		}
		if (Math.abs(vector.z) == 0.5) {
			return hit.getDirection();
		}
		else if (Math.abs(vector.z) >= Math.abs(vector.x) && Math.abs(vector.z) >= Math.abs(vector.y)) {
			if (vector.z > 0.0) {
				return Direction.SOUTH;
			}
			else if (vector.z < -0.0) {
				return Direction.NORTH;
			}
		}
		return hit.getDirection();
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		BlockState blockState = level.getBlockState(pos.relative(this.determineDirection(hit)));
		Property<Boolean> property = GarlandBlock.PROPERTY_BY_DIRECTION.get(this.determineDirection(hit).getOpposite());
		boolean bool = !blockState.hasProperty(property) || !blockState.getValue(property);
		if (stack.is(ExtravaganzaItems.WRENCH_AGANZA) && bool) {
			level.setBlock(
				pos,
				state.setValue(
					GarlandBlock.PROPERTY_BY_DIRECTION.get(this.determineDirection(hit)),
					!state.getValue(GarlandBlock.ATTACHED_FACING_PROPERTIES.get(this.determineDirection(hit)))
				).setValue(
					GarlandBlock.ATTACHED_FACING_PROPERTIES.get(this.determineDirection(hit)),
					!state.getValue(GarlandBlock.ATTACHED_FACING_PROPERTIES.get(this.determineDirection(hit)))
				),
				GarlandBlock.UPDATE_ALL
			);
			return InteractionResult.SUCCESS;
		}
		else {
			return super.useItemOn(stack, state, level, pos, player, hand, hit);
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return GarlandBlock.withConnectionProperties(context.getLevel(), context.getClickedPos(), this.defaultBlockState());
	}

	public static BlockState withConnectionProperties(BlockGetter world, BlockPos pos, BlockState state) {
		BlockState downState = world.getBlockState(pos.below());
		BlockState upState = world.getBlockState(pos.above());
		BlockState northState = world.getBlockState(pos.north());
		BlockState eastState = world.getBlockState(pos.east());
		BlockState southState = world.getBlockState(pos.south());
		BlockState westState = world.getBlockState(pos.west());
		Block block = state.getBlock();
		return state.trySetValue(DOWN, downState.is(block) || downState.is(ExtravaganzaBlocks.PINATA))
			.trySetValue(UP, upState.is(block))
			.trySetValue(NORTH, northState.is(block))
			.trySetValue(EAST, eastState.is(block))
			.trySetValue(SOUTH, southState.is(block))
			.trySetValue(WEST, westState.is(block));
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
		return state.setValue(
			GarlandBlock.PROPERTY_BY_DIRECTION.get(directionToNeighbour),
			neighbourState.is(this) || state.getValue(GarlandBlock.ATTACHED_FACING_PROPERTIES.get(directionToNeighbour))
		).setValue(
			GarlandBlock.ATTACHED_FACING_PROPERTIES.get(directionToNeighbour),
			!neighbourState.is(this) && state.getValue(GarlandBlock.ATTACHED_FACING_PROPERTIES.get(directionToNeighbour))
		);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
		builder.add(ATTACHED_NORTH, ATTACHED_EAST, ATTACHED_SOUTH, ATTACHED_WEST, ATTACHED_UP, ATTACHED_DOWN);
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}
}
