package com.mmodding.extravaganza.entity;

import com.mmodding.extravaganza.init.ExtravaganzaEntities;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.VehicleEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MerryGoRoundEntity extends VehicleEntity {

	private static final TrackedData<Byte> POWER = DataTracker.registerData(MerryGoRoundEntity.class, TrackedDataHandlerRegistry.BYTE);
	private static final TrackedData<Integer> ROTATION = DataTracker.registerData(MerryGoRoundEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private int lerpTicks;
	private double x;
	private double y;
	private double z;
	private double turnstileYaw;
	private double turnstilePitch;

	public MerryGoRoundEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	public MerryGoRoundEntity(World world, double x, double y, double z) {
		super(ExtravaganzaEntities.MERRY_GO_ROUND, world);
		this.setPosition(x, y, z);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(MerryGoRoundEntity.POWER, (byte) 1);
		builder.add(MerryGoRoundEntity.ROTATION, 0);
	}

	@Override
	public Item asItem() {
		return ExtravaganzaItems.MERRY_GO_ROUND;
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		this.dataTracker.set(MerryGoRoundEntity.POWER, nbt.getByte("Power"));
		this.dataTracker.set(MerryGoRoundEntity.ROTATION, nbt.getInt("CustomRotation"));
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.putByte("Power", this.dataTracker.get(MerryGoRoundEntity.POWER));
		nbt.putInt("CustomRotation", this.dataTracker.get(MerryGoRoundEntity.ROTATION));
	}

	@Override
	public boolean collidesWithStateAtPos(BlockPos pos, BlockState state) {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		if (this.isLogicalSideForUpdatingMovement()) {
			if (!this.getPassengerList().isEmpty()) {
				this.dataTracker.set(MerryGoRoundEntity.ROTATION, this.dataTracker.get(MerryGoRoundEntity.ROTATION) + 3 * this.dataTracker.get(MerryGoRoundEntity.POWER));
			}
			else {
				this.dataTracker.set(MerryGoRoundEntity.ROTATION, 0);
			}
		}
		this.updatePositionAndRotation();
	}

	@Override
	public double getLerpTargetX() {
		return this.lerpTicks > 0 ? this.x : this.getX();
	}

	@Override
	public double getLerpTargetY() {
		return this.lerpTicks > 0 ? this.y : this.getY();
	}

	@Override
	public double getLerpTargetZ() {
		return this.lerpTicks > 0 ? this.z : this.getZ();
	}

	@Override
	public float getLerpTargetYaw() {
		return this.lerpTicks > 0 ? (float) this.turnstileYaw : this.getYaw();
	}

	@Override
	public float getLerpTargetPitch() {
		return this.lerpTicks > 0 ? (float) this.turnstilePitch : this.getPitch();
	}

	@Override
	public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.turnstileYaw = this.dataTracker.get(MerryGoRoundEntity.ROTATION);
		this.turnstilePitch = pitch;
		this.lerpTicks = 11 - this.dataTracker.get(MerryGoRoundEntity.POWER);
	}

	private void updatePositionAndRotation() {
		if (this.isLogicalSideForUpdatingMovement()) {
			this.lerpTicks = 0;
			this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
		}

		if (this.lerpTicks > 0) {
			this.lerpPosAndRotation(this.lerpTicks, this.x, this.y, this.z, this.turnstileYaw, this.turnstilePitch);
			this.lerpTicks--;
		}
	}

	@Override
	public boolean collidesWith(Entity other) {
		return (other.isCollidable() || other.isPushable()) && !this.isConnectedThroughVehicle(other);
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

	@Override
	public boolean canHit() {
		return true;
	}

	@Override
	public float getYaw() {
		return this.dataTracker.get(MerryGoRoundEntity.ROTATION);
	}

	@Override
	protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
		float additionalX = 0.0f;
		float additionalZ = 0.0f;
		int i = this.getPassengerList().indexOf(passenger);

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

		return new Vec3d(additionalX, dimensions.height(), additionalZ).rotateY((float) Math.toRadians(-this.getYaw()));
	}

	@Override
	protected boolean canAddPassenger(Entity passenger) {
		return this.getPassengerList().size() < this.getMaxPassengers();
	}

	private int getMaxPassengers() {
		return 4;
	}

	@Override
	public ActionResult interact(PlayerEntity player, Hand hand) {
		if (player.getStackInHand(hand).isOf(ExtravaganzaItems.WRENCH_AGANZA)) {
			if (this.dataTracker.get(MerryGoRoundEntity.POWER) < 10) {
				this.dataTracker.set(MerryGoRoundEntity.POWER, (byte) MathHelper.clamp(this.dataTracker.get(MerryGoRoundEntity.POWER) + 1, 1, 10));
			}
			else {
				this.dataTracker.set(MerryGoRoundEntity.POWER, (byte) 1);
			}
			player.sendMessage(Text.translatable("enchantment.minecraft.power").append(": " + this.dataTracker.get(MerryGoRoundEntity.POWER)), true);
			return ActionResult.SUCCESS;
		}
		else if (super.interact(player, hand) != ActionResult.PASS) {
			return super.interact(player, hand);
		}
		else if (player.shouldCancelInteraction()) {
			return ActionResult.PASS;
		}
		else if (!this.getWorld().isClient()) {
			return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
		}
		else {
			return ActionResult.SUCCESS;
		}
	}

	@Override
	protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
		super.updatePassengerPosition(passenger, positionUpdater);
		this.clampPassengerYaw(passenger);
	}

	protected void clampPassengerYaw(Entity passenger) {
		passenger.setBodyYaw(this.getYaw());
		float f = MathHelper.wrapDegrees(passenger.getYaw() - this.getYaw());
		float g = MathHelper.clamp(f, -105.0f, 105.0f);
		passenger.prevYaw += g - f;
		passenger.setYaw(passenger.getYaw() + g - f);
		passenger.setHeadYaw(passenger.getYaw());
		if (passenger instanceof AnimalEntity && this.getPassengerList().size() == this.getMaxPassengers()) {
			passenger.setBodyYaw(((AnimalEntity) passenger).bodyYaw);
			passenger.setHeadYaw(passenger.getHeadYaw());
		}
	}

	@Override
	public void onPassengerLookAround(Entity passenger) {
		this.clampPassengerYaw(passenger);
	}
}
