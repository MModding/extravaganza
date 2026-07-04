package com.mmodding.extravaganza.client.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.library.core.api.AdvancedContainer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ExtravaganzaModelLayers {

	public static final ModelLayerLocation FESTIVE_BALL = new ModelLayerLocation(Extravaganza.createId("festive_ball"), "main");
	public static final ModelLayerLocation HELIUM_BALLOON = new ModelLayerLocation(Extravaganza.createId("helium_balloon"), "main");
	public static final ModelLayerLocation TURNSTILE = new ModelLayerLocation(Extravaganza.createId("turnstile"), "main");

	public static void register(AdvancedContainer mod) {
		ModelLayerRegistry.registerModelLayer(ExtravaganzaModelLayers.FESTIVE_BALL, ExtravaganzaModels::createFestiveBall);
		ModelLayerRegistry.registerModelLayer(ExtravaganzaModelLayers.HELIUM_BALLOON, ExtravaganzaModels::createHeliumBalloon);
		ModelLayerRegistry.registerModelLayer(ExtravaganzaModelLayers.TURNSTILE, ExtravaganzaModels::createTurnstile);
	}
}
