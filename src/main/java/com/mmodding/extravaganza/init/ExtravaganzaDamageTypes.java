package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import eu.pb4.ouch.api.PresetCreationEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class ExtravaganzaDamageTypes {

	public static final ResourceKey<DamageType> TRASH = Extravaganza.createKey(Registries.DAMAGE_TYPE, "trash");

	@SuppressWarnings("unchecked")
	public static void ouch() {
		PresetCreationEvents.APPEND.register((builder, _) -> builder.addDamage("<#ff0000>-${value}</><dark_gray>\uD83D\uDDD1", ExtravaganzaDamageTypes.TRASH));
	}
}
