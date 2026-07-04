package com.mmodding.extravaganza.client.entity.renderer;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.client.entity.state.HeliumBalloonRenderState;
import com.mmodding.extravaganza.client.init.ExtravaganzaModelLayers;
import com.mmodding.extravaganza.entity.HeliumBalloonEntity;
import com.mmodding.library.resource.api.client.model.SimpleEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

public class HeliumBalloonEntityRenderer extends EntityRenderer<HeliumBalloonEntity, HeliumBalloonRenderState> {

	private final EntityModel<HeliumBalloonRenderState> model;

	public HeliumBalloonEntityRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new SimpleEntityModel<>(ExtravaganzaModelLayers.HELIUM_BALLOON, context);
	}

	@Override
	public HeliumBalloonRenderState createRenderState() {
		return new HeliumBalloonRenderState();
	}

	@Override
	public void extractRenderState(HeliumBalloonEntity entity, HeliumBalloonRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.variant = entity.getVariant();
	}

	@Override
	public void submit(HeliumBalloonRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.XP.rotationDegrees(180.0f));
		poseStack.translate(0.0, -1.3, 0.0);
		Identifier texture = Extravaganza.createId("textures/entity/helium_balloon/" + state.variant + ".png");
		submitNodeCollector.submitModel(this.model, state, poseStack, texture, state.lightCoords, 0, state.outlineColor, null);
		poseStack.popPose();
	}
}
