package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class EmptyPopcornItem extends Item {

	public EmptyPopcornItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (context.getPlayer() != null && context.getWorld().getBlockState(context.getBlockPos()).isOf(ExtravaganzaBlocks.POPCORN_MACHINE)) {
			context.getPlayer().setStackInHand(context.getHand(), ExtravaganzaItems.POPCORN.getDefaultStack());
			return ActionResult.SUCCESS;
		}
		else {
			return super.useOnBlock(context);
		}
	}
}
