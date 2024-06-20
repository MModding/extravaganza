package com.mmodding.extravaganza.client.block.entity.renderer;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.block.entity.BallPoolInscriptionTableBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;

public class BallPoolInscriptionTableBlockEntityRenderer implements BlockEntityRenderer<BallPoolInscriptionTableBlockEntity> {

	@Override
	public void render(BallPoolInscriptionTableBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light, int overlay) {
		matrices.push();
		Box box = entity.getFullScanned().expand(1.0, 1.0, 1.0);
		WorldRenderer.drawBox(matrices, vertices.getBuffer(RenderLayer.getLines()), box, 0.9f, 0.9f, 0.9f, 1.0f);
		Extravaganza.getLogger().info(box.toString());
		matrices.pop();
	}

	@Override
	public boolean rendersOutsideBoundingBox(BallPoolInscriptionTableBlockEntity blockEntity) {
		return true;
	}

	@Override
	public int getRenderDistance() {
		return 96;
	}
}
