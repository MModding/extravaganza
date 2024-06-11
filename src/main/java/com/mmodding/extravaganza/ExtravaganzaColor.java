package com.mmodding.extravaganza;

import com.mmodding.extravaganza.entity.FestiveBallEntity;
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

	public ItemStack createBallStack() {
		return Registries.ITEM.get(Extravaganza.createId(this.asString() + "_festive_ball")).getDefaultStack();
	}

	public FestiveBallEntity createBallEntity(World world, Entity owner) {
		return new FestiveBallEntity(this, world, owner);
	}
}
