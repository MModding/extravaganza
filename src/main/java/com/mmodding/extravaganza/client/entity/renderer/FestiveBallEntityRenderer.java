package com.mmodding.extravaganza.client.entity.renderer;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.client.entity.state.FestiveBallRenderState;
import com.mmodding.extravaganza.client.init.ExtravaganzaModelLayers;
import com.mmodding.extravaganza.entity.FestiveBallEntity;
import com.mmodding.library.resource.api.client.model.SimpleEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public class FestiveBallEntityRenderer extends EntityRenderer<FestiveBallEntity, FestiveBallRenderState> {

	private final EntityModel<FestiveBallRenderState> model;

	public FestiveBallEntityRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new SimpleEntityModel<>(ExtravaganzaModelLayers.FESTIVE_BALL, context);
	}

	public Identifier getTexture(FestiveBallRenderState state) {
		return Extravaganza.createId("textures/entity/festive_ball/" + state.color.getSerializedName() + ".png");
	}

	@Override
	public FestiveBallRenderState createRenderState() {
		return new FestiveBallRenderState();
	}

	@Override
	public void extractRenderState(FestiveBallEntity entity, FestiveBallRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.color = entity.getColor();
	}

	@Override
	public void submit(FestiveBallRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		if (state.ageInTicks >= 2 || !(camera.pos.distanceTo(new Vec3(state.x, state.y, state.z)) < 12.25f)) {
			poseStack.pushPose();
			poseStack.translate(0.0f, -1.1f, 0.0f);
			poseStack.scale(0.8f, 0.8f, 0.8f);
			submitNodeCollector.submitModel(this.model, state, poseStack, this.getTexture(state), state.lightCoords, 0, state.outlineColor, null);
			poseStack.popPose();
		}
	}
}
