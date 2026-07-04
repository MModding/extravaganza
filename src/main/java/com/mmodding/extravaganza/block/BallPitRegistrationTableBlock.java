package com.mmodding.extravaganza.block;

import com.mmodding.extravaganza.block.entity.BallPitRegistrationTableBlockEntity;
import com.mmodding.extravaganza.init.ExtravaganzaBlockEntities;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.extravaganza.init.ExtravaganzaDataAttachments;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

// This block will manage the full Ball Pool, including changing the power of the velocity in example.
public class BallPitRegistrationTableBlock extends BaseEntityBlock {

	public static final MapCodec<BallPitRegistrationTableBlock> CODEC = simpleCodec(BallPitRegistrationTableBlock::new);

	public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

	public static final BooleanProperty LOCK_SCAN = BooleanProperty.create("lock_scan");
	public static final BooleanProperty LOCK_SETTINGS = BooleanProperty.create("lock_settings");

	public BallPitRegistrationTableBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.defaultBlockState()
				.setValue(BallPitRegistrationTableBlock.FACING, Direction.NORTH)
				.setValue(BallPitRegistrationTableBlock.LOCK_SCAN, false)
				.setValue(BallPitRegistrationTableBlock.LOCK_SETTINGS, false)
		);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return BallPitRegistrationTableBlock.CODEC;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BallPitRegistrationTableBlock.FACING);
		builder.add(BallPitRegistrationTableBlock.LOCK_SCAN);
		builder.add(BallPitRegistrationTableBlock.LOCK_SETTINGS);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BallPitRegistrationTableBlockEntity(pos, state);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (player.getItemInHand(hand).is(ExtravaganzaItems.WRENCH_AGANZA) || player.getItemInHand(hand).is(Items.DEBUG_STICK)) {
			return InteractionResult.FAIL;
		}
		else {
			return InteractionResult.PASS;
		}
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.getBlockEntity(pos) instanceof BallPitRegistrationTableBlockEntity bpitbe) {
			if (player instanceof ServerPlayer serverPlayer) {
				serverPlayer.setAttached(ExtravaganzaDataAttachments.BEFORE_BALL_PIT, serverPlayer.position());
			}
			Vec3 center = bpitbe.getRelativeFullScanned(pos).getCenter();
			player.teleportTo(center.x(), center.y(), center.z());
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
		if (level.getBlockEntity(pos) instanceof BallPitRegistrationTableBlockEntity bpitbe) {
			if (!player.isShiftKeyDown() && !level.getBlockState(pos).getValue(BallPitRegistrationTableBlock.LOCK_SCAN)) {
				bpitbe.switchSelectionMode();
				if (!level.isClientSide()) {
					Object object;
					if (bpitbe.getSelectionMode().equals(BallPitRegistrationTableBlockEntity.SelectionMode.SOURCE)) {
						object = bpitbe.isSource();
					} else {
						object = bpitbe.getScannedCurrent();
					}
					player.sendOverlayMessage(Component.literal(bpitbe.getSelectionMode().getSerializedName() + ": " + object));
				}
			}
			else if (!level.getBlockState(pos).getValue(BallPitRegistrationTableBlock.LOCK_SETTINGS)) {
				if (bpitbe.getPoolSettings().power < 15) {
					bpitbe.getPoolSettings().power = Mth.clamp(bpitbe.getPoolSettings().power + 1, 1, 15);
				}
				else {
					bpitbe.getPoolSettings().power = 1;
				}
				if (!level.isClientSide()) {
					player.sendOverlayMessage(Component.translatable("enchantment.minecraft.power").append(": " + bpitbe.getPoolSettings().power));
				}
			}
		}
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (level.getBlockEntity(pos) instanceof BallPitRegistrationTableBlockEntity bpitbe) {
			BlockPos.betweenClosed(bpitbe.getRelativeScannedStart(pos), bpitbe.getRelativeScannedEnd(pos)).forEach(blockPos -> {
				if (level.getBlockState(blockPos).is(ExtravaganzaBlocks.BALL_PIT_PROTECTION)) {
					level.removeBlock(blockPos, false);
				}
			});
		}
		return super.playerWillDestroy(level, pos, state, player);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
		return BallPitRegistrationTableBlock.createTickerHelper(type, ExtravaganzaBlockEntities.BALL_PIT_REGISTRATION_TABLE, BallPitRegistrationTableBlockEntity::tick);
	}

	@Override
	protected BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(BallPitRegistrationTableBlock.FACING, rotation.rotate(state.getValue(BallPitRegistrationTableBlock.FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(BallPitRegistrationTableBlock.FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(BallPitRegistrationTableBlock.FACING, ctx.getHorizontalDirection().getOpposite());
	}
}
