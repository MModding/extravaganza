package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.block.entity.BallPitRegistrationTableBlockEntity;
import com.mmodding.extravaganza.block.entity.CottonCandyMachineBlockEntity;
import com.mmodding.library.core.api.AdvancedContainer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ExtravaganzaBlockEntities {

	public static final BlockEntityType<BallPitRegistrationTableBlockEntity> BALL_PIT_REGISTRATION_TABLE = FabricBlockEntityTypeBuilder.create(
		BallPitRegistrationTableBlockEntity::new,
		ExtravaganzaBlocks.BALL_PIT_REGISTRATION_TABLE
	).build();

	public static final BlockEntityType<CottonCandyMachineBlockEntity> COTTON_CANDY_MACHINE = FabricBlockEntityTypeBuilder.create(
		CottonCandyMachineBlockEntity::new,
		ExtravaganzaBlocks.COTTON_CANDY_MACHINE
	).build();

	public static void register(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, "ball_pit_registration_table", BALL_PIT_REGISTRATION_TABLE);
		mod.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, "cotton_candy_machine", COTTON_CANDY_MACHINE);
	}
}
