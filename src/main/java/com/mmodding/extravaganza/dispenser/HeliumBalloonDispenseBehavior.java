package com.mmodding.extravaganza.dispenser;

import com.mmodding.extravaganza.item.HeliumBalloonItem;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.ItemStack;

public class HeliumBalloonDispenseBehavior implements DispenseItemBehavior {

	@Override
	public ItemStack dispense(BlockSource source, ItemStack dispensed) {
		HeliumBalloonItem balloonItem = (HeliumBalloonItem) dispensed.getItem();
		balloonItem.createEntity(source.level(), source.center(), dispensed, null);
		return dispensed;
	}
}
