package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.block.BallPoolContentBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ExtravaganzaBlocks {

	public static final Block BALL_POOL_CONTENT = new BallPoolContentBlock(AbstractBlock.Settings.create().noCollision());

	public static void register() {
		Registry.register(Registries.BLOCK, Extravaganza.createId("ball_pool_content"), ExtravaganzaBlocks.BALL_POOL_CONTENT);
	}
}
