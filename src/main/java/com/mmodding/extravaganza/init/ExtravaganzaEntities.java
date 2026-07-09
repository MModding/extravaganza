package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.entity.FestiveBallEntity;
import com.mmodding.extravaganza.entity.HeliumBalloonEntity;
import com.mmodding.extravaganza.entity.MerryGoRoundEntity;
import com.mmodding.library.core.api.AdvancedContainer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ExtravaganzaEntities {

	public static final EntityType<FestiveBallEntity> FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>of(FestiveBallEntity::new, MobCategory.MISC)
		.sized(0.3f, 0.3f)
		.fireImmune()
		.clientTrackingRange(10)
		.build(Extravaganza.createKey(Registries.ENTITY_TYPE, "festive_ball"));

	public static final EntityType<HeliumBalloonEntity> HELIUM_BALLOON = EntityType.Builder.<HeliumBalloonEntity>of(HeliumBalloonEntity::new, MobCategory.MISC)
		.sized(0.3f, 0.8f)
		.fireImmune()
		.clientTrackingRange(10)
		.build(Extravaganza.createKey(Registries.ENTITY_TYPE, "helium_balloon"));

	public static final EntityType<MerryGoRoundEntity> MERRY_GO_ROUND = EntityType.Builder.<MerryGoRoundEntity>of(MerryGoRoundEntity::new, MobCategory.MISC)
		.sized(1.5f, 0.75f)
		.fireImmune()
		.clientTrackingRange(10)
		.build(Extravaganza.createKey(Registries.ENTITY_TYPE, "merry_go_round"));

	public static void register(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.ENTITY_TYPE, factory -> {
			factory.register("festive_ball", FESTIVE_BALL);
			factory.register("helium_ballon", HELIUM_BALLOON);
			factory.register("merry_go_round", MERRY_GO_ROUND);
		});
	}
}
