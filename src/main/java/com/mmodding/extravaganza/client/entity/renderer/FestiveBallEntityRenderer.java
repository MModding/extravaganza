package com.mmodding.extravaganza.client.entity.renderer;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.client.entity.model.FestiveBallEntityModel;
import com.mmodding.extravaganza.client.init.ExtravaganzaModelLayers;
import com.mmodding.extravaganza.entity.FestiveBallEntity;
import com.mmodding.extravaganza.init.ExtravaganzaEntities;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class FestiveBallEntityRenderer extends EntityRenderer<FestiveBallEntity> {

	private final FestiveBallEntityModel model;

	public FestiveBallEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		this.model = new FestiveBallEntityModel(ctx.getPart(ExtravaganzaModelLayers.FESTIVE_BALL));
	}

	@Override
	public Identifier getTexture(FestiveBallEntity entity) {
		Function<String, Identifier> factory = color -> Extravaganza.createId("textures/entity/festive_ball/" + color + ".png");
		// fuck you java for not allowing me to do a switch
		if (entity.getType().equals(ExtravaganzaEntities.BLACK_FESTIVE_BALL)) {
			return factory.apply("black");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.BLUE_FESTIVE_BALL)) {
			return factory.apply("blue");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.BROWN_FESTIVE_BALL)) {
			return factory.apply("brown");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.CYAN_FESTIVE_BALL)) {
			return factory.apply("cyan");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.GRAY_FESTIVE_BALL)) {
			return factory.apply("gray");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.GREEN_FESTIVE_BALL)) {
			return factory.apply("green");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.LIGHT_BLUE_FESTIVE_BALL)) {
			return factory.apply("light_blue");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.LIGHT_GRAY_FESTIVE_BALL)) {
			return factory.apply("light_gray");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.LIME_FESTIVE_BALL)) {
			return factory.apply("lime");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.MAGENTA_FESTIVE_BALL)) {
			return factory.apply("magenta");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.ORANGE_FESTIVE_BALL)) {
			return factory.apply("orange");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.PINK_FESTIVE_BALL)) {
			return factory.apply("pink");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.PURPLE_FESTIVE_BALL)) {
			return factory.apply("purple");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.RED_FESTIVE_BALL)) {
			return factory.apply("red");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.WHITE_FESTIVE_BALL)) {
			return factory.apply("white");
		}
		else if (entity.getType().equals(ExtravaganzaEntities.YELLOW_FESTIVE_BALL)) {
			return factory.apply("yellow");
		}
		else {
			return null;
		}
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
