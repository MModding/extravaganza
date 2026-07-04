package com.mmodding.extravaganza;

import com.mmodding.extravaganza.entity.FestiveBallEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.MapColor;

import java.util.Arrays;
import java.util.List;

public enum ExtravaganzaColor implements StringRepresentable {

	BLACK, BLUE, BROWN, CYAN,
	GRAY, GREEN, LIGHT_BLUE, LIGHT_GRAY,
	LIME, MAGENTA, ORANGE, PINK,
	PURPLE, RED, WHITE, YELLOW,
	PLANT, TOMATO, TEAR, NYMPH; // ModFest Carnival Colors

	public static final List<ExtravaganzaColor> VALUES = Arrays.stream(ExtravaganzaColor.values()).toList();

	public static ExtravaganzaColor fromString(String identifier) {
		return ExtravaganzaColor.valueOf(identifier.toUpperCase());
	}

	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public MapColor getMapColor() {
		return switch (this) {
			case BLACK -> MapColor.COLOR_BLACK;
			case BLUE -> MapColor.COLOR_BLUE;
			case BROWN -> MapColor.COLOR_BROWN;
			case CYAN -> MapColor.COLOR_CYAN;
			case GRAY -> MapColor.COLOR_GRAY;
			case GREEN -> MapColor.COLOR_GREEN;
			case LIGHT_BLUE -> MapColor.COLOR_LIGHT_BLUE;
			case LIGHT_GRAY -> MapColor.COLOR_LIGHT_GRAY;
			case LIME -> MapColor.COLOR_LIGHT_GREEN;
			case MAGENTA -> MapColor.COLOR_MAGENTA;
			case ORANGE -> MapColor.COLOR_ORANGE;
			case PINK -> MapColor.COLOR_PINK;
			case PURPLE -> MapColor.COLOR_PURPLE;
			case RED -> MapColor.COLOR_RED;
			case WHITE -> MapColor.SNOW;
			case YELLOW -> MapColor.COLOR_YELLOW;
			case PLANT -> MapColor.GRASS;
			case TOMATO -> MapColor.CRIMSON_NYLIUM;
			case TEAR -> MapColor.WATER;
			case NYMPH -> MapColor.CRIMSON_STEM;
		};
	}

	public ItemStack createBallStack() {
		return BuiltInRegistries.ITEM.getValue(Extravaganza.createId(this.getSerializedName() + "_festive_ball")).getDefaultInstance();
	}

	public FestiveBallEntity createBallEntity(Level level, Entity owner) {
		return new FestiveBallEntity(this, level, owner);
	}
}
