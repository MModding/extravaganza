package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.entity.FestiveBallEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ExtravaganzaEntities {

	public static final EntityType<FestiveBallEntity> FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static void register() {
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("festive_ball"), ExtravaganzaEntities.FESTIVE_BALL);
	}
}
