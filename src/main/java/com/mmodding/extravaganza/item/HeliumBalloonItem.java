package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.entity.HeliumBalloonEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
		if (result.getType() == HitResult.Type.MISS) {
			return InteractionResult.PASS;
		}
		else {
			if (result.getType() == HitResult.Type.BLOCK) {
				HeliumBalloonEntity heliumBalloonEntity = this.createEntity(level, result, stack, player);
				if (!level.noCollision(heliumBalloonEntity, heliumBalloonEntity.getBoundingBox())) {
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
	}

	private HeliumBalloonEntity createEntity(Level level, HitResult hitResult, ItemStack stack, Player player) {
		Vec3 vec3d = hitResult.getLocation();
		HeliumBalloonEntity heliumBalloonEntity = new HeliumBalloonEntity(level, vec3d.x, vec3d.y, vec3d.z, this.variant);
		if (level instanceof ServerLevel serverLevel) {
			EntityType.createDefaultStackConfig(serverLevel, stack, player).apply(heliumBalloonEntity);
		}
		return heliumBalloonEntity;
	}
}
