package com.mmodding.extravaganza.client.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.client.entity.model.FestiveBallEntityModel;
import com.mmodding.extravaganza.client.entity.model.HeliumBalloonEntityModel;
import com.mmodding.extravaganza.client.entity.model.MerryGoRoundEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class ExtravaganzaModelLayers {

	public static final EntityModelLayer FESTIVE_BALL = new EntityModelLayer(Extravaganza.createId("festive_ball"), "main");
	public static final EntityModelLayer HELIUM_BALLOON = new EntityModelLayer(Extravaganza.createId("helium_balloon"), "main");
	public static final EntityModelLayer TURNSTILE = new EntityModelLayer(Extravaganza.createId("turnstile"), "main");

	public static void register() {
		EntityModelLayerRegistry.registerModelLayer(ExtravaganzaModelLayers.FESTIVE_BALL, FestiveBallEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ExtravaganzaModelLayers.HELIUM_BALLOON, HeliumBalloonEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ExtravaganzaModelLayers.TURNSTILE, MerryGoRoundEntityModel::getTexturedModelData);
	}
}
