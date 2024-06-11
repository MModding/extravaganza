package com.mmodding.extravaganza.client.entity.renderer;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.client.entity.model.FestiveBallEntityModel;
import com.mmodding.extravaganza.client.init.ExtravaganzaModelLayers;
import com.mmodding.extravaganza.entity.FestiveBallEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class FestiveBallEntityRenderer extends EntityRenderer<FestiveBallEntity> {

	private final FestiveBallEntityModel model;

	public FestiveBallEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		this.model = new FestiveBallEntityModel(ctx.getPart(ExtravaganzaModelLayers.FESTIVE_BALL));
	}

	@Override
	public Identifier getTexture(FestiveBallEntity entity) {
		return Extravaganza.createId("textures/entity/festive_ball/" + entity.getColor().asString() + ".png");
	}

	@Override
	public void render(FestiveBallEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
		matrices.translate(0.0f, -1.1f, 0.0f);
		matrices.scale(0.8f, 0.8f, 0.8f);
		this.model.render(matrices, vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(entity))), light, OverlayTexture.DEFAULT_UV, 654311423);
		matrices.pop();
	}
}
