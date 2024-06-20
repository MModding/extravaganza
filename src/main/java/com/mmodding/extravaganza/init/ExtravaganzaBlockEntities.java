package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.block.entity.BallPoolInscriptionTableBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.Set;

public class ExtravaganzaBlockEntities {

	public static final BlockEntityType<BallPoolInscriptionTableBlockEntity> BALL_POOl_INSCRIPTION_TABLE = new BlockEntityType<>(
		BallPoolInscriptionTableBlockEntity::new,
		Set.of(ExtravaganzaBlocks.BALL_POOL_INSCRIPTION_TABLE),
		null
	);

	public static void register() {
		Registry.register(Registries.BLOCK_ENTITY_TYPE, Extravaganza.createId("ball_pool_inscription_table"), ExtravaganzaBlockEntities.BALL_POOl_INSCRIPTION_TABLE);
	}
}
