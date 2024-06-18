package com.mmodding.extravaganza;

import com.mmodding.extravaganza.block.BallDistributorBlock;
import com.mmodding.extravaganza.block.TrashCanBlock;
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
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

public class ExtravaganzaDataGenerator implements DataGeneratorEntrypoint {

	public static final TextureKey MAIN_KEY = TextureKey.of("main");

	public static final Function<String, Model> MAIN_MODEL = path -> new Model(Optional.of(Extravaganza.createId(path)), Optional.empty(), ExtravaganzaDataGenerator.MAIN_KEY);

	public static final Function<String, Model> MAIN_PARTICLE_MODEL = path -> new Model(Optional.of(Extravaganza.createId(path)), Optional.empty(), ExtravaganzaDataGenerator.MAIN_KEY, TextureKey.PARTICLE);

	public static final Function<Block, TextureMap> MAIN = block -> TextureMap.of(ExtravaganzaDataGenerator.MAIN_KEY, TextureMap.getId(block));

	public static final Function<Block, TextureMap> MAIN_PARTICLE = block -> ExtravaganzaDataGenerator.MAIN.apply(block).put(TextureKey.PARTICLE, TextureMap.getId(block));

	public static final TexturedModel.Factory TRASH_CAN = TexturedModel.makeFactory(
		ExtravaganzaDataGenerator.MAIN_PARTICLE,
		ExtravaganzaDataGenerator.MAIN_PARTICLE_MODEL.apply("block/trash_can")
	);

	public static final TexturedModel.Factory TRASH_CAN_LID = TexturedModel.makeFactory(
		ExtravaganzaDataGenerator.MAIN,
		ExtravaganzaDataGenerator.MAIN_MODEL.apply("block/trash_can_lid")
	);

