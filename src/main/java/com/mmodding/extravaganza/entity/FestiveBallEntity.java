package com.mmodding.extravaganza.entity;

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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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
		/* this.setYaw(owner.getYaw());
		this.setPitch(owner.getPitch()); */
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
		this.manageVelocity(this.getVelocity(), () -> {
			this.discard();
			this.dropItem(this.source);
		});
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		this.manageVelocity(this.getVelocity(), () -> {
			this.discard();
			if (entityHitResult.getEntity() instanceof PlayerEntity player) {
				player.getInventory().insertStack(this.source.getDefaultStack());
			}
			else if (entityHitResult.getEntity() instanceof InventoryOwner owner) {
				owner.getInventory().addStack(this.source.getDefaultStack());
			}
		});
	}

	@Override
	protected void onDeflected(@Nullable Entity deflector, boolean fromAttack) {
		AtomicBoolean bool = new AtomicBoolean();
		this.manageVelocity(this.getVelocity(), () -> {
			this.discard();
			if (deflector != null) {
				if (deflector instanceof PlayerEntity player) {
					player.getInventory().insertStack(this.source.getDefaultStack());
				}
				else if (deflector instanceof InventoryOwner owner) {
					owner.getInventory().addStack(this.source.getDefaultStack());
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

	private boolean collides(BlockPos pos) {
		return !this.getWorld().getBlockState(pos).getCollisionShape(this.getWorld(), pos).isEmpty();
	}

	private boolean isLess(BlockPos than, double value) {
		return Math.abs(this.getX() - than.getX()) <= value || Math.abs(this.getY() - than.getY()) <= value || Math.abs(this.getZ() - than.getZ()) <= value;
	}

	private void manageVelocity(Vec3d velocity, Runnable notInBounds) {
		/*
		 * TODO: potential better way to do it: calculate pitch and yaw based on previous values (prevX, prevY, prevZ)
		 * TODO: and current values (position) to then apply them and retrieve the lookingDirection to choose which
		 * TODO: velocity coordinate should apply first
		*/
		boolean bl = Math.abs(velocity.y) > 0.3 || (Math.abs(velocity.y) > 0.05 && Math.abs(velocity.x) > 0.1 && Math.abs(velocity.z) > 0.1);
		if (!this.getWorld().getBlockState(this.getBlockPos()).getCollisionShape(this.getWorld(), this.getBlockPos()).isEmpty()) {
			notInBounds.run();
		}
		else if (Math.abs(velocity.y) >= Math.abs(velocity.x) && Math.abs(velocity.y) >= Math.abs(velocity.z) && bl) {
			this.setVelocity(velocity.x, velocity.y * -0.8, velocity.z);
		}
		else if (Math.abs(velocity.x) >= Math.abs(velocity.y) && Math.abs(velocity.x) >= Math.abs(velocity.z) && Math.abs(velocity.x) > 0.3) {
			this.setVelocity(velocity.x * -0.8, velocity.y, velocity.z);
		}
		else if (Math.abs(velocity.z) >= Math.abs(velocity.x) && Math.abs(velocity.z) >= Math.abs(velocity.y) && Math.abs(velocity.z) > 0.3) {
			this.setVelocity(velocity.x, velocity.y, velocity.z * -0.8);
		}
	}

	@Override
	public void tick() {
		super.tick();
		this.getWorld().addParticle(ExtravaganzaParticles.createRandomConfetti(this.getRandom()), this.getX(), this.getY(), this.getZ(), 0.2, 0.2, 0.2);
	}
}
