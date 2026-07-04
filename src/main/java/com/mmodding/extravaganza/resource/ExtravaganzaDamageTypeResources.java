package com.mmodding.extravaganza.resource;

import com.mmodding.extravaganza.init.ExtravaganzaDamageTypes;
import com.mmodding.library.core.api.AdvancedContainer;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageType;

public class ExtravaganzaDamageTypeResources {

	public static void configure(AdvancedContainer mod, BootstrapContext<DamageType> damageTypes) {
		damageTypes.register(ExtravaganzaDamageTypes.TRASH, new DamageType("trash", 0.0f));
	}
}
