package com.mmodding.extravaganza;

import com.mmodding.extravaganza.init.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
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
			(dispatcher, registries, environment) -> dispatcher.register(CommandManager.literal("leave-ball-pit").executes(context -> {
				if (context.getSource().getPlayer() != null && context.getSource().getPlayer().hasAttached(ExtravaganzaDataAttachments.BEFORE_BALL_PIT)) {
					Vec3d position = context.getSource().getPlayer().getAttached(ExtravaganzaDataAttachments.BEFORE_BALL_PIT);
					assert position != null;
					context.getSource().getPlayer().teleport(position.getX(), position.getY(), position.getZ(), false);
					context.getSource().getPlayer().removeAttached(ExtravaganzaDataAttachments.BEFORE_BALL_PIT);
					return 1;
				}
				else {
					return 0;
				}
			}))
		);
		if (FabricLoader.getInstance().isModLoaded("ouch")) ExtravaganzaDamageTypes.ouch();
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

	public static String nameTweak(String name) {
		if (name.endsWith("s") && !name.endsWith("ss")) {
			return name.substring(0, name.length() - 1);
		}
		else {
			return name;
		}
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
