package com.mmodding.extravaganza;

import com.mmodding.extravaganza.init.*;
import com.mmodding.extravaganza.resource.ExtravaganzaWorldGenerationResources;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.ExtendedModInitializer;
import com.mmodding.library.core.api.management.ElementsManager;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Extravaganza implements ExtendedModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("extravaganza");

	@Override
	public void setupManager(ElementsManager manager) {
		manager.content(ExtravaganzaItems::register);
		manager.content(ExtravaganzaBlocks::register);
		manager.content(ExtravaganzaEntities::register);
		manager.content(ExtravaganzaBlockEntities::register);
		manager.content(ExtravaganzaGameRules::register);
		manager.content(ExtravaganzaParticleTypes::register);
		manager.content(ExtravaganzaDataAttachments::register);
		manager.content(ExtravaganzaWorldGeneration::register);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {
		Extravaganza.getLogger().info("Time to add some extravaganza to your game!");

		LootTableEvents.MODIFY.register((key, builder, source, provider) -> {
			if (BuiltInLootTables.ABANDONED_MINESHAFT.equals(key) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.lootPool()
					.add(LootItem.lootTableItem(ExtravaganzaItems.COMMON_FESTIVE_COIN).setWeight(20))
					.add(LootItem.lootTableItem(ExtravaganzaItems.UNCOMMON_FESTIVE_COIN).setWeight(10))
					.add(LootItem.lootTableItem(ExtravaganzaItems.GOLDEN_FESTIVE_COIN).setWeight(5));
				builder.pool(pool.build());
			}
		});
		CommandRegistrationCallback.EVENT.register(
			(dispatcher, registries, environment) -> dispatcher.register(Commands.literal("leave-ball-pit").executes(context -> {
				if (context.getSource().getPlayer() != null && context.getSource().getPlayer().hasAttached(ExtravaganzaDataAttachments.BEFORE_BALL_PIT)) {
					Vec3 position = context.getSource().getPlayer().getAttached(ExtravaganzaDataAttachments.BEFORE_BALL_PIT);
					assert position != null;
					context.getSource().getPlayer().teleportTo(position.x(), position.y(), position.z());
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
		return Identifier.fromNamespaceAndPath(id(), path);
	}

	public static <T> ResourceKey<T> createKey(ResourceKey<? extends Registry<T>> registry, String path) {
		return ResourceKey.create(registry, createId(path));
	}

	public static String nameTweak(String name) {
		if (name.endsWith("s") && !name.endsWith("ss")) {
			return name.substring(0, name.length() - 1);
		}
		else {
			return name;
		}
	}
}
