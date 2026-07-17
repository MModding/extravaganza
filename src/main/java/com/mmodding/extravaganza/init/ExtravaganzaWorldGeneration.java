package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.library.core.api.AdvancedContainer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ExtravaganzaWorldGeneration {

	public static final ResourceKey<ConfiguredFeature<?, ?>> HEVEA_BRASILIENSIS = Extravaganza.createKey(Registries.CONFIGURED_FEATURE, "hevea_brasiliensis");

	public static final ResourceKey<PlacedFeature> HEVEA_BRASILIENSIS_CHECKED = Extravaganza.createKey(Registries.PLACED_FEATURE, "hevea_brasiliensis_checked");

	public static void register(AdvancedContainer mod) {
		BiomeModifications.addFeature(
			BiomeSelectors.foundInOverworld(),
			GenerationStep.Decoration.VEGETAL_DECORATION,
			ExtravaganzaWorldGeneration.HEVEA_BRASILIENSIS_CHECKED
		);
	}
}
