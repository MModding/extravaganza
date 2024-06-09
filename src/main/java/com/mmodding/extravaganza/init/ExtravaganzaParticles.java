package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.particle.ConfettiParticleEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.random.Random;

import java.util.List;
import java.util.function.Function;

public class ExtravaganzaParticles {

	public static final ParticleType<ConfettiParticleEffect> CONFETTI = ExtravaganzaParticles.createParticleType(
		true, ConfettiParticleEffect::createCodec, ConfettiParticleEffect::createPacketCodec
	);

	public static final ParticleType<ConfettiParticleEffect> CONFETTI_SHAKE = ExtravaganzaParticles.createParticleType(
		true, ConfettiParticleEffect::createCodec, ConfettiParticleEffect::createPacketCodec
	);

	public static final ParticleType<ConfettiParticleEffect> CONFETTI_SHATTER = ExtravaganzaParticles.createParticleType(
		true, ConfettiParticleEffect::createCodec, ConfettiParticleEffect::createPacketCodec
	);

	public static void register() {
		Registry.register(Registries.PARTICLE_TYPE, Extravaganza.createId("confetti"), ExtravaganzaParticles.CONFETTI);
		Registry.register(Registries.PARTICLE_TYPE, Extravaganza.createId("confetti_shake"), ExtravaganzaParticles.CONFETTI_SHAKE);
		Registry.register(Registries.PARTICLE_TYPE, Extravaganza.createId("confetti_shatter"), ExtravaganzaParticles.CONFETTI_SHATTER);
	}

	private static <T extends ParticleEffect> ParticleType<T> createParticleType(
		boolean alwaysShow,
		Function<ParticleType<T>, MapCodec<T>> codecGetter,
		Function<ParticleType<T>, PacketCodec<? super RegistryByteBuf, T>> packetCodecGetter
	) {
		return new ParticleType<>(alwaysShow) {

			@Override
			public MapCodec<T> getCodec() {
				return codecGetter.apply(this);
			}

			@Override
			public PacketCodec<? super RegistryByteBuf, T> getPacketCodec() {
				return packetCodecGetter.apply(this);
			}
		};
	}

	public static ConfettiParticleEffect createRandomConfetti(Random random) {
		List<ParticleType<ConfettiParticleEffect>> particles = List.of(
			ExtravaganzaParticles.CONFETTI,
			ExtravaganzaParticles.CONFETTI_SHAKE,
			ExtravaganzaParticles.CONFETTI_SHATTER
		);
		return ConfettiParticleEffect.create(
			particles.get(random.nextInt(3)),
			random.nextFloat() * 255,
			random.nextFloat() * 255,
			random.nextFloat() * 255
		);
	}
}
