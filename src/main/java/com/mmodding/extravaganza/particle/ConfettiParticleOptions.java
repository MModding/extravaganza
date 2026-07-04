package com.mmodding.extravaganza.particle;

import com.mmodding.library.java.api.color.ARGB;
import com.mmodding.library.java.api.color.Color;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class ConfettiParticleOptions implements ParticleOptions {

	private final ParticleType<ConfettiParticleOptions> type;
	private final ARGB color;

	public static MapCodec<ConfettiParticleOptions> createCodec(ParticleType<ConfettiParticleOptions> type) {
		return Codec.INT.xmap(color -> new ConfettiParticleOptions(type, Color.argb(color)), effect -> effect.color.toDecimal()).fieldOf("color");
	}

	public static StreamCodec<? super ByteBuf, ConfettiParticleOptions> createPacketCodec(ParticleType<ConfettiParticleOptions> type) {
		return ByteBufCodecs.INT.map(color -> new ConfettiParticleOptions(type, Color.argb(color)), particleEffect -> particleEffect.color.toDecimal());
	}

	private ConfettiParticleOptions(ParticleType<ConfettiParticleOptions> type, ARGB color) {
		this.type = type;
		this.color = color;
	}

	@Override
	public ParticleType<ConfettiParticleOptions> getType() {
		return this.type;
	}

	public float getRed() {
		return this.color.getRed();
	}

	public float getGreen() {
		return this.color.getGreen();
	}

	public float getBlue() {
		return this.color.getBlue();
	}

	public float getAlpha() {
		return this.color.getAlpha();
	}

	public static ConfettiParticleOptions create(ParticleType<ConfettiParticleOptions> type, ARGB color) {
		return new ConfettiParticleOptions(type, color);
	}

	public static ConfettiParticleOptions create(ParticleType<ConfettiParticleOptions> type, int r, int g, int b) {
		return ConfettiParticleOptions.create(type, Color.argb(256, r, g, b));
	}
}
