package com.mmodding.extravaganza.client;

import com.mmodding.extravaganza.block.TrashCanBlock;
import com.mmodding.extravaganza.client.init.*;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.client.ExtendedClientModInitializer;
import com.mmodding.library.core.api.management.ElementsManager;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ExtravaganzaClient implements ExtendedClientModInitializer {

	@Override
	public void setupManager(ElementsManager manager) {
		manager.content(ExtravaganzaModelLayers::register);
		manager.content(ExtravaganzaParticles::register);
		manager.content(ExtravaganzaRenderers::register);
		manager.content(ExtravaganzaColorProviders::register);
	}

	@Override
	public void onInitializeClient(AdvancedContainer mod) {
		ItemTooltipCallback.EVENT.register(ExtravaganzaClient::itemTooltipCallback);
	}

	private static void itemTooltipCallback(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipFlag tooltipFlag, List<Component> components) {
		if (itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof TrashCanBlock) {
			components.add(Component.translatable("message.extravaganza.trash_can.right_click").withStyle(ChatFormatting.GRAY));
			components.add(Component.translatable("message.extravaganza.trash_can.quick_throw").withStyle(ChatFormatting.GRAY));
			components.add(Component.translatable("message.extravaganza.trash_can.opening_trash").withStyle(ChatFormatting.GRAY));
			components.add(Component.translatable("message.extravaganza.trash_can.throw_whole_stack").withStyle(ChatFormatting.GRAY));
		}
	}
}
