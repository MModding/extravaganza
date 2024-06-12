package com.mmodding.extravaganza.client.init;

import com.mmodding.extravaganza.Extravaganza;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;

import java.util.function.Predicate;

public class ExtravaganzaRenderLayers {

	public static void register() {
		Extravaganza.executeKeyForRegistry(Registries.BLOCK, key -> {
			Predicate<String> checker = word -> key.getValue().getPath().contains(word);
			if (checker.test("pinata") || checker.test("grate") || checker.test("windowed") || checker.test("ladder")) {
				BlockRenderLayerMap.INSTANCE.putBlock(Registries.BLOCK.get(key), RenderLayer.getCutout());
			}
			else if(checker.test("distributor") || checker.test("stained")) {
				BlockRenderLayerMap.INSTANCE.putBlock(Registries.BLOCK.get(key), RenderLayer.getTranslucent());
			}
		});
	}
}
