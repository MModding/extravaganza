package com.mmodding.extravaganza.dispenser;

import com.mmodding.extravaganza.item.HeliumBalloonItem;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;

public class HeliumBalloonDispenseBehavior implements DispenseItemBehavior {

	@Override
	public ItemStack dispense(BlockSource source, ItemStack dispensed) {
		HeliumBalloonItem balloonItem = (HeliumBalloonItem) dispensed.getItem();
		source.level().addFreshEntity(
			balloonItem.createEntity(
				source.level(),
				source.center().relative(source.state().getValue(DispenserBlock.FACING), 1.0),
				dispensed,
				null
			)
		);
		dispensed.consume(1, null);
		return dispensed;
	}
}
