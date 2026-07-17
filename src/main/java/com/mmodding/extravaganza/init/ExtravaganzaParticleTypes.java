package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.particle.ConfettiParticleOptions;
import com.mmodding.library.core.api.AdvancedContainer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;

import java.util.List;

public class ExtravaganzaParticleTypes {

	public static final ParticleType<ConfettiParticleOptions> CONFETTI = FabricParticleTypes.complex(
		ConfettiParticleOptions::createCodec, ConfettiParticleOptions::createPacketCodec
	);

	public static final ParticleType<ConfettiParticleOptions> CONFETTI_SHAKE = FabricParticleTypes.complex(
		ConfettiParticleOptions::createCodec, ConfettiParticleOptions::createPacketCodec
	);

	public static final ParticleType<ConfettiParticleOptions> CONFETTI_SHATTER = FabricParticleTypes.complex(
		ConfettiParticleOptions::createCodec, ConfettiParticleOptions::createPacketCodec
	);

	public static void register(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.PARTICLE_TYPE, factory -> {
			factory.register("confetti", CONFETTI);
			factory.register("confetti_shake", CONFETTI_SHAKE);
			factory.register("confetti_shatter", CONFETTI_SHATTER);
		});
	}

	public static ConfettiParticleOptions createRandomConfetti(RandomSource random) {
		List<ParticleType<ConfettiParticleOptions>> particles = List.of(
			ExtravaganzaParticleTypes.CONFETTI,
			ExtravaganzaParticleTypes.CONFETTI_SHAKE,
			ExtravaganzaParticleTypes.CONFETTI_SHATTER
		);
		return ConfettiParticleOptions.create(
			particles.get(random.nextInt(3)),
			random.nextInt(256),
			random.nextInt(256),
			random.nextInt(256)
		);
	}
}
