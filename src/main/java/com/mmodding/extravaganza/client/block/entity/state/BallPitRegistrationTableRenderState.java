package com.mmodding.extravaganza.client.block.entity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.BlockPos;

public class BallPitRegistrationTableRenderState extends BlockEntityRenderState {

	public BlockPos scannedStart = BlockPos.ZERO;
	public BlockPos scannedEnd = BlockPos.ZERO;
}
