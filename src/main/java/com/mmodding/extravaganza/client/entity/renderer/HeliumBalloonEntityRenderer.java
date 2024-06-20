package com.mmodding.extravaganza.client.entity.renderer;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.client.entity.model.HeliumBalloonEntityModel;
import com.mmodding.extravaganza.client.init.ExtravaganzaModelLayers;
import com.mmodding.extravaganza.entity.HeliumBalloonEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class HeliumBalloonEntityRenderer extends EntityRenderer<HeliumBalloonEntity> {

	private final HeliumBalloonEntityModel model;

	public HeliumBalloonEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		this.model = new HeliumBalloonEntityModel(ctx.getPart(ExtravaganzaModelLayers.HELIUM_BALLOON));
	}

	@Override
	public Identifier getTexture(HeliumBalloonEntity entity) {
		return Extravaganza.createId("textures/entity/helium_balloon/" + entity.getVariant() + ".png");
	}

	@Override
	public void render(HeliumBalloonEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0f));
		matrices.translate(0.0, -1.3, 0.0);
		this.model.render(matrices, vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(entity))), light, OverlayTexture.DEFAULT_UV, 654311423);
		matrices.pop();
	}
}
