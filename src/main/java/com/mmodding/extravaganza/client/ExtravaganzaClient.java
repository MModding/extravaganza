package com.mmodding.extravaganza.client;

import com.mmodding.extravaganza.block.TrashCanBlock;
import com.mmodding.extravaganza.client.init.ExtravaganzaModelLayers;
import com.mmodding.extravaganza.client.init.ExtravaganzaParticles;
import com.mmodding.extravaganza.client.init.ExtravaganzaRenderLayers;
import com.mmodding.extravaganza.client.init.ExtravaganzaRenderers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class ExtravaganzaClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ExtravaganzaModelLayers.register();
		ExtravaganzaParticles.register();
		ExtravaganzaRenderers.register();
		ExtravaganzaRenderLayers.register();
		ItemTooltipCallback.EVENT.register(ExtravaganzaClient::itemTooltipCallback);
	}

	private static void itemTooltipCallback(ItemStack stack, Item.TooltipContext tooltipContext, TooltipType tooltipType, List<Text> lines) {
		if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof TrashCanBlock) {
			lines.add(Text.translatable("message.extravaganza.trash_can.right_click").formatted(Formatting.GRAY));
			lines.add(Text.translatable("message.extravaganza.trash_can.quick_throw").formatted(Formatting.GRAY));
			lines.add(Text.translatable("message.extravaganza.trash_can.opening_trash").formatted(Formatting.GRAY));
			lines.add(Text.translatable("message.extravaganza.trash_can.throw_whole_stack").formatted(Formatting.GRAY));
		}
	}
}
