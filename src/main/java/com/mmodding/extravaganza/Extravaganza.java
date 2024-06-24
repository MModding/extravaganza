package com.mmodding.extravaganza;

import com.mmodding.extravaganza.init.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class Extravaganza implements ModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("extravaganza");

	@Override
	public void onInitialize() {
		Extravaganza.getLogger().info("Time to add some extravaganza to your game!");

		ExtravaganzaItems.register();
		ExtravaganzaBlocks.register();
		ExtravaganzaEntities.register();
		ExtravaganzaBlockEntities.register();
		ExtravaganzaGameRules.register();
		ExtravaganzaParticleTypes.register();
		ExtravaganzaWorldGeneration.register();
		ExtravaganzaDataAttachments.register();
		LootTableEvents.MODIFY.register((key, builder, source) -> {
			if (LootTables.ABANDONED_MINESHAFT_CHEST.equals(key) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.builder()
					.with(ItemEntry.builder(ExtravaganzaItems.COMMON_FESTIVE_COIN).weight(20))
					.with(ItemEntry.builder(ExtravaganzaItems.UNCOMMON_FESTIVE_COIN).weight(10))
					.with(ItemEntry.builder(ExtravaganzaItems.GOLDEN_FESTIVE_COIN).weight(5));
				builder.pool(pool);
			}
		});
		CommandRegistrationCallback.EVENT.register(
			(dispatcher, registries, environment) -> dispatcher.register(CommandManager.literal("before-entering-poll").executes(context -> {
				if (context.getSource().getPlayer() != null && context.getSource().getPlayer().hasAttached(ExtravaganzaDataAttachments.BEFORE_ENTERING_POOL)) {
					Vec3d position = context.getSource().getPlayer().getAttached(ExtravaganzaDataAttachments.BEFORE_ENTERING_POOL);
					assert position != null;
					context.getSource().getPlayer().teleport(position.getX(), position.getY(), position.getZ(), false);
					context.getSource().getPlayer().removeAttached(ExtravaganzaDataAttachments.BEFORE_ENTERING_POOL);
					return 1;
				}
				else {
					return 0;
				}
			}))
		);
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
