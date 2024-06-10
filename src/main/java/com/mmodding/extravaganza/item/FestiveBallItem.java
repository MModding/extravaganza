package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.entity.FestiveBallEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class FestiveBallItem extends Item {

	private final Supplier<EntityType<? extends FestiveBallEntity>> typeGetter;

	public FestiveBallItem(Supplier<EntityType<? extends FestiveBallEntity>> typeGetter, Settings settings) {
		super(settings);
		this.typeGetter = typeGetter;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		FestiveBallEntity ball = new FestiveBallEntity(this, this.typeGetter.get(), world, user);
		world.spawnEntity(ball);
		if (!user.getAbilities().creativeMode) {
			user.setStackInHand(hand, new ItemStack(user.getStackInHand(hand).getItem(), user.getStackInHand(hand).getCount() - 1));
		}
		return TypedActionResult.success(user.getStackInHand(hand));
	}
}
