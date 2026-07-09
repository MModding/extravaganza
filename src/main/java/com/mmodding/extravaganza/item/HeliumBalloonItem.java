package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.entity.HeliumBalloonEntity;
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

public class HeliumBalloonItem extends Item {

	private final String variant;

	public HeliumBalloonItem(String variant, Properties properties) {
		super(properties);
		this.variant = variant;
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		HitResult result = HeliumBalloonItem.getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
		if (result.getType() == HitResult.Type.BLOCK) {
			HeliumBalloonEntity heliumBalloonEntity = this.createEntity(level, result, stack, player);
			if (heliumBalloonEntity == null || !level.noCollision(heliumBalloonEntity, heliumBalloonEntity.getBoundingBox())) {
				return InteractionResult.FAIL;
			}
			else {
				if (!level.isClientSide()) {
					level.addFreshEntity(heliumBalloonEntity);
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

	private HeliumBalloonEntity createEntity(Level level, HitResult hitResult, ItemStack stack, Player player) {
		HeliumBalloonEntity heliumBalloonEntity = ExtravaganzaEntities.HELIUM_BALLOON.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
		if (heliumBalloonEntity != null) {
			if (level instanceof ServerLevel serverLevel) {
				Vec3 location = hitResult.getLocation();
				heliumBalloonEntity.setPos(location.x, location.y, location.z);
				heliumBalloonEntity.getEntityData().set(HeliumBalloonEntity.VARIANT, this.variant);
				EntityType.createDefaultStackConfig(serverLevel, stack, player).apply(heliumBalloonEntity);
			}
		}
		return heliumBalloonEntity;
	}
}
