package com.mmodding.extravaganza;

import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.extravaganza.init.ExtravaganzaEntities;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Extravaganza implements ModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("extravaganza");

	@Override
	public void onInitialize() {
		Extravaganza.getLogger().info("Time to add some extravaganza to your game!");

		ExtravaganzaBlocks.register();
		ExtravaganzaEntities.register();
		ExtravaganzaItems.register();
	}

	public static Logger getLogger() {
		return Extravaganza.LOGGER;
	}

	public static String id() {
		return "extravaganza";
	}

	public static Identifier createId(String path) {
		return Identifier.of(Extravaganza.id(), path);
	}
}
