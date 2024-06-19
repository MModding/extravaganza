package com.mmodding.extravaganza.client.init;

import com.mmodding.extravaganza.client.entity.renderer.FestiveBallEntityRenderer;
import com.mmodding.extravaganza.client.entity.renderer.MerryGoRoundEntityRenderer;
import com.mmodding.extravaganza.init.ExtravaganzaEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ExtravaganzaRenderers {

	public static void register() {
		EntityRendererRegistry.register(ExtravaganzaEntities.FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.MERRY_GO_ROUND, MerryGoRoundEntityRenderer::new);
	}
}
