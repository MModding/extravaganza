package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.entity.FestiveBallEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class FestiveBallItem extends Item {

	private final ExtravaganzaColor color;

	public FestiveBallItem(ExtravaganzaColor color, Settings settings) {
		super(settings);
		this.color = color;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		FestiveBallEntity ball = this.color.createBallEntity(world, user);
		world.spawnEntity(ball);
		if (!user.getAbilities().creativeMode) {
			user.setStackInHand(hand, new ItemStack(user.getStackInHand(hand).getItem(), user.getStackInHand(hand).getCount() - 1));
		}
		return TypedActionResult.success(user.getStackInHand(hand));
	}
}
