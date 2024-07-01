package com.mmodding.extravaganza.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Map;

public class GarlandBlock extends ConnectingBlock {

	public static final MapCodec<GarlandBlock> CODEC = GarlandBlock.createCodec(GarlandBlock::new);

	public static final BooleanProperty ATTACHED_NORTH = BooleanProperty.of("attached_north");
	public static final BooleanProperty ATTACHED_EAST = BooleanProperty.of("attached_east");
	public static final BooleanProperty ATTACHED_SOUTH = BooleanProperty.of("attached_south");
	public static final BooleanProperty ATTACHED_WEST = BooleanProperty.of("attached_west");
	public static final BooleanProperty ATTACHED_UP = BooleanProperty.of("attached_up");
	public static final BooleanProperty ATTACHED_DOWN = BooleanProperty.of("attached_down");

	public static final Map<Direction, BooleanProperty> ATTACHED_FACING_PROPERTIES = ImmutableMap.copyOf(Util.make(Maps.newEnumMap(Direction.class), directions -> {
		directions.put(Direction.NORTH, ATTACHED_NORTH);
		directions.put(Direction.EAST, ATTACHED_EAST);
		directions.put(Direction.SOUTH, ATTACHED_SOUTH);
		directions.put(Direction.WEST, ATTACHED_WEST);
		directions.put(Direction.UP, ATTACHED_UP);
		directions.put(Direction.DOWN, ATTACHED_DOWN);
	}));

	public GarlandBlock(Settings settings) {
		super(0.15f, settings);
		this.setDefaultState(
			this.getDefaultState()
				.with(NORTH, false).with(EAST, false)
				.with(SOUTH, false).with(WEST, false)
				.with(UP, false).with(DOWN, false)
				.with(ATTACHED_NORTH, false).with(ATTACHED_EAST, false)
				.with(ATTACHED_SOUTH, false).with(ATTACHED_WEST, false)
				.with(ATTACHED_UP, false).with(ATTACHED_DOWN, false)
		);
	}

	@Override
	public MapCodec<GarlandBlock> getCodec() {
		return GarlandBlock.CODEC;
	}

	private Direction determineDirection(BlockHitResult hit) {
		double x = hit.getPos().x + -1 * Math.floor(hit.getPos().x);
		double y = hit.getPos().y + -1 * Math.floor(hit.getPos().y);
		double z = hit.getPos().z + -1 * Math.floor(hit.getPos().z);
		Vec3d vector = new Vec3d(x >= 0 ? x - 0.5 : x + 0.5, y >= 0 ? y - 0.5 : y + 0.5, z >= 0 ? z - 0.5 : z + 0.5);
		if (Math.abs(vector.x) == 0.5) {
			return hit.getSide();
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
			return hit.getSide();
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
			return hit.getSide();
		}
		else if (Math.abs(vector.z) >= Math.abs(vector.x) && Math.abs(vector.z) >= Math.abs(vector.y)) {
			if (vector.z > 0.0) {
				return Direction.SOUTH;
			}
			else if (vector.z < -0.0) {
				return Direction.NORTH;
			}
		}
		return hit.getSide();
	}

	@Override
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		BlockState blockState = world.getBlockState(pos.offset(this.determineDirection(hit)));
		Property<Boolean> property = GarlandBlock.FACING_PROPERTIES.get(this.determineDirection(hit).getOpposite());
		boolean bool = !blockState.contains(property) || !blockState.get(property);
		if (stack.isOf(ExtravaganzaItems.WRENCH_AGANZA) && bool) {
			world.setBlockState(
				pos,
				state.with(
					GarlandBlock.FACING_PROPERTIES.get(this.determineDirection(hit)),
					!state.get(GarlandBlock.ATTACHED_FACING_PROPERTIES.get(this.determineDirection(hit)))
				).with(
					GarlandBlock.ATTACHED_FACING_PROPERTIES.get(this.determineDirection(hit)),
					!state.get(GarlandBlock.ATTACHED_FACING_PROPERTIES.get(this.determineDirection(hit)))
				)
			);
			return ItemActionResult.SUCCESS;
		}
		else {
			return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
		}
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return GarlandBlock.withConnectionProperties(ctx.getWorld(), ctx.getBlockPos(), this.getDefaultState());
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
			neighborState.isOf(this) || state.get(GarlandBlock.ATTACHED_FACING_PROPERTIES.get(direction))
		).with(
			GarlandBlock.ATTACHED_FACING_PROPERTIES.get(direction),
			!neighborState.isOf(this) && state.get(GarlandBlock.ATTACHED_FACING_PROPERTIES.get(direction))
		);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
		builder.add(ATTACHED_NORTH, ATTACHED_EAST, ATTACHED_SOUTH, ATTACHED_WEST, ATTACHED_UP, ATTACHED_DOWN);
	}

	@Override
	protected boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}
}
