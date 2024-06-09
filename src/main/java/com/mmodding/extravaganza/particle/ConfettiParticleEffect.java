package com.mmodding.extravaganza.particle;

import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.ColorHelper;

public class ConfettiParticleEffect implements ParticleEffect {

	private final ParticleType<ConfettiParticleEffect> type;
	private final int color;

	public static MapCodec<ConfettiParticleEffect> createCodec(ParticleType<ConfettiParticleEffect> type) {
		return Codecs.ARGB.xmap(color -> new ConfettiParticleEffect(type, color), effect -> effect.color).fieldOf("color");
	}

	public static PacketCodec<? super ByteBuf, ConfettiParticleEffect> createPacketCodec(ParticleType<ConfettiParticleEffect> type) {
		return PacketCodecs.INTEGER.xmap(color -> new ConfettiParticleEffect(type, color), particleEffect -> particleEffect.color);
	}

	private ConfettiParticleEffect(ParticleType<ConfettiParticleEffect> type, int color) {
		this.type = type;
		this.color = color;
	}

	@Override
	public ParticleType<ConfettiParticleEffect> getType() {
		return this.type;
	}

	public float getRed() {
		return ColorHelper.Argb.getRed(this.color) / 255.0f;
	}

	public float getGreen() {
		return ColorHelper.Argb.getGreen(this.color) / 255.0f;
	}

	public float getBlue() {
		return ColorHelper.Argb.getBlue(this.color) / 255.0f;
	}

	public float getAlpha() {
		return ColorHelper.Argb.getAlpha(this.color) / 255.0f;
	}

	public static ConfettiParticleEffect create(ParticleType<ConfettiParticleEffect> type, int color) {
		return new ConfettiParticleEffect(type, color);
	}

	public static ConfettiParticleEffect create(ParticleType<ConfettiParticleEffect> type, float r, float g, float b) {
		return ConfettiParticleEffect.create(type, ColorHelper.Argb.fromFloats(1.0f, r, g, b));
	}
}
