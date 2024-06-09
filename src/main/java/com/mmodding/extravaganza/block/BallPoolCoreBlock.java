package com.mmodding.extravaganza.block;

import com.mmodding.extravaganza.block.entity.BallPoolCoreBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

// This block will manage the full Ball Pool, including changing the power of the velocity in example.
public class BallPoolCoreBlock extends BlockWithEntity {

	public static final MapCodec<BallPoolCoreBlock> CODEC = BallPoolCoreBlock.createCodec(BallPoolCoreBlock::new);

	public BallPoolCoreBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return BallPoolCoreBlock.CODEC;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new BallPoolCoreBlockEntity(pos, state);
	}
}
