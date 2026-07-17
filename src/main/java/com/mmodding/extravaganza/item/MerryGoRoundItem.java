package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.entity.MerryGoRoundEntity;
import com.mmodding.extravaganza.init.ExtravaganzaEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class MerryGoRoundItem extends Item {

	public MerryGoRoundItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		HitResult result = MerryGoRoundItem.getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
		if (result.getType() == HitResult.Type.MISS) {
			return InteractionResult.PASS;
		}
		else {
			if (result.getType() == HitResult.Type.BLOCK) {
				MerryGoRoundEntity merryGoRoundEntity = this.createEntity(level, result, stack, player);
				if (merryGoRoundEntity == null || !level.noCollision(merryGoRoundEntity, merryGoRoundEntity.getBoundingBox())) {
					return InteractionResult.FAIL;
				}
				else {
					if (!level.isClientSide()) {
						level.addFreshEntity(merryGoRoundEntity);
						level.gameEvent(player, GameEvent.ENTITY_PLACE, result.getLocation());
						stack.consume(1, player);
					}

					player.awardStat(Stats.ITEM_USED.get(this));
					return InteractionResult.SUCCESS;
				}
			}
			else {
				return InteractionResult.PASS;
			}
		}
	}

	private MerryGoRoundEntity createEntity(Level level, HitResult hitResult, ItemStack stack, Player player) {
		MerryGoRoundEntity merryGoRoundEntity = ExtravaganzaEntities.MERRY_GO_ROUND.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
		if (merryGoRoundEntity != null) {
			Vec3 location = hitResult.getLocation();
			merryGoRoundEntity.setPos(location.x, location.y, location.z);
			if (level instanceof ServerLevel serverLevel) {
				EntityType.createDefaultStackConfig(serverLevel, stack, player).apply(merryGoRoundEntity);
			}
		}
		return merryGoRoundEntity;
	}
}