	public static final TexturedModel.Factory TRASH_CAN_LID_OPEN = TexturedModel.makeFactory(
		ExtravaganzaDataGenerator.MAIN,
		ExtravaganzaDataGenerator.MAIN_MODEL.apply("block/trash_can_lid_open")
	);

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
			translationBuilder.add("message.extravaganza.trash_can.right_click", "The player can open the trash by right-clicking.");
			translationBuilder.add("message.extravaganza.trash_can.quick_throw", "The player can throw items to trash (one by one) by sneaking + right-clicking whenever the trash is open or not.");
			translationBuilder.add("message.extravaganza.trash_can.opening_trash", "The player can throw entities to trash by putting them on top of the opened trash.");
			translationBuilder.add("message.extravaganza.trash_can.throw_whole_stack", "If the player wants to throw an entire stack, he needs to open the trash and then throw the whole stack.");
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
			block instanceof TrashCanBlock ||
			block instanceof LadderBlock ||
			block.equals(ExtravaganzaBlocks.BALL_POOL_CORE) ||
			block.equals(ExtravaganzaBlocks.BALL_POOL_CONTENT) ||
			block.equals(ExtravaganzaBlocks.BALL_DISTRIBUTOR) ||
			block.equals(ExtravaganzaBlocks.GARLAND) ||
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
						pool.stairs(Registries.BLOCK.get(identifier.withPath(string -> string + "_stairs")));
						pool.slab(Registries.BLOCK.get(identifier.withPath(string -> string + "_slab")));
						pool.wall(Registries.BLOCK.get(identifier.withPath(string -> string + "_wall")));
					}
				}
				else if (block instanceof TransparentBlock) {
					blockStateModelGenerator.registerSimpleCubeAll(block);
				}
				else if (block instanceof TrashCanBlock) {
					Identifier trashCan = ExtravaganzaDataGenerator.TRASH_CAN.upload(block, blockStateModelGenerator.modelCollector);
					Identifier trashCanLid = ExtravaganzaDataGenerator.TRASH_CAN_LID.upload(block, "_lid", blockStateModelGenerator.modelCollector);
					Identifier trashCanLidOpen = ExtravaganzaDataGenerator.TRASH_CAN_LID_OPEN.upload(block, "_lid_open", blockStateModelGenerator.modelCollector);
					blockStateModelGenerator.blockStateCollector.accept(
						MultipartBlockStateSupplier.create(block)
							.with(BlockStateVariant.create().put(VariantSettings.MODEL, trashCan))
							.with(When.create().set(TrashCanBlock.OPEN, false), BlockStateVariant.create().put(VariantSettings.MODEL, trashCanLid))
							.with(
								When.create().set(TrashCanBlock.FACING, Direction.NORTH).set(TrashCanBlock.OPEN, true),
								BlockStateVariant.create()
									.put(VariantSettings.MODEL, trashCanLidOpen)
							)
							.with(
								When.create().set(TrashCanBlock.FACING, Direction.SOUTH).set(TrashCanBlock.OPEN, true),
								BlockStateVariant.create()
									.put(VariantSettings.MODEL, trashCanLidOpen)
									.put(VariantSettings.Y, VariantSettings.Rotation.R180)
							)
							.with(
								When.create().set(TrashCanBlock.FACING, Direction.EAST).set(TrashCanBlock.OPEN, true),
								BlockStateVariant.create()
									.put(VariantSettings.MODEL, trashCanLidOpen)
									.put(VariantSettings.Y, VariantSettings.Rotation.R90)
							)
							.with(
								When.create().set(TrashCanBlock.FACING, Direction.WEST).set(TrashCanBlock.OPEN, true),
								BlockStateVariant.create()
									.put(VariantSettings.MODEL, trashCanLidOpen)
									.put(VariantSettings.Y, VariantSettings.Rotation.R270)
							)
					);
				}
				else if (block instanceof LadderBlock) {
					blockStateModelGenerator.registerNorthDefaultHorizontalRotation(block);
					blockStateModelGenerator.registerItemModel(block);
				}
				else if (block.equals(ExtravaganzaBlocks.GARLAND)) {
					Identifier garland = Extravaganza.createId("block/garland");
					Identifier horizontal = Extravaganza.createId("block/garland_horizontal");
					Identifier vertical = Extravaganza.createId("block/garland_vertical");
					Identifier up = Extravaganza.createId("block/garland_up");
					Identifier down = Extravaganza.createId("block/garland_down");
					Identifier north = Extravaganza.createId("block/garland_north");
					Identifier south = Extravaganza.createId("block/garland_south");
					Identifier west = Extravaganza.createId("block/garland_west");
					Identifier east = Extravaganza.createId("block/garland_east");
					When none = When.allOf(
						When.create().set(Properties.UP, false),
						When.create().set(Properties.DOWN, false),
						When.create().set(Properties.NORTH, false),
						When.create().set(Properties.SOUTH, false),
						When.create().set(Properties.WEST, false),
						When.create().set(Properties.EAST, false)
					);
					blockStateModelGenerator.blockStateCollector.accept(
						MultipartBlockStateSupplier.create(ExtravaganzaBlocks.GARLAND)
							.with(
								When.anyOf(
									none,
									When.anyOf(
										When.create().set(Properties.UP, true),
										When.create().set(Properties.DOWN, true),
										When.create().set(Properties.NORTH, true),
										When.create().set(Properties.SOUTH, true)
									)
								),
								BlockStateVariant.create().put(VariantSettings.MODEL, garland)
							)
							.with(
								When.anyOf(
									none,
									When.anyOf(
										When.create().set(Properties.NORTH, true),
										When.create().set(Properties.SOUTH, true),
										When.create().set(Properties.WEST, true),
										When.create().set(Properties.EAST, true)
									)
								),
								BlockStateVariant.create().put(VariantSettings.MODEL, horizontal)
							)
							.with(
								When.anyOf(
									none,
									When.anyOf(
										When.create().set(Properties.UP, true),
										When.create().set(Properties.DOWN, true),
										When.create().set(Properties.WEST, true),
										When.create().set(Properties.EAST, true)
									)
								),
								BlockStateVariant.create().put(VariantSettings.MODEL, vertical)
							)
							.with(When.create().set(Properties.UP, true), BlockStateVariant.create().put(VariantSettings.MODEL, up))
							.with(When.create().set(Properties.DOWN, true), BlockStateVariant.create().put(VariantSettings.MODEL, down))
							.with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, north))
							.with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, south))
							.with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, west))
							.with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, east))
					);
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
