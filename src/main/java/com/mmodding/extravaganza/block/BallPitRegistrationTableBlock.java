package com.mmodding.extravaganza.block;

import com.mmodding.extravaganza.block.entity.BallPitRegistrationTableBlockEntity;
import com.mmodding.extravaganza.init.ExtravaganzaBlockEntities;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.extravaganza.init.ExtravaganzaDataAttachments;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

// This block will manage the full Ball Pool, including changing the power of the velocity in example.
public class BallPitRegistrationTableBlock extends BlockWithEntity {

	public static final MapCodec<BallPitRegistrationTableBlock> CODEC = BallPitRegistrationTableBlock.createCodec(BallPitRegistrationTableBlock::new);

	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public static final BooleanProperty LOCK_SCAN = BooleanProperty.of("lock_scan");
	public static final BooleanProperty LOCK_SETTINGS = BooleanProperty.of("lock_settings");

	public BallPitRegistrationTableBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(BallPitRegistrationTableBlock.FACING, Direction.NORTH).with(BallPitRegistrationTableBlock.LOCK_SCAN, false).with(BallPitRegistrationTableBlock.LOCK_SETTINGS, false));
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return BallPitRegistrationTableBlock.CODEC;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new BallPitRegistrationTableBlockEntity(pos, state);
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (world.getBlockEntity(pos) instanceof BallPitRegistrationTableBlockEntity bpitbe) {
			if (player instanceof ServerPlayerEntity serverPlayer) {
				serverPlayer.setAttached(ExtravaganzaDataAttachments.BEFORE_BALL_PIT, serverPlayer.getPos());
			}
			Vec3d center = bpitbe.getRelativeFullScanned(pos).getCenter();
			player.teleport(center.getX(), center.getY(), center.getZ(), false);
		}
		return ActionResult.SUCCESS;
	}

	@Override
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player.getStackInHand(hand).isOf(ExtravaganzaItems.WRENCH_AGANZA) || player.getStackInHand(hand).isOf(Items.DEBUG_STICK)) {
			return ItemActionResult.FAIL;
		}
		else {
			return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		}
	}

	@Override
	protected void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
		if (world.getBlockEntity(pos) instanceof BallPitRegistrationTableBlockEntity bpitbe) {
			if (!player.isSneaking() && !world.getBlockState(pos).get(BallPitRegistrationTableBlock.LOCK_SCAN)) {
				bpitbe.switchSelectionMode();
				if (!world.isClient()) {
					Object object;
					if (bpitbe.getSelectionMode().equals(BallPitRegistrationTableBlockEntity.SelectionMode.SOURCE)) {
						object = bpitbe.isSource();
					} else {
						object = bpitbe.getScannedCurrent();
					}
					player.sendMessage(Text.literal(bpitbe.getSelectionMode().asString() + ": " + object), true);
				}
			}
			else if (!world.getBlockState(pos).get(BallPitRegistrationTableBlock.LOCK_SETTINGS)) {
				if (bpitbe.getPoolSettings().power < 15) {
					bpitbe.getPoolSettings().power = MathHelper.clamp(bpitbe.getPoolSettings().power + 1, 1, 15);
				}
				else {
					bpitbe.getPoolSettings().power = 1;
				}
				if (!world.isClient()) {
					player.sendMessage(Text.translatable("enchantment.minecraft.power").append(": " + bpitbe.getPoolSettings().power), true);
				}
			}
		}
	}

	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (world.getBlockEntity(pos) instanceof BallPitRegistrationTableBlockEntity bpitbe) {
			BlockPos.iterate(bpitbe.getRelativeScannedStart(pos), bpitbe.getRelativeScannedEnd(pos)).forEach(blockPos -> {
				if (world.getBlockState(blockPos).isOf(ExtravaganzaBlocks.BALL_PIT_PROTECTION)) {
					world.removeBlock(blockPos, false);
				}
			});
		}
		return super.onBreak(world, pos, state, player);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return BallPitRegistrationTableBlock.validateTicker(type, ExtravaganzaBlockEntities.BALL_PIT_REGISTRATION_TABLE, BallPitRegistrationTableBlockEntity::tick);
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(BallPitRegistrationTableBlock.FACING, rotation.rotate(state.get(BallPitRegistrationTableBlock.FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(BallPitRegistrationTableBlock.FACING)));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(BallPitRegistrationTableBlock.FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(BallPitRegistrationTableBlock.FACING);
		builder.add(BallPitRegistrationTableBlock.LOCK_SCAN);
		builder.add(BallPitRegistrationTableBlock.LOCK_SETTINGS);
	}
}
