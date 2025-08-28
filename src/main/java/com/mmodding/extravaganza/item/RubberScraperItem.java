package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.Extravaganza;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class RubberScraperItem extends Item {

	public static final TagKey<Block> MINEABLE = TagKey.of(RegistryKeys.BLOCK, Extravaganza.createId("mineable/rubber_scrapper"));

	public RubberScraperItem(Settings settings) {
		super(settings);
	}

	public static ToolComponent createToolComponent() {
		return new ToolComponent(List.of(ToolComponent.Rule.of(RubberScraperItem.MINEABLE, 10.0f)), 1.0f, 1);
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (!world.isClient()) {
			stack.damage(1, miner, EquipmentSlot.MAINHAND);
		}
		return state.isIn(RubberScraperItem.MINEABLE);
	}
}
