package com.mmodding.extravaganza.entity;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.init.ExtravaganzaEntities;
import com.mmodding.extravaganza.init.ExtravaganzaGameRules;
import com.mmodding.extravaganza.init.ExtravaganzaParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.atomic.AtomicBoolean;

public class FestiveBallEntity extends ThrowableItemProjectile {

	public static final EntityDataAccessor<String> COLOR = SynchedEntityData.defineId(FestiveBallEntity.class, EntityDataSerializers.STRING);

	public FestiveBallEntity(EntityType<? extends FestiveBallEntity> type, Level level) {
		super(type, level);
	}

	public FestiveBallEntity(ExtravaganzaColor color, Level level, Entity owner) {
		this(ExtravaganzaEntities.FESTIVE_BALL, level);
		this.setColor(color);
		this.setOwner(owner);
		this.setPosRaw(owner.getX(), owner.getEyeY() - 0.1f, owner.getZ());
		this.setDeltaMovement(
			owner.getViewVector(1).x(),
			owner.getViewVector(1).y(),
			owner.getViewVector(1).z()
		);
		this.setSilent(true);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder entityData) {
		super.defineSynchedData(entityData);
		entityData.define(FestiveBallEntity.COLOR, ExtravaganzaColor.BLACK.getSerializedName());
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		this.setColor(ExtravaganzaColor.fromString(input.getStringOr("color", "black")));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		output.putString("color", this.getColor().getSerializedName());
	}

	public ExtravaganzaColor getColor() {
		return ExtravaganzaColor.fromString(this.entityData.get(FestiveBallEntity.COLOR));
	}

	public void setColor(ExtravaganzaColor color) {
		this.entityData.set(FestiveBallEntity.COLOR, color.getSerializedName());
	}

	@Override
	protected Item getDefaultItem() {
		return BuiltInRegistries.ITEM.getValue(Extravaganza.createId("black_festive_ball"));
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		Vec3 velocity = this.getDeltaMovement();
		switch (hitResult.getDirection()) {
			case WEST, EAST -> this.setDeltaMovement(velocity.x * -0.8, velocity.y, velocity.z);
			case UP, DOWN -> this.setDeltaMovement(velocity.x, velocity.y * -0.8, velocity.z);
			case NORTH, SOUTH -> this.setDeltaMovement(velocity.x, velocity.y, velocity.z * -0.8);
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		this.manageVelocityForEntity(this.getDeltaMovement(), () -> {
			this.discard();
			if (hitResult.getEntity() instanceof Player player) {
				player.getInventory().add(this.getColor().createBallStack());
			} else if (hitResult.getEntity() instanceof InventoryCarrier owner) {
				owner.getInventory().addItem(this.getColor().createBallStack());
			}
		});
	}

	@Override
	protected void onDeflection(boolean byAttack) {
		if (!(this.level() instanceof ServerLevel level)) { super.onDeflection(byAttack); return; }
		AtomicBoolean bool = new AtomicBoolean();
		this.manageVelocityForEntity(this.getDeltaMovement(), () -> {
			this.discard();
			if (this.getOwner() != null) {
				if (this.getOwner() instanceof Player player) {
					player.getInventory().add(this.getColor().createBallStack());
				}
				else if (this.getOwner() instanceof InventoryCarrier owner) {
					owner.getInventory().addItem(this.getColor().createBallStack());
				}
				else {
					this.discard();
					this.spawnAtLocation(level, this.getColor().createBallStack());
				}
			}
			else {
				this.discard();
				this.spawnAtLocation(level, this.getColor().createBallStack());
			}
			bool.set(true);
		});
		if (!bool.get()) {
			this.setDeltaMovement(
				this.getDeltaMovement().x() * 1.5,
				this.getDeltaMovement().y() * 1.5,
				this.getDeltaMovement().z() * 1.5
			);
		}
	}

	private void manageVelocityForEntity(Vec3 velocity, Runnable halfAction) {
		if (this.level().isClientSide()) return;
		if (this.tickCount <= this.level().getServer().getGameRules().get(ExtravaganzaGameRules.FESTIVE_BALL_AGE_PERCENTAGE_BEFORE_PICKING) * 2) {
			boolean bl = Math.abs(velocity.y) > 0.3 || (Math.abs(velocity.y) > 0.05 && Math.abs(velocity.x) > 0.1 && Math.abs(velocity.z) > 0.1);
			if (Math.abs(velocity.y) >= Math.abs(velocity.x) && Math.abs(velocity.y) >= Math.abs(velocity.z) && bl) {
				this.setDeltaMovement(velocity.x, velocity.y * -0.8, velocity.z);
			}
			else if (Math.abs(velocity.x) >= Math.abs(velocity.y) && Math.abs(velocity.x) >= Math.abs(velocity.z) && Math.abs(velocity.x) > 0.3) {
				this.setDeltaMovement(velocity.x * -0.8, velocity.y, velocity.z);
			}
			else if (Math.abs(velocity.z) >= Math.abs(velocity.x) && Math.abs(velocity.z) >= Math.abs(velocity.y) && Math.abs(velocity.z) > 0.3) {
				this.setDeltaMovement(velocity.x, velocity.y, velocity.z * -0.8);
			}
		}
		else {
			halfAction.run();
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level() instanceof ServerLevel level) {
			this.level().addParticle(ExtravaganzaParticleTypes.createRandomConfetti(this.getRandom()), this.getX(), this.getY(), this.getZ(), 0.2, 0.2, 0.2);
			if (this.tickCount >= 10 * 20) {
				this.discard();
				this.spawnAtLocation(level, this.getColor().createBallStack());
			}
		}
	}
}
