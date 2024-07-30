package com.mmodding.extravaganza.client.init;

import com.mmodding.extravaganza.client.block.entity.renderer.BallPitRegistrationTableBlockEntityRenderer;
import com.mmodding.extravaganza.client.entity.renderer.FestiveBallEntityRenderer;
import com.mmodding.extravaganza.client.entity.renderer.HeliumBalloonEntityRenderer;
import com.mmodding.extravaganza.client.entity.renderer.MerryGoRoundEntityRenderer;
import com.mmodding.extravaganza.init.ExtravaganzaBlockEntities;
import com.mmodding.extravaganza.init.ExtravaganzaEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class ExtravaganzaRenderers {

	public static void register() {
		EntityRendererRegistry.register(ExtravaganzaEntities.FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.HELIUM_BALLOON, HeliumBalloonEntityRenderer::new);
		EntityRendererRegistry.register(ExtravaganzaEntities.MERRY_GO_ROUND, MerryGoRoundEntityRenderer::new);
		BlockEntityRendererFactories.register(ExtravaganzaBlockEntities.BALL_PIT_REGISTRATION_TABLE, ctx -> new BallPitRegistrationTableBlockEntityRenderer());
	}
}
