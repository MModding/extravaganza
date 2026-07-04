package com.mmodding.extravaganza.client.init;

import com.mmodding.extravaganza.client.block.entity.renderer.BallPitRegistrationTableBlockEntityRenderer;
import com.mmodding.extravaganza.client.entity.renderer.FestiveBallEntityRenderer;
import com.mmodding.extravaganza.client.entity.renderer.HeliumBalloonEntityRenderer;
import com.mmodding.extravaganza.client.entity.renderer.MerryGoRoundEntityRenderer;
import com.mmodding.extravaganza.init.ExtravaganzaBlockEntities;
import com.mmodding.extravaganza.init.ExtravaganzaEntities;
import com.mmodding.library.core.api.AdvancedContainer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class ExtravaganzaRenderers {

	public static void register(AdvancedContainer mod) {
		EntityRenderers.register(ExtravaganzaEntities.FESTIVE_BALL, FestiveBallEntityRenderer::new);
		EntityRenderers.register(ExtravaganzaEntities.HELIUM_BALLOON, HeliumBalloonEntityRenderer::new);
		EntityRenderers.register(ExtravaganzaEntities.MERRY_GO_ROUND, MerryGoRoundEntityRenderer::new);
		BlockEntityRenderers.register(ExtravaganzaBlockEntities.BALL_PIT_REGISTRATION_TABLE, ctx -> new BallPitRegistrationTableBlockEntityRenderer());
	}
}
