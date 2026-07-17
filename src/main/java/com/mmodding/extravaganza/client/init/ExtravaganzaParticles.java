package com.mmodding.extravaganza.client.init;

import com.mmodding.extravaganza.client.particle.ConfettiParticle;
import com.mmodding.extravaganza.init.ExtravaganzaParticleTypes;
import com.mmodding.library.core.api.AdvancedContainer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;

public class ExtravaganzaParticles {

	public static void register(AdvancedContainer mod) {
		ParticleProviderRegistry.getInstance().register(ExtravaganzaParticleTypes.CONFETTI, ConfettiParticle.Provider::new);
		ParticleProviderRegistry.getInstance().register(ExtravaganzaParticleTypes.CONFETTI_SHAKE, ConfettiParticle.Provider::new);
		ParticleProviderRegistry.getInstance().register(ExtravaganzaParticleTypes.CONFETTI_SHATTER, ConfettiParticle.Provider::new);
	}
}
