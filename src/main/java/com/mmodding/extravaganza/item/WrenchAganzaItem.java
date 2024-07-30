package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.block.BallPitRegistrationTableBlock;
import com.mmodding.extravaganza.block.entity.BallPitRegistrationTableBlockEntity;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class WrenchAganzaItem extends Item {

	public WrenchAganzaItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		if (world.getBlockEntity(pos) instanceof BallPitRegistrationTableBlockEntity bpitbe) {
			if (!miner.isSneaking() && !world.getBlockState(pos).get(BallPitRegistrationTableBlock.LOCK_SCAN)) {
				bpitbe.switchSelectionMode();
				if (!world.isClient()) {
					Object object;
					if (bpitbe.getSelectionMode().equals(BallPitRegistrationTableBlockEntity.SelectionMode.SOURCE)) {
						object = bpitbe.isSource();
					} else {
						object = bpitbe.getScannedCurrent();
					}
					miner.sendMessage(Text.literal(bpitbe.getSelectionMode().asString() + ": " + object), true);
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
					miner.sendMessage(Text.translatable("enchantment.minecraft.power").append(": " + bpitbe.getPoolSettings().power), true);
				}
			}
		}
		return false;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (context.getWorld().getBlockEntity(context.getBlockPos()) instanceof BallPitRegistrationTableBlockEntity bpitbe) {
			assert context.getPlayer() != null;
			if (!context.getWorld().getBlockState(context.getBlockPos()).get(BallPitRegistrationTableBlock.LOCK_SCAN)) {
				Consumer<BlockPos> deleter = blockPos -> {
					if (context.getWorld().getBlockState(blockPos).isOf(ExtravaganzaBlocks.BALL_PIT_PROTECTION)) {
						context.getWorld().removeBlock(blockPos, false);
					}
				};
				switch (bpitbe.getSelectionMode()) {
					case POSITIVE_X -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().add(1, 0, 0), deleter);
					case NEGATIVE_X -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().add(-1, 0, 0), deleter);
					case POSITIVE_Y -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().add(0, 1, 0), deleter);
					case NEGATIVE_Y -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().add(0, -1, 0), deleter);
					case POSITIVE_Z -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().add(0, 0, 1), deleter);
					case NEGATIVE_Z -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().add(0, 0, -1), deleter);
					case SOURCE -> bpitbe.switchSource();
				}
				if (!context.getWorld().isClient()) {
					Object object;
					if (bpitbe.getSelectionMode().equals(BallPitRegistrationTableBlockEntity.SelectionMode.SOURCE)) {
						object = bpitbe.isSource();
					}
					else {
						object = bpitbe.getScannedCurrent();
					}
					context.getPlayer().sendMessage(Text.literal(bpitbe.getSelectionMode().asString() + ": " + object), true);
				}
				return ActionResult.SUCCESS;
			}
		}
		return super.useOnBlock(context);
	}
}
