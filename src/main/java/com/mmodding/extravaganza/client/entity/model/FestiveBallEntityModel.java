package com.mmodding.extravaganza.client.entity.model;

import com.mmodding.extravaganza.entity.FestiveBallEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

// Originally made with Blockbench 4.10.1
public class FestiveBallEntityModel extends EntityModel<FestiveBallEntity> {

	private final ModelPart ball;

	public FestiveBallEntityModel(ModelPart root) {
		this.ball = root.getChild("ball");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData ball = modelPartData.addChild("ball", ModelPartBuilder.create().uv(-6, 0).cuboid(-3.0f, 3.0f, -3.0f, 6.0f, 0.0f, 6.0f, new Dilation(0.0f))
			.uv(-6, 0).cuboid(-3.0f, -3.0f, -3.0f, 6.0f, 0.0f, 6.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 24.0f, 0.0f));
		ball.addChild("cube_r1", ModelPartBuilder.create().uv(-6, 0).cuboid(-3.0f, 0.0f, -3.0f, 6.0f, 0.0f, 6.0f, new Dilation(0.0f)), ModelTransform.of(-3.0f, 0.0f, 0.0f, -3.1416f, 1.5708f, 1.5708f));
		ball.addChild("cube_r2", ModelPartBuilder.create().uv(-6, 0).cuboid(-3.0f, 0.0f, -3.0f, 6.0f, 0.0f, 6.0f, new Dilation(0.0f)), ModelTransform.of(3.0f, 0.0f, 0.0f, 1.5708f, -1.5708f, 0.0f));
		ball.addChild("cube_r3", ModelPartBuilder.create().uv(-6, 0).cuboid(-3.0f, 0.0f, -3.0f, 6.0f, 0.0f, 6.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, -3.0f, 1.5708f, 0.0f, 0.0f));
		ball.addChild("cube_r4", ModelPartBuilder.create().uv(-6, 0).cuboid(-3.0f, 0.0f, -3.0f, 6.0f, 0.0f, 6.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 3.0f, 1.5708f, 0.0f, 1.5708f));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(FestiveBallEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		this.ball.render(matrices, vertices, light, overlay, color);
	}
}
