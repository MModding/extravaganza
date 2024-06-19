package com.mmodding.extravaganza.client.entity.renderer;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.client.entity.model.MerryGoRoundEntityModel;
import com.mmodding.extravaganza.client.init.ExtravaganzaModelLayers;
import com.mmodding.extravaganza.entity.MerryGoRoundEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class MerryGoRoundEntityRenderer extends EntityRenderer<MerryGoRoundEntity> {

	private final MerryGoRoundEntityModel model;

	public MerryGoRoundEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		this.model = new MerryGoRoundEntityModel(ctx.getPart(ExtravaganzaModelLayers.TURNSTILE));
	}

	@Override
	public Identifier getTexture(MerryGoRoundEntity entity) {
		return Extravaganza.createId("textures/entity/merry_go_round.png");
	}

	@Override
	public void render(MerryGoRoundEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - yaw));
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0f));
		matrices.translate(0.0, -1.5, 0.0);
		this.model.render(matrices, vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(entity))), light, OverlayTexture.DEFAULT_UV, 654311423);
		matrices.pop();
	}
}
