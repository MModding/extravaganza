package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.block.HeveaBrasiliensisLog;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;

public class RubberExtractorItem extends Item {

	public RubberExtractorItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (context.getWorld().getBlockState(context.getBlockPos()).isOf(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LOG) && context.getWorld().getBlockState(context.getBlockPos()).get(HeveaBrasiliensisLog.RUBBER)) {
			Vec3d position = Vec3d.ofCenter(context.getBlockPos());
			context.getWorld().spawnEntity(new ItemEntity(context.getWorld(), position.getX(), position.getY(), position.getZ(), ExtravaganzaItems.RUBBER.getDefaultStack()));
			context.getWorld().setBlockState(context.getBlockPos(), context.getWorld().getBlockState(context.getBlockPos()).with(HeveaBrasiliensisLog.RUBBER, false));
			return ActionResult.SUCCESS;
		}
		else {
			return super.useOnBlock(context);
		}
	}
}
