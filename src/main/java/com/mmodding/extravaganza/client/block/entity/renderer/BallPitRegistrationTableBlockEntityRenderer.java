package com.mmodding.extravaganza.client.block.entity.renderer;

import com.mmodding.extravaganza.block.entity.BallPitRegistrationTableBlockEntity;
import com.mmodding.extravaganza.client.block.entity.state.BallPitRegistrationTableRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class BallPitRegistrationTableBlockEntityRenderer implements BlockEntityRenderer<BallPitRegistrationTableBlockEntity, BallPitRegistrationTableRenderState> {

	@Override
	public BallPitRegistrationTableRenderState createRenderState() {
		return new BallPitRegistrationTableRenderState();
	}

	@Override
	public void extractRenderState(BallPitRegistrationTableBlockEntity blockEntity, BallPitRegistrationTableRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
		BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
		state.scannedEnd = blockEntity.getScannedStart();
		state.scannedStart = blockEntity.getScannedEnd();
	}

	@Override
	public void submit(BallPitRegistrationTableRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
		poseStack.pushPose();
		Gizmos.cuboid(
			new AABB(
				state.scannedStart.getX(), state.scannedStart.getY(), state.scannedStart.getZ(),
				state.scannedEnd.getX(), state.scannedEnd.getY(), state.scannedEnd.getZ()
			).move(state.blockPos),
			GizmoStyle.stroke(ARGB.colorFromFloat(1.0F, 0.9F, 0.9F, 0.9F)),
			true
		);
		poseStack.popPose();
	}

	@Override
	public boolean shouldRenderOffScreen() {
		return true;
	}

	@Override
	public int getViewDistance() {
		return 96;
	}
}
