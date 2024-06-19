package com.mmodding.extravaganza.client.entity.model;

import com.mmodding.extravaganza.entity.MerryGoRoundEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

// Originally made with Blockbench 4.10.3
@SuppressWarnings("unused")
public class MerryGoRoundEntityModel extends EntityModel<MerryGoRoundEntity> {

	private final ModelPart root;
	private final ModelPart down;
	private final ModelPart top;

	public MerryGoRoundEntityModel(ModelPart root) {
		this.root = root;
		this.down = root.getChild("down");
		this.top = root.getChild("top");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData down = modelPartData.addChild("down", ModelPartBuilder.create().uv(0, 54).cuboid(-4.0f, -7.0f, -6.0f, 16.0f, 2.0f, 16.0f, new Dilation(0.0f))
			.uv(0, 33).cuboid(-5.0f, -5.0f, -7.0f, 18.0f, 3.0f, 18.0f, new Dilation(0.0f)), ModelTransform.pivot(-4.0f, 26.0f, -2.0f));

		ModelPartData top = modelPartData.addChild("top", ModelPartBuilder.create().uv(0, 0).cuboid(0.0f, 0.0f, -22.0f, 28.0f, 5.0f, 28.0f, new Dilation(0.0f))
			.uv(48, 54).cuboid(0.0f, -14.0f, -8.0f, 28.0f, 14.0f, 0.0f, new Dilation(0.0f))
			.uv(77, 81).cuboid(15.0f, -2.0f, -20.0f, 11.0f, 2.0f, 11.0f, new Dilation(0.0f))
			.uv(33, 81).cuboid(15.0f, -2.0f, -7.0f, 11.0f, 2.0f, 11.0f, new Dilation(0.0f))
			.uv(0, 72).cuboid(2.0f, -2.0f, -20.0f, 11.0f, 2.0f, 11.0f, new Dilation(0.0f))
			.uv(53, 68).cuboid(2.0f, -2.0f, -7.0f, 11.0f, 2.0f, 11.0f, new Dilation(0.0f)), ModelTransform.pivot(-14.0f, 14.0f, 8.0f));

		ModelPartData cube_r1 = top.addChild("cube_r1", ModelPartBuilder.create().uv(54, 33).cuboid(-14.0f, -12.0f, 0.0f, 28.0f, 14.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(14.0f, -2.0f, -8.0f, 0.0f, 1.5708f, 0.0f));

		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(MerryGoRoundEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		this.root.render(matrices, vertices, light, overlay, color);
	}
}
