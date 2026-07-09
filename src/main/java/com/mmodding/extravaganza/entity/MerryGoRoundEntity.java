package com.mmodding.extravaganza.entity;

import com.mmodding.extravaganza.init.ExtravaganzaEntities;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class MerryGoRoundEntity extends VehicleEntity {

	private static final EntityDataAccessor<Byte> POWER = SynchedEntityData.defineId(MerryGoRoundEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Integer> ROTATION = SynchedEntityData.defineId(MerryGoRoundEntity.class, EntityDataSerializers.INT);

	private final InterpolationHandler interpolation;

	public MerryGoRoundEntity(EntityType<?> entityType, Level level) {
		super(entityType, level);
		this.interpolation = new InterpolationHandler(this, this::onInterpolation);
	}

	public MerryGoRoundEntity(Level level, double x, double y, double z) {
		super(ExtravaganzaEntities.MERRY_GO_ROUND, level);
		this.interpolation = new InterpolationHandler(this, this::onInterpolation);
		this.setPosRaw(x, y, z);
		this.xo = x;
		this.yo = y;
		this.zo = z;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder entityData) {
		super.defineSynchedData(entityData);
		entityData.define(POWER, (byte) 1);
		entityData.define(ROTATION, 0);
	}

	private void onInterpolation(InterpolationHandler interpolation) {
		this.setRot(interpolation.xRot(), interpolation.yRot());
	}

	@Override
	protected Item getDropItem() {
		return ExtravaganzaItems.MERRY_GO_ROUND;
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		this.entityData.set(POWER, input.getByteOr("power", (byte) 1));
		this.entityData.set(ROTATION, input.getIntOr("custom_rotation", 0));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		output.putByte("power", this.entityData.get(POWER));
		output.putInt("custom_rotation", this.entityData.get(ROTATION));
	}

	@Override
	public boolean isColliding(BlockPos pos, BlockState state) {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		if (this.isLocalInstanceAuthoritative()) {
			if (!this.getPassengers().isEmpty()) {
				this.entityData.set(MerryGoRoundEntity.ROTATION, this.entityData.get(MerryGoRoundEntity.ROTATION) + 3 * this.entityData.get(MerryGoRoundEntity.POWER));
			}
			else {
				this.entityData.set(MerryGoRoundEntity.ROTATION, 0);
			}
		}
		this.interpolation.setInterpolationLength(11 - this.entityData.get(MerryGoRoundEntity.POWER));
	}

	@Override
	public @Nullable InterpolationHandler getInterpolation() {
		return this.interpolation;
	}

	@Override
	public boolean canBeCollidedWith(Entity other) {
		return other != null && other.isPushable() && !this.isPassengerOfSameVehicle(other);
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	protected @NonNull Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
		float additionalX = 0.0f;
		float additionalZ = 0.0f;
		int i = this.getPassengers().indexOf(passenger);

		switch (i) {
			case 0 -> {
				additionalX = 0.4f;
				additionalZ = 0.4f;
			}
			case 1 -> {
				additionalX = -0.4f;
				additionalZ = 0.4f;
			}
			case 2 -> {
				additionalX = 0.4f;
				additionalZ = -0.4f;
			}
			case 3 -> {
				additionalX = -0.4f;
				additionalZ = -0.4f;
			}
		}

		return new Vec3(additionalX, dimensions.height(), additionalZ).yRot((float) Math.toRadians(-this.getYRot()));
	}

	@Override
	protected boolean canAddPassenger(Entity passenger) {
		return this.getPassengers().size() < this.getMaxPassengers();
	}

	private int getMaxPassengers() {
		return 4;
	}

	@Override
	public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
		if (player.getItemInHand(hand).is(ExtravaganzaItems.WRENCH_AGANZA)) {
			if (this.entityData.get(MerryGoRoundEntity.POWER) < 10) {
				this.entityData.set(MerryGoRoundEntity.POWER, (byte) Mth.clamp(this.entityData.get(MerryGoRoundEntity.POWER) + 1, 1, 10));
			}
			else {
				this.entityData.set(MerryGoRoundEntity.POWER, (byte) 1);
			}
			player.sendOverlayMessage(Component.translatable("enchantment.minecraft.power").append(": " + this.entityData.get(MerryGoRoundEntity.POWER)));
			return InteractionResult.SUCCESS;
		}
		else if (super.interact(player, hand, location) != InteractionResult.PASS) {
			return super.interact(player, hand, location);
		}
		else if (player.isSecondaryUseActive()) {
			return InteractionResult.PASS;
		}
		else if (!this.level().isClientSide()) {
			return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
		}
		else {
			return InteractionResult.SUCCESS;
		}
	}

	@Override
	protected void positionRider(Entity passenger, MoveFunction moveFunction) {
		super.positionRider(passenger, moveFunction);
		this.clampPassengerYaw(passenger);
	}

	protected void clampPassengerYaw(Entity passenger) {
		passenger.setYBodyRot(this.getYRot());
		float f = Mth.wrapDegrees(passenger.getYRot() - this.getYRot());
		float g = Mth.clamp(f, -105.0f, 105.0f);
		passenger.setYRot(passenger.getYRot() + g - f);
		passenger.setYHeadRot(passenger.getYRot());
		if (passenger instanceof LivingEntity && this.getPassengers().size() == this.getMaxPassengers()) {
			passenger.setYBodyRot(((LivingEntity) passenger).yBodyRot);
			passenger.setYHeadRot(passenger.getYHeadRot());
		}
	}

	@Override
	public void onPassengerTurned(Entity passenger) {
		this.clampPassengerYaw(passenger);
	}
}
