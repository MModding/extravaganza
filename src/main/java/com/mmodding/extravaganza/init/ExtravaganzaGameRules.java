package com.mmodding.extravaganza.init;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ExtravaganzaGameRules {

	public static final GameRules.Key<GameRules.IntRule> FESTIVE_BALL_AGE_PERCENTAGE_BEFORE_PICKING = new GameRules.Key<>("festiveBallAgePercentageBeforePicking", GameRules.Category.MOBS);
	public static final GameRules.Key<GameRules.BooleanRule> PLAYERS_INTO_TRASH = new GameRules.Key<>("playersIntoTrash", GameRules.Category.PLAYER);

	public static void register() {
		GameRuleRegistry.register("festiveBallAgePercentageBeforePicking", GameRules.Category.MOBS, GameRuleFactory.createIntRule(50, 0, 100));
		GameRuleRegistry.register("playersIntoTrash", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false));
	}
}
