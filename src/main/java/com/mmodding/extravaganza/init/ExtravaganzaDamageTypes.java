package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import eu.pb4.ouch.api.PresetCreationEvents;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ExtravaganzaDamageTypes {

	public static final RegistryKey<DamageType> TRASH = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Extravaganza.createId("trash"));

	public static void data(RegistryBuilder registries) {
		registries.addRegistry(
			RegistryKeys.DAMAGE_TYPE,
			damageTypes -> damageTypes.register(ExtravaganzaDamageTypes.TRASH, new DamageType("trash", 0.0f))
		);
	}

	@SuppressWarnings("unchecked")
	public static void ouch() {
		PresetCreationEvents.APPEND.register((builder, preset) -> builder.addDamage("<#ff0000>-${value}</><dark_gray>\uD83D\uDDD1", ExtravaganzaDamageTypes.TRASH));
	}
}
