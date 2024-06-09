package com.mmodding.extravaganza;

import com.mmodding.extravaganza.init.*;
import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Extravaganza implements ModInitializer {

	public static final List<String> COLOR_QUALIFIERS = List.of(
		"black", "blue", "brown", "cyan",
		"gray", "green", "light_blue", "light_gray",
		"lime", "magenta", "orange", "pink",
		"purple", "red", "white", "yellow"
	);

    private static final Logger LOGGER = LoggerFactory.getLogger("extravaganza");

	@Override
	public void onInitialize() {
		Extravaganza.getLogger().info("Time to add some extravaganza to your game!");

		ExtravaganzaBlocks.register();
		ExtravaganzaBlockEntities.register();
		ExtravaganzaEntities.register();
		ExtravaganzaItems.register();
		ExtravaganzaParticles.register();
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

	public static <T> Stream<RegistryKey<T>> extractKeyFromRegistry(Registry<T> registry) {
		return registry.streamEntries()
			.filter(ref -> ref.registryKey().getValue().getNamespace().equals(Extravaganza.id()))
			.map(RegistryEntry.Reference::registryKey);
	}

	public static <T> void executeKeyForRegistry(Registry<T> registry, Consumer<RegistryKey<T>> action) {
		Extravaganza.extractKeyFromRegistry(registry).forEach(action);
	}

	public static <T> Stream<T> extractFromRegistry(Registry<T> registry) {
		return registry.streamEntries()
			.filter(ref -> ref.registryKey().getValue().getNamespace().equals(Extravaganza.id()))
			.map(ref -> registry.get(ref.registryKey()));
	}

	public static <T> void executeForRegistry(Registry<T> registry, Consumer<T> action) {
		Extravaganza.extractFromRegistry(registry).forEach(action);
	}
}
