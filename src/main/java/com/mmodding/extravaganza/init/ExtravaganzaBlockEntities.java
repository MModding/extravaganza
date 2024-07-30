package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.block.entity.BallPitRegistrationTableBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.Set;

public class ExtravaganzaBlockEntities {

	public static final BlockEntityType<BallPitRegistrationTableBlockEntity> BALL_PIT_REGISTRATION_TABLE = new BlockEntityType<>(
		BallPitRegistrationTableBlockEntity::new,
		Set.of(ExtravaganzaBlocks.BALL_PIT_REGISTRATION_TABLE),
		null
	);

	public static void register() {
		Registry.register(Registries.BLOCK_ENTITY_TYPE, Extravaganza.createId("ball_pit_registration_table"), ExtravaganzaBlockEntities.BALL_PIT_REGISTRATION_TABLE);
	}
}
