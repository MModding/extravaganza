package com.mmodding.extravaganza.client.init;

import com.mmodding.extravaganza.Extravaganza;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

import java.util.Arrays;
import java.util.Map;

public class ExtravaganzaColorProviders {

	private static final Map<String, Integer> COLORS = Map.ofEntries(
		Map.entry("black", 0x393941),
		Map.entry("blue", 0x4d7bdb),
		Map.entry("brown", 0xab5c21),
		Map.entry("cyan", 0x2acfb2),
		Map.entry("gray", 0x75757b),
		Map.entry("green", 0x67c453),
		Map.entry("light_blue", 0x5f9fdb),
		Map.entry("light_gray", 0xb2b1b6),
		Map.entry("lime", 0x9dbf2c),
		Map.entry("magenta", 0xd42eae),
		Map.entry("orange", 0xef6c26),
		Map.entry("pink", 0xe95d90),
		Map.entry("purple", 0xa924e0),
		Map.entry("red", 0xe82d30),
		Map.entry("white", 0xe6e6ea),
		Map.entry("yellow", 0xf3aa1c),
		Map.entry("plant", 0xb6cc6e),
		Map.entry("tomato", 0xcb4429),
		Map.entry("tear", 0x73c3dd),
		Map.entry("nymph", 0xe79cb9)
	);

	public static void register() {
		Block[] blockInkMarks = Extravaganza.extractKeyFromRegistry(Registries.BLOCK)
			.filter(key -> key.getValue().getPath().contains("ink_marks") && !key.getValue().getPath().contains("colorful"))
			.map(Registries.BLOCK::get)
			.toArray(Block[]::new);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
			String path = Registries.BLOCK.getId(state.getBlock()).getPath();
			return ExtravaganzaColorProviders.COLORS.get(path.substring(0, path.length() - 10)); // 10 is the length of "_ink_marks"
		}, blockInkMarks);
		Item[] itemInkMarks = Arrays.stream(blockInkMarks).map(Block::asItem).toArray(Item[]::new);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			String path = Registries.ITEM.getId(stack.getItem()).getPath();
			return ExtravaganzaColorProviders.COLORS.get(path.substring(0, path.length() - 10)); // 10 is the length of "_ink_marks"
		}, itemInkMarks);
	}
}
