package com.mmodding.extravaganza.entity;

import com.mmodding.extravaganza.init.ExtravaganzaEntities;
import com.mmodding.extravaganza.init.ExtravaganzaParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class HeliumBalloonEntity extends Entity {

	public static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(HeliumBalloonEntity.class, EntityDataSerializers.STRING);

	public HeliumBalloonEntity(EntityType<?> type, Level level) {
		super(type, level);
	}

	public HeliumBalloonEntity(Level level, double x, double y, double z, String variant) {
		this(ExtravaganzaEntities.HELIUM_BALLOON, level);
		this.setPosRaw(x, y, z);
		this.entityData.set(HeliumBalloonEntity.VARIANT, variant);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder entityData) {
		entityData.define(HeliumBalloonEntity.VARIANT, "none");
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		this.entityData.set(VARIANT, input.getStringOr("variant", "none"));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		output.putString("variant", this.entityData.get(VARIANT));
	}

	@Override
	public void tick() {
		super.tick();
		this.setPosRaw(this.getX(), this.getY() + 0.05, this.getZ());
		if (this.tickCount >= 10 * 20) {
			this.discard();
			this.playSound(SoundEvents.LAVA_POP, 1.0f, 1.0f);
			if (this.level() instanceof ServerLevel level) {
				level.sendParticles(
					ExtravaganzaParticleTypes.createRandomConfetti(this.getRandom()),
					this.getX(),
					this.getY(),
					this.getZ(),
					60,
					(this.getRandom().nextBoolean() ? -1 : 1) * this.getRandom().nextDouble(),
					this.getRandom().nextDouble() * 1.5,
					(this.getRandom().nextBoolean() ? -1 : 1) * this.getRandom().nextDouble(),
					0.0f
				);
			}
		}
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
		return false;
	}

	public String getVariant() {
		return this.entityData.get(HeliumBalloonEntity.VARIANT);
	}
}
