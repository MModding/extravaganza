package com.mmodding.extravaganza.entity;

import com.mmodding.extravaganza.init.ExtravaganzaGameRules;
import com.mmodding.extravaganza.init.ExtravaganzaParticles;
import com.mmodding.extravaganza.item.FestiveBallItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

public class FestiveBallEntity extends ThrownEntity {

	private Item source = Items.AIR;

	public FestiveBallEntity(EntityType<? extends FestiveBallEntity> type, World world) {
		super(type, world);
	}

	public FestiveBallEntity(FestiveBallItem source, EntityType<? extends FestiveBallEntity> type, World world, Entity owner) {
		this(type, world);
		this.source = source;
		this.setOwner(owner);
		this.setPosition(owner.getX(), owner.getEyeY() - 0.1f, owner.getZ());
		this.setVelocity(
			owner.getRotationVec(1).getX(),
			owner.getRotationVec(1).getY(),
			owner.getRotationVec(1).getZ(),
			1.0f,
			0
		);
		this.setSilent(true);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		Identifier identifier = Identifier.tryParse(nbt.getString("source"));
		assert identifier != null;
		this.source = Registries.ITEM.get(identifier);
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.putString("source", Registries.ITEM.getId(this.source).toString());
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		Vec3d velocity = this.getVelocity();
		switch (blockHitResult.getSide()) {
			case WEST, EAST -> this.setVelocity(velocity.x * -0.8, velocity.y, velocity.z);
			case UP, DOWN -> this.setVelocity(velocity.x, velocity.y * -0.8, velocity.z);
			case NORTH, SOUTH -> this.setVelocity(velocity.x, velocity.y, velocity.z * -0.8);
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		this.manageVelocityForEntity(this.getVelocity(), () -> {
			this.discard();
			if (entityHitResult.getEntity() instanceof PlayerEntity player) {
				player.getInventory().insertStack(this.source.getDefaultStack());
			} else if (entityHitResult.getEntity() instanceof InventoryOwner owner) {
				owner.getInventory().addStack(this.source.getDefaultStack());
			}
		});
	}

	@Override
	protected void onDeflected(@Nullable Entity deflector, boolean fromAttack) {
		AtomicBoolean bool = new AtomicBoolean();
		this.manageVelocityForEntity(this.getVelocity(), () -> {
			this.discard();
			if (deflector != null) {
				if (deflector instanceof PlayerEntity player) {
					player.getInventory().insertStack(this.source.getDefaultStack());
				}
				else if (deflector instanceof InventoryOwner owner) {
					owner.getInventory().addStack(this.source.getDefaultStack());
				}
				else {
					this.discard();
					this.dropItem(this.source);
				}
			}
			else {
				this.discard();
				this.dropItem(this.source);
			}
			bool.set(true);
		});
		if (!bool.get()) {
			this.setVelocity(
				this.getVelocity().getX() * 1.5,
				this.getVelocity().getY() * 1.5,
				this.getVelocity().getZ() * 1.5
			);
		}
	}

	private double extractDecimalPart(double value) {
		BigDecimal decimal = new BigDecimal(String.valueOf(value));
		return decimal.subtract(new BigDecimal(decimal.intValue())).doubleValue();
	}

	private boolean collidable(Vec3d position) {
		return !this.getWorld()
			.getBlockState(BlockPos.ofFloored(position))
			.getCollisionShape(this.getWorld(), BlockPos.ofFloored(position))
			.isEmpty();
	}

	private boolean collidableFor(Vec3d position, Direction.Axis axis, boolean negative) {
		return switch (axis) {
			case X -> this.collidable(position.add(negative ? -1 : 1, 0, 0));
			case Y -> this.collidable(position.add(0, negative ? -1 : 1, 0));
			case Z -> this.collidable(position.add(0, 0, negative ? -1 : 1));
		};
	}

	private void manageVelocityForEntity(Vec3d velocity, Runnable halfAction) {
		if (this.age <= this.getWorld().getGameRules().getInt(ExtravaganzaGameRules.FESTIVE_BALL_AGE_PERCENTAGE_BEFORE_PICKING) * 2) {
			boolean bl = Math.abs(velocity.y) > 0.3 || (Math.abs(velocity.y) > 0.05 && Math.abs(velocity.x) > 0.1 && Math.abs(velocity.z) > 0.1);
			if (Math.abs(velocity.y) >= Math.abs(velocity.x) && Math.abs(velocity.y) >= Math.abs(velocity.z) && bl) {
				this.setVelocity(velocity.x, velocity.y * -0.8, velocity.z);
			}
			else if (Math.abs(velocity.x) >= Math.abs(velocity.y) && Math.abs(velocity.x) >= Math.abs(velocity.z) && Math.abs(velocity.x) > 0.3) {
				this.setVelocity(velocity.x * -0.8, velocity.y, velocity.z);
			}
			else if (Math.abs(velocity.z) >= Math.abs(velocity.x) && Math.abs(velocity.z) >= Math.abs(velocity.y) && Math.abs(velocity.z) > 0.3) {
				this.setVelocity(velocity.x, velocity.y, velocity.z * -0.8);
			}
		}
		else {
			halfAction.run();
		}
	}

	@Override
	public void tick() {
		super.tick();
		this.getWorld().addParticle(ExtravaganzaParticles.createRandomConfetti(this.getRandom()), this.getX(), this.getY(), this.getZ(), 0.2, 0.2, 0.2);
		if (this.age >= 10 * 20) {
			this.discard();
			this.dropItem(this.source);
		}
	}
}
