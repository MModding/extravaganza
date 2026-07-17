package com.mmodding.extravaganza.init;

import com.mmodding.library.core.api.AdvancedContainer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class ExtravaganzaGameRules {

	public static final GameRule<Integer> FESTIVE_BALL_AGE_PERCENTAGE_BEFORE_PICKING = GameRuleBuilder.forInteger(50)
		.range(0, 100)
		.category(GameRuleCategory.MOBS)
		.build();

	public static final GameRule<Boolean> PLAYERS_INTO_TRASH = GameRuleBuilder.forBoolean(false)
		.category(GameRuleCategory.PLAYER)
		.build();

	public static void register(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.GAME_RULE, factory -> {
			factory.register("festive_ball_age_percentage_before_picking", FESTIVE_BALL_AGE_PERCENTAGE_BEFORE_PICKING);
			factory.register("players_into_trash", PLAYERS_INTO_TRASH);
		});
	}
}
