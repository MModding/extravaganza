package com.mmodding.extravaganza.client.init;

import com.mmodding.extravaganza.Extravaganza;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ExtravaganzaRenderLayers {

	private static final Set<String> CUTOUT = Set.of("sapling", "table", "popcorn", "cotton_candy", "garland", "pinata", "grate", "traversable", "windowed", "ladder", "confetti");

	private static final Set<String> TRANSLUCENT = Set.of("distributor", "ink_puddle", "glass");

	public static void register() {
		Extravaganza.executeKeyForRegistry(Registries.BLOCK, key -> {
			Predicate<String> checker = word -> key.getValue().getPath().contains(word);
			if (!ExtravaganzaRenderLayers.CUTOUT.stream().map(checker::test).filter(Boolean::booleanValue).collect(Collectors.toSet()).isEmpty()) {
				BlockRenderLayerMap.INSTANCE.putBlock(Registries.BLOCK.get(key), RenderLayer.getCutout());
			}
			else if (!ExtravaganzaRenderLayers.TRANSLUCENT.stream().map(checker::test).filter(Boolean::booleanValue).collect(Collectors.toSet()).isEmpty()) {
				BlockRenderLayerMap.INSTANCE.putBlock(Registries.BLOCK.get(key), RenderLayer.getTranslucent());
			}
		});
	}
}
