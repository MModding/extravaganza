package com.mmodding.extravaganza.client;

import com.mmodding.extravaganza.client.init.ExtravaganzaModelLayers;
import com.mmodding.extravaganza.client.init.ExtravaganzaRenderLayers;
import com.mmodding.extravaganza.client.init.ExtravaganzaRenderers;
import net.fabricmc.api.ClientModInitializer;

public class ExtravaganzaClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ExtravaganzaModelLayers.register();
		ExtravaganzaRenderers.register();
		ExtravaganzaRenderLayers.register();
	}
}
