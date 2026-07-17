package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class EmptyPopcornItem extends Item {

	public EmptyPopcornItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getPlayer() != null && context.getLevel().getBlockState(context.getClickedPos()).is(ExtravaganzaBlocks.POPCORN_MACHINE)) {
			int count = context.getPlayer().getItemInHand(context.getHand()).count();
			if (count > 1) {
				context.getPlayer().getItemInHand(context.getHand()).setCount(count - 1);
				context.getPlayer().addItem(ExtravaganzaItems.POPCORN.getDefaultInstance());
			}
			else {
				context.getPlayer().setItemInHand(context.getHand(), ExtravaganzaItems.POPCORN.getDefaultInstance());
			}
			return InteractionResult.SUCCESS;
		}
		else {
			return super.useOn(context);
		}
	}
}
