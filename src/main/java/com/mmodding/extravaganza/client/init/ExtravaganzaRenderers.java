package com.mmodding.extravaganza.client.init;

import com.mmodding.extravaganza.client.entity.renderer.FestiveBallEntityRenderer;
import com.mmodding.extravaganza.init.ExtravaganzaEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ExtravaganzaRenderers {

	public static void register() {
		EntityRendererRegistry.register(ExtravaganzaEntities.BLACK_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.BLUE_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.BROWN_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.CYAN_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.GRAY_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.GREEN_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.LIGHT_BLUE_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.LIGHT_GRAY_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.LIME_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.MAGENTA_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.ORANGE_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.PINK_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.PURPLE_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.RED_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.WHITE_FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.YELLOW_FESTIVE_BALL, FestiveBallEntityRenderer::new);
	}
}
