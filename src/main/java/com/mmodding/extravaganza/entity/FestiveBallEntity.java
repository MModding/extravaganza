package com.mmodding.extravaganza.entity;

import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.init.ExtravaganzaEntities;
import com.mmodding.extravaganza.init.ExtravaganzaGameRules;
import com.mmodding.extravaganza.init.ExtravaganzaParticleTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class FestiveBallEntity extends ThrownEntity {

	public static final TrackedData<String> COLOR = DataTracker.registerData(FestiveBallEntity.class, TrackedDataHandlerRegistry.STRING);

	public FestiveBallEntity(EntityType<? extends FestiveBallEntity> type, World world) {
		super(type, world);
	}

	public FestiveBallEntity(ExtravaganzaColor color, World world, Entity owner) {
		this(ExtravaganzaEntities.FESTIVE_BALL, world);
		this.setColor(color);
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
	protected void initDataTracker(DataTracker.Builder builder) {
		builder.add(FestiveBallEntity.COLOR, ExtravaganzaColor.BLACK.asString());
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		this.setColor(ExtravaganzaColor.fromString(nbt.getString("color")));
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.putString("color", this.getColor().asString());
	}

	public ExtravaganzaColor getColor() {
		return ExtravaganzaColor.fromString(this.dataTracker.get(FestiveBallEntity.COLOR));
	}

	public void setColor(ExtravaganzaColor color) {
		this.dataTracker.set(FestiveBallEntity.COLOR, color.asString());
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
				player.getInventory().insertStack(this.getColor().createBallStack());
			} else if (entityHitResult.getEntity() instanceof InventoryOwner owner) {
				owner.getInventory().addStack(this.getColor().createBallStack());
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
					player.getInventory().insertStack(this.getColor().createBallStack());
				}
				else if (deflector instanceof InventoryOwner owner) {
					owner.getInventory().addStack(this.getColor().createBallStack());
				}
				else {
					this.discard();
					this.dropStack(this.getColor().createBallStack());
				}
			}
			else {
				this.discard();
				this.dropStack(this.getColor().createBallStack());
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
		this.getWorld().addParticle(ExtravaganzaParticleTypes.createRandomConfetti(this.getRandom()), this.getX(), this.getY(), this.getZ(), 0.2, 0.2, 0.2);
		if (this.age >= 10 * 20) {
			this.discard();
			this.dropStack(this.getColor().createBallStack());
		}
	}
}
