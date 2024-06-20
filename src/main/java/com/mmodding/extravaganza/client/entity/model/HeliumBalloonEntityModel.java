// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.mmodding.extravaganza.client.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class HeliumBalloonEntityModel extends EntityModel<Entity> {

	private final ModelPart all;
	private final ModelPart rope;
	private final ModelPart rope2;
	private final ModelPart rope3;
	private final ModelPart gro;

	public HeliumBalloonEntityModel(ModelPart root) {
		this.all = root.getChild("all");
		this.rope = this.all.getChild("rope");
		this.rope2 = this.rope.getChild("rope2");
		this.rope3 = this.rope2.getChild("rope3");
		this.gro = this.all.getChild("gro");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData all = modelPartData.addChild("all", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 22.0f, 0.0f));

		ModelPartData rope = all.addChild("rope", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0f, 0.0f, 0.0f, 2.0f, 4.0f, 0.0f, new Dilation(0.0f)), ModelTransform.pivot(0.5f, -12.0f, 0.0f));

		ModelPartData rope2 = rope.addChild("rope2", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0f, 0.0f, 0.0f, 2.0f, 5.0f, 0.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 4.0f, 0.0f));

		ModelPartData rope3 = rope2.addChild("rope3", ModelPartBuilder.create().uv(4, 0).cuboid(-1.0f, 1.0f, 0.0f, 2.0f, 5.0f, 0.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 4.0f, 0.0f));

		ModelPartData gro = all.addChild("gro", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, -12.0f, 0.0f));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		this.all.render(matrices, vertices, light, overlay, color);
	}
}
