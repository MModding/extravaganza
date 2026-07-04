package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.Extravaganza;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class RubberScraperItem extends Item {

	public static final TagKey<Block> MINEABLE = TagKey.create(Registries.BLOCK, Extravaganza.createId("mineable/rubber_scrapper"));

	public RubberScraperItem(Properties properties) {
		super(properties);
	}

	public static Tool createToolComponent() {
		return new Tool(List.of(Tool.Rule.minesAndDrops(BuiltInRegistries.BLOCK.getOrThrow(MINEABLE), 10.0f)), 1.0f, 1, false);
	}

	@Override
	public boolean mineBlock(ItemStack itemStack, Level level, BlockState state, BlockPos pos, LivingEntity owner) {
		if (!level.isClientSide()) {
			itemStack.hurtAndBreak(1, owner, EquipmentSlot.MAINHAND);
		}
		return state.is(RubberScraperItem.MINEABLE);
	}
}
