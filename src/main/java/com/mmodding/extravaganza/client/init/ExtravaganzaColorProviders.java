package com.mmodding.extravaganza.client.init;

import com.mmodding.extravaganza.Extravaganza;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.ColorHelper;

import java.util.Arrays;
import java.util.Map;

public class ExtravaganzaColorProviders {

	private static final Map<String, Color> COLORS = Map.ofEntries(
		Map.entry("black", Color.argb(0x393941)),
		Map.entry("blue", Color.argb(0x4d7bdb)),
		Map.entry("brown", Color.argb(0xab5c21)),
		Map.entry("cyan", Color.argb(0x2acfb2)),
		Map.entry("gray", Color.argb(0x75757b)),
		Map.entry("green", Color.argb(0x67c453)),
		Map.entry("light_blue", Color.argb(0x5f9fdb)),
		Map.entry("light_gray", Color.argb(0xb2b1b6)),
		Map.entry("lime", Color.argb(0x9dbf2c)),
		Map.entry("magenta", Color.argb(0xd42eae)),
		Map.entry("orange", Color.argb(0xef6c26)),
		Map.entry("pink", Color.argb(0xe95d90)),
		Map.entry("purple", Color.argb(0xa924e0)),
		Map.entry("red", Color.argb(0xe82d30)),
		Map.entry("white", Color.argb(0xe6e6ea)),
		Map.entry("yellow", Color.argb(0xf3aa1c)),
		Map.entry("plant", Color.argb(0xb6cc6e)),
		Map.entry("tomato", Color.argb(0xcb4429)),
		Map.entry("tear", Color.argb(0x73c3dd)),
		Map.entry("nymph", Color.argb(0xe79cb9))
	);

	private static void applyColorSet(AdvancedContainer mod, String name) {
		Block[] blockSet = mod.streamRegistryEntries(BuiltInRegistries.BLOCK)
			.filter(key -> key.getKey().identifier().getPath().contains(name) && !key.getKey().identifier().getPath().contains("colorful"))
			.map(Map.Entry::getValue)
			.toArray(Block[]::new);
		BlockColorRegistry.register(List.of(
			state -> {
				String path = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
				return ExtravaganzaColorProviders.COLORS.get(path.substring(0, path.length() - name.length() - 1)).toDecimal();
			}
		), blockSet);
		Item[] itemSet = Arrays.stream(blockSet).map(Block::asItem).toArray(Item[]::new);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			String path = Registries.ITEM.getId(stack.getItem()).getPath();
			return ColorHelper.Argb.fullAlpha(ExtravaganzaColorProviders.COLORS.get(path.substring(0, path.length() - name.length() - 1)));
		}, itemSet);
	}

	public static void register(AdvancedContainer mod) {
		ExtravaganzaColorProviders.applyColorSet(mod, "ink_puddle");
		ExtravaganzaColorProviders.applyColorSet(mod, "confetti");
	}
}
