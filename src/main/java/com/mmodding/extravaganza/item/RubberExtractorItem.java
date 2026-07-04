package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.block.HeveaBrasiliensisLog;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class RubberExtractorItem extends Item {

	public RubberExtractorItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getLevel().getBlockState(context.getClickedPos()).is(ExtravaganzaBlocks.HEVEA_BRASILIENSIS.getLog()) && context.getLevel().getBlockState(context.getClickedPos()).getValue(HeveaBrasiliensisLog.RUBBER)) {
			Vec3 position = Vec3.atCenterOf(context.getClickedPos());
			context.getLevel().addFreshEntity(new ItemEntity(context.getLevel(), position.x(), position.y(), position.z(), ExtravaganzaItems.RUBBER.getDefaultInstance()));
			context.getLevel().setBlock(context.getClickedPos(), context.getLevel().getBlockState(context.getClickedPos()).setValue(HeveaBrasiliensisLog.RUBBER, false), Block.UPDATE_ALL);
			if (context.getPlayer() != null) {
				context.getItemInHand().hurtAndBreak(1, context.getPlayer(), EquipmentSlot.MAINHAND);
			}
			return InteractionResult.SUCCESS;
		}
		else {
			return super.useOn(context);
		}
	}
}
