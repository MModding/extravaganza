package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.AcaciaFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SurfaceWaterDepthFilterPlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer;

public class ExtravaganzaWorldGeneration {

	public static final RegistryKey<ConfiguredFeature<?, ?>> HEVEA_BRASILIENSIS = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Extravaganza.createId("hevea_brasiliensis"));

	public static final RegistryKey<PlacedFeature> HEVEA_BRASILIENSIS_CHECKED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Extravaganza.createId("hevea_brasiliensis_checked"));

	public static void register() {
		BiomeModifications.addFeature(
			BiomeSelectors.foundInOverworld(),
			GenerationStep.Feature.VEGETAL_DECORATION,
			ExtravaganzaWorldGeneration.HEVEA_BRASILIENSIS_CHECKED
		);
	}

	public static void data(RegistryBuilder registries) {
		registries.addRegistry(RegistryKeys.CONFIGURED_FEATURE, configuredFeatures -> ConfiguredFeatures.register(
			configuredFeatures,
			ExtravaganzaWorldGeneration.HEVEA_BRASILIENSIS,
			Feature.TREE,
			new TreeFeatureConfig.Builder(
				BlockStateProvider.of(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LOG),
				new ForkingTrunkPlacer(5, 2, 2),
				BlockStateProvider.of(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LEAVES),
				new AcaciaFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)),
				new TwoLayersFeatureSize(1, 0, 2)
			).ignoreVines().build()
		));
		registries.addRegistry(RegistryKeys.PLACED_FEATURE, placedFeatures -> PlacedFeatures.register(
			placedFeatures,
			ExtravaganzaWorldGeneration.HEVEA_BRASILIENSIS_CHECKED,
			placedFeatures.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE).getOrThrow(ExtravaganzaWorldGeneration.HEVEA_BRASILIENSIS),
			PlacedFeatures.createCountExtraModifier(0, 0.025f, 1),
			SquarePlacementModifier.of(),
			SurfaceWaterDepthFilterPlacementModifier.of(0),
			PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
			BiomePlacementModifier.of(),
			PlacedFeatures.wouldSurvive(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_SAPLING)
		));
	}
}
