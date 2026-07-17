package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.block.BallPitRegistrationTableBlock;
import com.mmodding.extravaganza.block.entity.BallPitRegistrationTableBlockEntity;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class WrenchAganzaItem extends Item {

	public WrenchAganzaItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean canDestroyBlock(ItemStack stack, BlockState state, Level level, BlockPos pos, LivingEntity living) {
		if (!(living instanceof Player miner)) return false;
		if (level.getBlockEntity(pos) instanceof BallPitRegistrationTableBlockEntity bpitbe) {
			if (!miner.isShiftKeyDown() && !level.getBlockState(pos).getValue(BallPitRegistrationTableBlock.LOCK_SCAN)) {
				bpitbe.switchSelectionMode();
				if (!level.isClientSide()) {
					Object object;
					if (bpitbe.getSelectionMode().equals(BallPitRegistrationTableBlockEntity.SelectionMode.SOURCE)) {
						object = bpitbe.isSource();
					} else {
						object = bpitbe.getScannedCurrent();
					}
					miner.sendOverlayMessage(Component.literal(bpitbe.getSelectionMode().getSerializedName() + ": " + object));
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
					miner.sendOverlayMessage(Component.translatable("enchantment.minecraft.power").append(": " + bpitbe.getPoolSettings().power));
				}
			}
		}
		return false;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof BallPitRegistrationTableBlockEntity bpitbe) {
			assert context.getPlayer() != null;
			if (!context.getLevel().getBlockState(context.getClickedPos()).getValue(BallPitRegistrationTableBlock.LOCK_SCAN)) {
				Consumer<BlockPos> deleter = blockPos -> {
					if (context.getLevel().getBlockState(blockPos).is(ExtravaganzaBlocks.BALL_PIT_PROTECTION)) {
						context.getLevel().removeBlock(blockPos, false);
					}
				};
				switch (bpitbe.getSelectionMode()) {
					case POSITIVE_X -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().offset(1, 0, 0), deleter);
					case NEGATIVE_X -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().offset(-1, 0, 0), deleter);
					case POSITIVE_Y -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().offset(0, 1, 0), deleter);
					case NEGATIVE_Y -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().offset(0, -1, 0), deleter);
					case POSITIVE_Z -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().offset(0, 0, 1), deleter);
					case NEGATIVE_Z -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().offset(0, 0, -1), deleter);
					case SOURCE -> bpitbe.switchSource();
				}
				if (!context.getLevel().isClientSide()) {
					Object object;
					if (bpitbe.getSelectionMode().equals(BallPitRegistrationTableBlockEntity.SelectionMode.SOURCE)) {
						object = bpitbe.isSource();
					}
					else {
						object = bpitbe.getScannedCurrent();
					}
					context.getPlayer().sendOverlayMessage(Component.literal(bpitbe.getSelectionMode().getSerializedName() + ": " + object));
				}
				return InteractionResult.SUCCESS;
			}
		}
		return super.useOn(context);
	}
}
