package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.block.entity.BallPoolCoreBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.Set;

public class ExtravaganzaBlockEntities {

	public static final BlockEntityType<BallPoolCoreBlockEntity> BALL_POOL_CORE = new BlockEntityType<>(
		BallPoolCoreBlockEntity::new,
		Set.of(ExtravaganzaBlocks.BALL_POOL_INSCRIPTION_TABLE),
		null
	);

	public static void register() {
		Registry.register(Registries.BLOCK_ENTITY_TYPE, Extravaganza.createId("ball_pool_core"), ExtravaganzaBlockEntities.BALL_POOL_CORE);
	}
}
