package com.mmodding.extravaganza.client.init;

import com.mmodding.extravaganza.Extravaganza;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;

public class ExtravaganzaRenderLayers {

	public static void register() {
		Extravaganza.executeKeyForRegistry(Registries.BLOCK, key -> {
			if (key.getValue().getPath().contains("grate") || key.getValue().getPath().contains("windowed")) {
				BlockRenderLayerMap.INSTANCE.putBlock(Registries.BLOCK.get(key), RenderLayer.getCutout());
			}
		});
	}
}
