package com.mmodding.extravaganza.client.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.client.color.item.ExtravaganzaColorSource;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.java.api.color.Color;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Map;

public class ExtravaganzaColorProviders {

	public static final Map<String, Color> COLORS = Map.ofEntries(
		Map.entry("black", Color.rgb(0x393941)),
		Map.entry("blue", Color.rgb(0x4d7bdb)),
		Map.entry("brown", Color.rgb(0xab5c21)),
		Map.entry("cyan", Color.rgb(0x2acfb2)),
		Map.entry("gray", Color.rgb(0x75757b)),
		Map.entry("green", Color.rgb(0x67c453)),
		Map.entry("light_blue", Color.rgb(0x5f9fdb)),
		Map.entry("light_gray", Color.rgb(0xb2b1b6)),
		Map.entry("lime", Color.rgb(0x9dbf2c)),
		Map.entry("magenta", Color.rgb(0xd42eae)),
		Map.entry("orange", Color.rgb(0xef6c26)),
		Map.entry("pink", Color.rgb(0xe95d90)),
		Map.entry("purple", Color.rgb(0xa924e0)),
		Map.entry("red", Color.rgb(0xe82d30)),
		Map.entry("white", Color.rgb(0xe6e6ea)),
		Map.entry("yellow", Color.rgb(0xf3aa1c)),
		Map.entry("plant", Color.rgb(0xb6cc6e)),
		Map.entry("tomato", Color.rgb(0xcb4429)),
		Map.entry("tear", Color.rgb(0x73c3dd)),
		Map.entry("nymph", Color.rgb(0xe79cb9))
	);

	private static void applyColorSet(AdvancedContainer mod, String name) {
		Block[] blockSet = mod.streamRegistryHolders(BuiltInRegistries.BLOCK)
			.filter(key -> key.key().identifier().getPath().contains(name) && !key.key().identifier().getPath().contains("colorful"))
			.map(Holder::value)
			.toArray(Block[]::new);
		BlockColorRegistry.register(List.of(
			state -> {
				String path = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
				return ExtravaganzaColorProviders.COLORS.get(path.substring(0, path.length() - name.length() - 1)).toDecimal();
			}
		), blockSet);
	}

	public static void register(AdvancedContainer mod) {
		ExtravaganzaColorProviders.applyColorSet(mod, "ink_puddle");
		ExtravaganzaColorProviders.applyColorSet(mod, "confetti");
		ItemTintSources.ID_MAPPER.put(Extravaganza.createId("color"), ExtravaganzaColorSource.CODEC);
	}
}
