package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.block.entity.BallPitRegistrationTableBlockEntity;
import com.mmodding.extravaganza.block.entity.CottonCandyMachineBlockEntity;
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

	public static final BlockEntityType<CottonCandyMachineBlockEntity> COTTON_CANDY_MACHINE = new BlockEntityType<>(
		CottonCandyMachineBlockEntity::new,
		Set.of(ExtravaganzaBlocks.COTTON_CANDY_MACHINE),
		null
	);

	public static void register() {
		Registry.register(Registries.BLOCK_ENTITY_TYPE, Extravaganza.createId("ball_pit_registration_table"), ExtravaganzaBlockEntities.BALL_PIT_REGISTRATION_TABLE);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, Extravaganza.createId("cotton_candy_machine"), ExtravaganzaBlockEntities.COTTON_CANDY_MACHINE);
	}
}
