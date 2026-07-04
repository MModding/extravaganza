package com.mmodding.extravaganza.block;

import com.mmodding.extravaganza.init.ExtravaganzaDamageTypes;
import com.mmodding.extravaganza.init.ExtravaganzaGameRules;
import com.mmodding.library.block.api.catalog.SimpleHorizontalFacingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class TrashCanBlock extends SimpleHorizontalFacingBlock {

	public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

	public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED;

	public TrashCanBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.defaultBlockState()
				.setValue(TrashCanBlock.FACING, Direction.NORTH)
				.setValue(TrashCanBlock.OPEN, false)
				.setValue(TrashCanBlock.LOCKED, false)
		);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!state.getValue(TrashCanBlock.LOCKED)) {
			level.setBlock(pos, state.setValue(TrashCanBlock.OPEN, !state.getValue(TrashCanBlock.OPEN)), Block.UPDATE_ALL);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (player.getItemInHand(hand).is(Items.DEBUG_STICK)) {
			return InteractionResult.FAIL;
		}
		else if (!player.getItemInHand(hand).isEmpty() && player.isShiftKeyDown()) {
			stack.shrink(1);
			player.setItemInHand(hand, stack);
			return InteractionResult.SUCCESS;
		}
		else {
			return InteractionResult.PASS;
		}
	}

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
		if (level instanceof ServerLevel serverLevel && level.getBlockState(pos).getValue(TrashCanBlock.OPEN) && (!(entity instanceof Player) || level.getServer().getGameRules().get(ExtravaganzaGameRules.PLAYERS_INTO_TRASH))) {
			DamageSource source;
			if (entity instanceof LivingEntity livingEntity && livingEntity.getKillCredit() != null) {
				source = level.damageSources().source(ExtravaganzaDamageTypes.TRASH, livingEntity.getKillCredit());
			}
			else {
				source = level.damageSources().source(ExtravaganzaDamageTypes.TRASH);
			}
			entity.hurtServer(serverLevel, source, 1000000.0f);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(TrashCanBlock.OPEN);
		builder.add(TrashCanBlock.LOCKED);
	}
}
