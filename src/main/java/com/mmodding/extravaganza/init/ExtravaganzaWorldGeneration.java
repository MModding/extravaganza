package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.AcaciaFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer;

import java.util.List;

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

	public static void callback(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures) {
		Registry.register(
			configuredFeatures,
			ExtravaganzaWorldGeneration.HEVEA_BRASILIENSIS,
			new ConfiguredFeature<>(
				Feature.TREE,
				new TreeFeatureConfig.Builder(
					BlockStateProvider.of(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LOG),
					new ForkingTrunkPlacer(5, 2, 2),
					BlockStateProvider.of(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LEAVES),
					new AcaciaFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)),
					new TwoLayersFeatureSize(1, 0, 2)
				).ignoreVines().build()
			)
		);
		Registry.register(
			placedFeatures,
			ExtravaganzaWorldGeneration.HEVEA_BRASILIENSIS_CHECKED,
			new PlacedFeature(
				configuredFeatures.getEntry(ExtravaganzaWorldGeneration.HEVEA_BRASILIENSIS).orElseThrow(),
				List.of(PlacedFeatures.wouldSurvive(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_SAPLING))
			)
		);
	}
}
