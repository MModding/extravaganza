package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.entity.FestiveBallEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ExtravaganzaEntities {

	public static final EntityType<FestiveBallEntity> BLACK_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> BLUE_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> BROWN_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> CYAN_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> GRAY_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> GREEN_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> LIGHT_BLUE_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> LIGHT_GRAY_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> LIME_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> MAGENTA_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> ORANGE_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> PINK_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> PURPLE_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> RED_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> WHITE_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static final EntityType<FestiveBallEntity> YELLOW_FESTIVE_BALL = EntityType.Builder.<FestiveBallEntity>create(FestiveBallEntity::new, SpawnGroup.MISC)
		.dimensions(0.3f, 0.3f)
		.makeFireImmune()
		.build();

	public static void register() {
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("black_festive_ball"), ExtravaganzaEntities.BLACK_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("blue_festive_ball"), ExtravaganzaEntities.BLUE_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("brown_festive_ball"), ExtravaganzaEntities.BROWN_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("cyan_festive_ball"), ExtravaganzaEntities.CYAN_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("gray_festive_ball"), ExtravaganzaEntities.GRAY_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("green_festive_ball"), ExtravaganzaEntities.GREEN_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("light_blue_festive_ball"), ExtravaganzaEntities.LIGHT_BLUE_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("light_gray_festive_ball"), ExtravaganzaEntities.LIGHT_GRAY_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("lime_festive_ball"), ExtravaganzaEntities.LIME_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("magenta_festive_ball"), ExtravaganzaEntities.MAGENTA_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("orange_festive_ball"), ExtravaganzaEntities.ORANGE_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("pink_festive_ball"), ExtravaganzaEntities.PINK_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("purple_festive_ball"), ExtravaganzaEntities.PURPLE_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("red_festive_ball"), ExtravaganzaEntities.RED_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("white_festive_ball"), ExtravaganzaEntities.WHITE_FESTIVE_BALL);
		Registry.register(Registries.ENTITY_TYPE, Extravaganza.createId("yellow_festive_ball"), ExtravaganzaEntities.YELLOW_FESTIVE_BALL);
	}
}
