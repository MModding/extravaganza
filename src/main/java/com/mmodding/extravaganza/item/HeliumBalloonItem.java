package com.mmodding.extravaganza.item;

import com.mmodding.extravaganza.entity.HeliumBalloonEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class HeliumBalloonItem extends Item {

	private final String variant;

	public HeliumBalloonItem(String variant, Settings settings) {
		super(settings);
		this.variant = variant;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		HitResult result = HeliumBalloonItem.raycast(world, user, RaycastContext.FluidHandling.ANY);
		if (result.getType() == HitResult.Type.MISS) {
			return TypedActionResult.pass(stack);
		}
		else {
			if (result.getType() == HitResult.Type.BLOCK) {
				HeliumBalloonEntity heliumBalloonEntity = this.createEntity(world, result, stack, user);
				if (!world.isSpaceEmpty(heliumBalloonEntity, heliumBalloonEntity.getBoundingBox())) {
					return TypedActionResult.fail(stack);
				}
				else {
					if (!world.isClient) {
						world.spawnEntity(heliumBalloonEntity);
						world.emitGameEvent(user, GameEvent.ENTITY_PLACE, result.getPos());
						stack.decrementUnlessCreative(1, user);
					}

					user.incrementStat(Stats.USED.getOrCreateStat(this));
					return TypedActionResult.success(stack, world.isClient());
				}
			}
			else {
				return TypedActionResult.pass(stack);
			}
		}
	}

	private HeliumBalloonEntity createEntity(World world, HitResult hitResult, ItemStack stack, PlayerEntity player) {
		Vec3d vec3d = hitResult.getPos();
		HeliumBalloonEntity heliumBalloonEntity = new HeliumBalloonEntity(world, vec3d.x, vec3d.y, vec3d.z, this.variant);
		if (world instanceof ServerWorld serverWorld) {
			EntityType.copier(serverWorld, stack, player).accept(heliumBalloonEntity);
		}
		return heliumBalloonEntity;
	}
}
