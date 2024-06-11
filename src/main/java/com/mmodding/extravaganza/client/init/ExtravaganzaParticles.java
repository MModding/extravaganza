package com.mmodding.extravaganza.client.init;

import com.mmodding.extravaganza.client.particle.ConfettiParticle;
import com.mmodding.extravaganza.init.ExtravaganzaParticleTypes;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class ExtravaganzaParticles {

	public static void register() {
		ParticleFactoryRegistry.getInstance().register(ExtravaganzaParticleTypes.CONFETTI, ConfettiParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ExtravaganzaParticleTypes.CONFETTI_SHAKE, ConfettiParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ExtravaganzaParticleTypes.CONFETTI_SHATTER, ConfettiParticle.Factory::new);
	}
}
