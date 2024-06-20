package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.entity.FestiveBallEntity;
import com.mmodding.extravaganza.entity.HeliumBalloonEntity;
import com.mmodding.extravaganza.entity.MerryGoRoundEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ExtravaganzaEntities {

	public static final EntityType<FestiveBallEntity> FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.maxTrackingRange(10)
		.build();

	public static final EntityType<HeliumBalloonEntity> HELIUM_BALLOON = EntityType.Builder.<HeliumBalloonEntity>create(HeliumBalloonEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 1.3f)
		.makeFireImmune()
		.maxTrackingRange(10)
		.build();

	public static final EntityType<MerryGoRoundEntity> MERRY_GO_ROUND = EntityType.Builder.<MerryGoRoundEntity>create(MerryGoRoundEntity::new, SpawnGroup.MISC)
		.dimensions(1.5f, 0.75f)
		.makeFireImmune()
		.maxTrackingRange(10)
		.build();

	public static void register() {
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("festive_ball"), ExtravaganzaEntities.FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("helium_balloon"), ExtravaganzaEntities.HELIUM_BALLOON);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("merry_go_round"), ExtravaganzaEntities.MERRY_GO_ROUND);
	}
}
