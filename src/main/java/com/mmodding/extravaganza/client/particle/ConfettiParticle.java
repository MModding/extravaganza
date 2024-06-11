package com.mmodding.extravaganza.client.particle;

import com.mmodding.extravaganza.init.ExtravaganzaParticleTypes;
import com.mmodding.extravaganza.particle.ConfettiParticleEffect;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;

public class ConfettiParticle extends AscendingParticle {

	protected ConfettiParticle(ClientWorld world, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteProvider spriteProvider, float colorMultiplier, int baseMaxAge, float gravityStrength, boolean collidesWithWorld) {
		super(world, x, y, z, randomVelocityXMultiplier, randomVelocityYMultiplier, randomVelocityZMultiplier, velocityX, velocityY, velocityZ, scaleMultiplier, spriteProvider, colorMultiplier, baseMaxAge, gravityStrength, collidesWithWorld);
	}

	public static class Factory implements ParticleFactory<ConfettiParticleEffect> {

		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(ConfettiParticleEffect effect, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			float multiplier;
			if (effect.getType().equals(ExtravaganzaParticleTypes.CONFETTI_SHAKE)) {
				multiplier = 0.6f;
			}
			else if (effect.getType().equals(ExtravaganzaParticleTypes.CONFETTI_SHATTER)) {
				multiplier = 0.3f;
			}
			else {
				multiplier = 0.1f;
			}
			ConfettiParticle particle = new ConfettiParticle(clientWorld, d, e, f, multiplier, multiplier, multiplier, 0.0f, 0.0f, 0.0f, 1.0f, this.spriteProvider, 1.0f, 30, 0.1f, true);
			particle.setAlpha(effect.getAlpha());
			particle.setColor(effect.getRed(), effect.getGreen(), effect.getBlue());
			return particle;
		}
	}
}
