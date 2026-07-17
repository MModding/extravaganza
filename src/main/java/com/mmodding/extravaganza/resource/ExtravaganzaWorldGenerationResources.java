package com.mmodding.extravaganza.resource;

import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.extravaganza.init.ExtravaganzaWorldGeneration;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.levelgen.api.feature.FeaturePack;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;

public class ExtravaganzaWorldGenerationResources {

	private static final FeaturePack<TreeConfiguration> HEVEA_BRASILIENSIS = FeaturePack.of(Feature.TREE)
		.appendConfiguredFeature(
			ExtravaganzaWorldGeneration.HEVEA_BRASILIENSIS,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(ExtravaganzaBlocks.HEVEA_BRASILIENSIS.getLog()),
				new ForkingTrunkPlacer(5, 2, 2),
				BlockStateProvider.simple(ExtravaganzaBlocks.HEVEA_BRASILIENSIS.getLeaves()),
				new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
				new TwoLayersFeatureSize(1, 0, 2),
				BlockStateProvider.simple(Blocks.DIRT)
			).ignoreVines().build(),
			pack -> pack
				.appendPlacedFeature(
					ExtravaganzaWorldGeneration.HEVEA_BRASILIENSIS_CHECKED,
					PlacementUtils.countExtra(0, 0.025f, 1),
					InSquarePlacement.spread(),
					SurfaceWaterDepthFilter.forMaxDepth(0),
					PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
					BiomeFilter.biome(),
					PlacementUtils.filteredByBlockSurvival(ExtravaganzaBlocks.HEVEA_BRASILIENSIS.getSapling())
				)
		);

	public static void configureFeatures(AdvancedContainer mod, BootstrapContext<ConfiguredFeature<?, ?>> configuredFeatures) {
		HEVEA_BRASILIENSIS.registerConfigs(configuredFeatures);
	}

	public static void configurePlacements(AdvancedContainer mod, BootstrapContext<PlacedFeature> placedFeatures) {
		HEVEA_BRASILIENSIS.registerPlacements(placedFeatures);
	}
}
