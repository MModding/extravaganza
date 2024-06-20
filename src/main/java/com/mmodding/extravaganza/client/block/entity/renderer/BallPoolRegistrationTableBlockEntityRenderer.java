package com.mmodding.extravaganza.client.block.entity.renderer;

import com.mmodding.extravaganza.block.entity.BallPoolRegistrationTableBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class BallPoolRegistrationTableBlockEntityRenderer implements BlockEntityRenderer<BallPoolRegistrationTableBlockEntity> {

	@Override
	public void render(BallPoolRegistrationTableBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light, int overlay) {
		matrices.push();
		WorldRenderer.drawBox(matrices, vertices.getBuffer(RenderLayer.getLines()), entity.getFullScanned(), 0.9f, 0.9f, 0.9f, 1.0f);
		matrices.pop();
	}

	@Override
	public boolean rendersOutsideBoundingBox(BallPoolRegistrationTableBlockEntity blockEntity) {
		return true;
	}

	@Override
	public int getRenderDistance() {
		return 96;
	}
}
