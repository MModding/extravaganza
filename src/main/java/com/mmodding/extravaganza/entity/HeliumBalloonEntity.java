package com.mmodding.extravaganza.entity;

import com.mmodding.extravaganza.init.ExtravaganzaEntities;
import com.mmodding.extravaganza.init.ExtravaganzaParticleTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class HeliumBalloonEntity extends Entity {

	private static final TrackedData<String> VARIANT = DataTracker.registerData(HeliumBalloonEntity.class, TrackedDataHandlerRegistry.STRING);

	public HeliumBalloonEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	public HeliumBalloonEntity(World world, double x, double y, double z, String variant) {
		this(ExtravaganzaEntities.HELIUM_BALLOON, world);
		this.setPosition(x, y, z);
		this.dataTracker.set(HeliumBalloonEntity.VARIANT, variant);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		builder.add(HeliumBalloonEntity.VARIANT, "none");
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
	}

	@Override
	public void tick() {
		super.tick();
		this.setPosition(this.getX(), this.getY() + 0.05, this.getZ());
		if (this.age >= 10 * 20) {
			this.discard();
			this.playSound(SoundEvents.BLOCK_LAVA_POP, 1.0f, 1.0f);
			if (this.getWorld() instanceof ServerWorld world) {
				world.spawnParticles(
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

	public String getVariant() {
		return this.dataTracker.get(HeliumBalloonEntity.VARIANT);
	}
}
