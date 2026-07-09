package com.mmodding.extravaganza.client.entity.renderer;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.client.entity.state.MerryGoRoundRenderState;
import com.mmodding.extravaganza.client.init.ExtravaganzaModelLayers;
import com.mmodding.extravaganza.entity.MerryGoRoundEntity;
import com.mmodding.library.resource.api.client.model.SimpleEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class MerryGoRoundEntityRenderer extends EntityRenderer<MerryGoRoundEntity, MerryGoRoundRenderState> {

	private static final Identifier TEXTURE = Extravaganza.createId("textures/entity/merry_go_round.png");

	private final EntityModel<MerryGoRoundRenderState> model;

	public MerryGoRoundEntityRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new SimpleEntityModel<>(context.bakeLayer(ExtravaganzaModelLayers.TURNSTILE));
	}

	@Override
	public MerryGoRoundRenderState createRenderState() {
		return new MerryGoRoundRenderState();
	}

	@Override
	public void extractRenderState(MerryGoRoundEntity entity, MerryGoRoundRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.yRot = entity.getYRot();
	}

	@Override
	public void submit(MerryGoRoundRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0f - state.yRot));
		poseStack.mulPose(Axis.XP.rotationDegrees(180.0f));
		poseStack.translate(0.0, -1.5, 0.0);
		submitNodeCollector.submitModel(this.model, state, poseStack, TEXTURE, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
		poseStack.popPose();
	}
}
