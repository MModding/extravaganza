package com.mmodding.extravaganza;

import com.mmodding.extravaganza.block.BallDistributorBlock;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.client.*;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class ExtravaganzaDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ExtravaganzaLanguageProvider::new);
		pack.addProvider(ExtravaganzaModelProvider::new);
		pack.addProvider(ExtravaganzaRecipeProvider::new);
	}

	public static class ExtravaganzaLanguageProvider extends FabricLanguageProvider {

		private ExtravaganzaLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
			translationBuilder.add("itemGroup.extravaganza.main", "Extravaganza!");
			Extravaganza.executeKeyForRegistry(Registries.ITEM, key -> translationBuilder.add(Registries.ITEM.get(key), this.makeItReadable(key)));
		}

		private <T> String makeItReadable(RegistryKey<T> key) {
			String path = key.getValue().getPath();
			String[] words = path.split("_");
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < words.length; i++) {
				String word = words[i];
				builder.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
				if (i != words.length - 1) {
					builder.append(" ");
				}
			}
			builder.delete(builder.toString().length() - 1, builder.toString().length() - 1);
			return builder.toString();
		}
	}

	public static class ExtravaganzaModelProvider extends FabricModelProvider {

		private static final Predicate<Block> UNCOMMON_BLOCKS = block ->
			block instanceof TransparentBlock ||
			block instanceof LadderBlock ||
			block.equals(ExtravaganzaBlocks.BALL_POOL_CORE) ||
			block.equals(ExtravaganzaBlocks.BALL_POOL_CONTENT) ||
			block.equals(ExtravaganzaBlocks.BALL_DISTRIBUTOR) ||
			block.equals(ExtravaganzaBlocks.PINATA);

		private static final Predicate<Item> UNCOMMON_ITEMS = item ->
			item.equals(ExtravaganzaItems.BAT);

		private ExtravaganzaModelProvider(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
			Extravaganza.executeForRegistry(Registries.BLOCK, block -> {
				if (!ExtravaganzaModelProvider.UNCOMMON_BLOCKS.test(block)) {
					if (!(block instanceof StairsBlock) && !(block instanceof SlabBlock) && !(block instanceof WallBlock)) {
						BlockStateModelGenerator.BlockTexturePool pool = blockStateModelGenerator.registerCubeAllModelTexturePool(block);
						Identifier identifier = Registries.BLOCK.getId(block);
						Extravaganza.getLogger().info(identifier.toString());
						pool.stairs(Registries.BLOCK.get(identifier.withPath(string -> string + "_stairs")));
						pool.slab(Registries.BLOCK.get(identifier.withPath(string -> string + "_slab")));
						pool.wall(Registries.BLOCK.get(identifier.withPath(string -> string + "_wall")));
					}
				}
				else if (block instanceof TransparentBlock) {
					blockStateModelGenerator.registerSimpleCubeAll(block);
				}
				else if (block instanceof LadderBlock) {
					blockStateModelGenerator.registerNorthDefaultHorizontalRotation(block);
					blockStateModelGenerator.registerItemModel(block);
				}
				else if (block.equals(ExtravaganzaBlocks.BALL_DISTRIBUTOR)) {
					Identifier up = Extravaganza.createId("block/ball_distributor_up");
					Identifier down = Extravaganza.createId("block/ball_distributor_down");
					blockStateModelGenerator.blockStateCollector.accept(
						VariantsBlockStateSupplier.create(block)
							.coordinate(
								BlockStateVariantMap.create(BallDistributorBlock.FACING, BallDistributorBlock.HALF)
									.register(
										Direction.NORTH,
										DoubleBlockHalf.UPPER,
										BlockStateVariant.create()
											.put(VariantSettings.MODEL, up)
									)
									.register(
										Direction.EAST,
										DoubleBlockHalf.UPPER,
										BlockStateVariant.create()
											.put(VariantSettings.MODEL, up)
											.put(VariantSettings.Y, VariantSettings.Rotation.R90)
									)
									.register(
										Direction.SOUTH,
										DoubleBlockHalf.UPPER,
										BlockStateVariant.create()
											.put(VariantSettings.MODEL, up)
											.put(VariantSettings.Y, VariantSettings.Rotation.R180)
									)
									.register(
										Direction.WEST,
										DoubleBlockHalf.UPPER,
										BlockStateVariant.create()
											.put(VariantSettings.MODEL, up)
											.put(VariantSettings.Y, VariantSettings.Rotation.R270)
									)
									.register(
										Direction.NORTH,
										DoubleBlockHalf.LOWER,
										BlockStateVariant.create()
											.put(VariantSettings.MODEL, down)
									)
									.register(
										Direction.EAST,
										DoubleBlockHalf.LOWER,
										BlockStateVariant.create()
											.put(VariantSettings.MODEL, down)
											.put(VariantSettings.Y, VariantSettings.Rotation.R90)
									)
									.register(
										Direction.SOUTH,
										DoubleBlockHalf.LOWER,
										BlockStateVariant.create()
											.put(VariantSettings.MODEL, down)
											.put(VariantSettings.Y, VariantSettings.Rotation.R180)
									)
									.register(
										Direction.WEST,
										DoubleBlockHalf.LOWER,
										BlockStateVariant.create()
											.put(VariantSettings.MODEL, down)
											.put(VariantSettings.Y, VariantSettings.Rotation.R270)
									)
							)
					);
				}
				else if (block.equals(ExtravaganzaBlocks.BALL_POOL_CONTENT) || block.equals(ExtravaganzaBlocks.PINATA)) {
					blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
				}
			});
		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
			Extravaganza.executeForRegistry(Registries.ITEM, item -> {
				if (!(item instanceof BlockItem) && !ExtravaganzaModelProvider.UNCOMMON_ITEMS.test(item)) {
					itemModelGenerator.register(item, Models.GENERATED);
				}
			});
		}
	}

	public static class ExtravaganzaRecipeProvider extends FabricRecipeProvider {

		public ExtravaganzaRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		public void generate(RecipeExporter exporter) {
			ExtravaganzaColor.VALUES.forEach(color -> {
				Item item = Registries.ITEM.get(Extravaganza.createId(color.asString() + "_festive_rubber"));
				if (!color.equals(ExtravaganzaColor.PLANT) && !color.equals(ExtravaganzaColor.TOMATO) && !color.equals(ExtravaganzaColor.TEAR) && !color.equals(ExtravaganzaColor.NYMPH)) {
					ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, item, 16)
						.criterion(ExtravaganzaRecipeProvider.hasItem(item), ExtravaganzaRecipeProvider.conditionsFromItem(item))
						.input('C', Registries.ITEM.get(Identifier.of(color.asString() + "_dye")))
						.input('R', ExtravaganzaItems.RUBBER)
						.pattern("CRC")
						.pattern("RCR")
						.pattern("CRC")
						.offerTo(exporter);
				}
				Set.of(
					"striped", "poured", "sharped", "scratched",
					"dotted", "screwed", "split", "wooded",
					"barred", "perforated", "slipped", "padded",
					"curved", "bent", "windowed", "tiled"
				).forEach(
					prefix -> StonecuttingRecipeJsonBuilder.createStonecutting(
						Ingredient.ofItems(item),
						RecipeCategory.BUILDING_BLOCKS,
						Registries.ITEM.get(Extravaganza.createId(color.asString() + "_" + prefix + "_festive_rubber"))
					).criterion(ExtravaganzaRecipeProvider.hasItem(item), ExtravaganzaRecipeProvider.conditionsFromItem(item)).offerTo(exporter)
				);
				StonecuttingRecipeJsonBuilder.createStonecutting(
					Ingredient.ofItems(item),
					RecipeCategory.BUILDING_BLOCKS,
					Registries.ITEM.get(Extravaganza.createId(color.asString() + "_festive_rubber_grate"))
				).criterion(ExtravaganzaRecipeProvider.hasItem(item), ExtravaganzaRecipeProvider.conditionsFromItem(item)).offerTo(exporter);
			});
		}
	}
}
