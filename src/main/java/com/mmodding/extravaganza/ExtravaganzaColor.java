package com.mmodding.extravaganza;

import com.mmodding.extravaganza.entity.FestiveBallEntity;
import net.minecraft.block.MapColor;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public enum ExtravaganzaColor implements StringIdentifiable {

	BLACK, BLUE, BROWN, CYAN,
	GRAY, GREEN, LIGHT_BLUE, LIGHT_GRAY,
	LIME, MAGENTA, ORANGE, PINK,
	PURPLE, RED, WHITE, YELLOW,
	PLANT, TOMATO, TEAR, NYMPH; // ModFest Carnival Colors

	public static final List<ExtravaganzaColor> VALUES = Arrays.stream(ExtravaganzaColor.values()).toList();

	public static ExtravaganzaColor fromString(@NotNull String identifier) {
		return ExtravaganzaColor.valueOf(identifier.toUpperCase());
	}

	@Override
	public String asString() {
		return this.name().toLowerCase();
	}

	public MapColor getMapColor() {
		return switch (this) {
			case BLACK -> MapColor.BLACK;
			case BLUE -> MapColor.BLUE;
			case BROWN -> MapColor.BROWN;
			case CYAN -> MapColor.CYAN;
			case GRAY -> MapColor.GRAY;
			case GREEN -> MapColor.GREEN;
			case LIGHT_BLUE -> MapColor.LIGHT_BLUE;
			case LIGHT_GRAY -> MapColor.LIGHT_GRAY;
			case LIME -> MapColor.LIME;
			case MAGENTA -> MapColor.MAGENTA;
			case ORANGE -> MapColor.ORANGE;
			case PINK -> MapColor.PINK;
			case PURPLE -> MapColor.PURPLE;
			case RED -> MapColor.RED;
			case WHITE -> MapColor.WHITE;
			case YELLOW -> MapColor.YELLOW;
			case PLANT -> MapColor.PALE_GREEN;
			case TOMATO -> MapColor.DULL_RED;
			case TEAR -> MapColor.WATER_BLUE;
			case NYMPH -> MapColor.DULL_PINK;
		};
	}

	public ItemStack createBallStack() {
		return Registries.ITEM.get(Extravaganza.createId(this.asString() + "_festive_ball")).getDefaultStack();
	}

	public FestiveBallEntity createBallEntity(World world, Entity owner) {
		return new FestiveBallEntity(this, world, owner);
	}
}
