package com.mmodding.extravaganza.block;

import com.mmodding.extravaganza.block.entity.BallPoolRegistrationTableBlockEntity;
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
public class BallPoolRegistrationTableBlock extends BlockWithEntity {

	public static final MapCodec<BallPoolRegistrationTableBlock> CODEC = BallPoolRegistrationTableBlock.createCodec(BallPoolRegistrationTableBlock::new);

	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public static final BooleanProperty LOCK_SCAN = BooleanProperty.of("lock_scan");
	public static final BooleanProperty LOCK_SETTINGS = BooleanProperty.of("lock_settings");

	public BallPoolRegistrationTableBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(BallPoolRegistrationTableBlock.FACING, Direction.NORTH).with(BallPoolRegistrationTableBlock.LOCK_SCAN, false).with(BallPoolRegistrationTableBlock.LOCK_SETTINGS, false));
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return BallPoolRegistrationTableBlock.CODEC;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new BallPoolRegistrationTableBlockEntity(pos, state);
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (world.getBlockEntity(pos) instanceof BallPoolRegistrationTableBlockEntity bpitbe) {
			if (player instanceof ServerPlayerEntity serverPlayer) {
				serverPlayer.setAttached(ExtravaganzaDataAttachments.BEFORE_ENTERING_POOL, serverPlayer.getPos());
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
		if (world.getBlockEntity(pos) instanceof BallPoolRegistrationTableBlockEntity bpitbe) {
			if (!player.isSneaking() && !world.getBlockState(pos).get(BallPoolRegistrationTableBlock.LOCK_SCAN)) {
				bpitbe.switchSelectionMode();
				if (!world.isClient()) {
					Object object;
					if (bpitbe.getSelectionMode().equals(BallPoolRegistrationTableBlockEntity.SelectionMode.SOURCE)) {
						object = bpitbe.isSource();
					} else {
						object = bpitbe.getScannedCurrent();
					}
					player.sendMessage(Text.literal(bpitbe.getSelectionMode().asString() + ": " + object), true);
				}
			}
			else if (!world.getBlockState(pos).get(BallPoolRegistrationTableBlock.LOCK_SETTINGS)) {
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
		if (world.getBlockEntity(pos) instanceof BallPoolRegistrationTableBlockEntity bpitbe) {
			BlockPos.iterate(bpitbe.getRelativeScannedStart(pos), bpitbe.getRelativeScannedEnd(pos)).forEach(blockPos -> {
				if (world.getBlockState(blockPos).isOf(ExtravaganzaBlocks.BALL_POOL_PROTECTION)) {
					world.removeBlock(blockPos, false);
				}
			});
		}
		return super.onBreak(world, pos, state, player);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return BallPoolRegistrationTableBlock.validateTicker(type, ExtravaganzaBlockEntities.BALL_POOl_REGISTRATION_TABLE, BallPoolRegistrationTableBlockEntity::tick);
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(BallPoolRegistrationTableBlock.FACING, rotation.rotate(state.get(BallPoolRegistrationTableBlock.FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(BallPoolRegistrationTableBlock.FACING)));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(BallPoolRegistrationTableBlock.FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(BallPoolRegistrationTableBlock.FACING);
		builder.add(BallPoolRegistrationTableBlock.LOCK_SCAN);
		builder.add(BallPoolRegistrationTableBlock.LOCK_SETTINGS);
	}
}
