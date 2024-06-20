package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.block.entity.BallPoolInscriptionTableBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class WrenchAganzaItem extends Item {

	public WrenchAganzaItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		if (world.getBlockEntity(pos) instanceof BallPoolInscriptionTableBlockEntity bpitbe) {
			bpitbe.switchSelectionMode();
			if (!world.isClient()) {
				Object object;
				if (bpitbe.getSelectionMode().equals(BallPoolInscriptionTableBlockEntity.SelectionMode.SOURCE)) {
					object = bpitbe.isSource();
				} else {
					object = bpitbe.getScannedCurrent();
				}
				miner.sendMessage(Text.literal(bpitbe.getSelectionMode().asString() + ": " + object), true);
			}
		}
		return false;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (context.getWorld().getBlockEntity(context.getBlockPos()) instanceof BallPoolInscriptionTableBlockEntity bpitbe) {
			switch (bpitbe.getSelectionMode()) {
				case POSITIVE_X -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().add(1, 0, 0));
				case NEGATIVE_X -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().add(-1, 0, 0));
				case POSITIVE_Y -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().add(0, 1, 0));
				case NEGATIVE_Y -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().add(0, -1, 0));
				case POSITIVE_Z -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().add(0, 0, 1));
				case NEGATIVE_Z -> bpitbe.setScannedCurrent(bpitbe.getScannedCurrent().add(0, 0, -1));
				case SOURCE -> bpitbe.switchSource();
			}
			if (!context.getWorld().isClient()) {
				Object object;
				if (bpitbe.getSelectionMode().equals(BallPoolInscriptionTableBlockEntity.SelectionMode.SOURCE)) {
					object = bpitbe.isSource();
				}
				else {
					object = bpitbe.getScannedCurrent();
				}
				Objects.requireNonNull(context.getPlayer()).sendMessage(Text.literal(bpitbe.getSelectionMode().asString() + ": " + object), true);
			}
			return ActionResult.SUCCESS;
		}
		else {
			return super.useOnBlock(context);
		}
	}
}
