package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.particle.ConfettiParticleEffect;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class ExtravaganzaParticleTypes {

	public static final ParticleType<ConfettiParticleEffect> CONFETTI = FabricParticleTypes.complex(
		ConfettiParticleEffect::createCodec, ConfettiParticleEffect::createPacketCodec
	);

	public static final ParticleType<ConfettiParticleEffect> CONFETTI_SHAKE = FabricParticleTypes.complex(
		ConfettiParticleEffect::createCodec, ConfettiParticleEffect::createPacketCodec
	);

	public static final ParticleType<ConfettiParticleEffect> CONFETTI_SHATTER = FabricParticleTypes.complex(
		ConfettiParticleEffect::createCodec, ConfettiParticleEffect::createPacketCodec
	);

	public static void register() {
		Registry.register(Registries.PARTICLE_TYPE, Extravaganza.createId("confetti"), ExtravaganzaParticleTypes.CONFETTI);
		Registry.register(Registries.PARTICLE_TYPE, Extravaganza.createId("confetti_shake"), ExtravaganzaParticleTypes.CONFETTI_SHAKE);
		Registry.register(Registries.PARTICLE_TYPE, Extravaganza.createId("confetti_shatter"), ExtravaganzaParticleTypes.CONFETTI_SHATTER);
	}

	public static ConfettiParticleEffect createRandomConfetti(Random random) {
		List<ParticleType<ConfettiParticleEffect>> particles = List.of(
			ExtravaganzaParticleTypes.CONFETTI,
			ExtravaganzaParticleTypes.CONFETTI_SHAKE,
			ExtravaganzaParticleTypes.CONFETTI_SHATTER
		);
		return ConfettiParticleEffect.create(
			particles.get(random.nextInt(3)),
			random.nextFloat() * 255,
			random.nextFloat() * 255,
			random.nextFloat() * 255
		);
	}
}
