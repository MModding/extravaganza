package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.entity.FestiveBallEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class FestiveBallItem extends Item implements ProjectileItem {

	private final ExtravaganzaColor color;

	public FestiveBallItem(ExtravaganzaColor color, Properties properties) {
		super(properties);
		this.color = color;
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		FestiveBallEntity ball = this.color.createBallEntity(level, player);
		level.addFreshEntity(ball);
		if (!player.getAbilities().instabuild) {
			player.setItemInHand(hand, new ItemStack(player.getItemInHand(hand).getItem(), player.getItemInHand(hand).getCount() - 1));
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
		FestiveBallEntity ball = this.color.createDispensedBlockEntity(level, direction);
		ball.setPos(position.x(), position.y(), position.z());
		return ball;
	}
}
