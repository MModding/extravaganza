package com.mmodding.extravaganza.client.particle;

import com.mmodding.extravaganza.init.ExtravaganzaParticleTypes;
import com.mmodding.extravaganza.particle.ConfettiParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.Nullable;

public class ConfettiParticle extends BaseAshSmokeParticle {

	protected ConfettiParticle(ClientLevel level, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteSet spriteSet, float colorMultiplier, int baseMaxAge, float gravityStrength, boolean collidesWithWorld) {
		super(level, x, y, z, randomVelocityXMultiplier, randomVelocityYMultiplier, randomVelocityZMultiplier, velocityX, velocityY, velocityZ, scaleMultiplier, spriteSet, colorMultiplier, baseMaxAge, gravityStrength, collidesWithWorld);
	}

	public static class Provider implements ParticleProvider<ConfettiParticleOptions> {

		private final SpriteSet spriteSet;

		public Provider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public @Nullable Particle createParticle(ConfettiParticleOptions options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random) {
			float multiplier;
			if (options.getType().equals(ExtravaganzaParticleTypes.CONFETTI_SHAKE)) {
				multiplier = 0.6f;
			}
			else if (options.getType().equals(ExtravaganzaParticleTypes.CONFETTI_SHATTER)) {
				multiplier = 0.3f;
			}
			else {
				multiplier = 0.1f;
			}
			ConfettiParticle particle = new ConfettiParticle(level, x, y, z, multiplier, multiplier, multiplier, 0.0f, 0.0f, 0.0f, 1.0f, this.spriteSet, 1.0f, 30, 0.1f, true);
			particle.setAlpha(options.getAlpha());
			particle.setColor(options.getRed(), options.getGreen(), options.getBlue());
			return particle;
		}
	}
}
