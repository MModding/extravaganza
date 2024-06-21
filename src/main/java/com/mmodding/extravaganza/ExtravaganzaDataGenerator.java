package com.mmodding.extravaganza;

import com.mmodding.extravaganza.block.BallDistributorBlock;
import com.mmodding.extravaganza.block.HeveaBrasiliensisLog;
import com.mmodding.extravaganza.block.TrashCanBlock;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.client.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

public class ExtravaganzaDataGenerator implements DataGeneratorEntrypoint {

	public static final BlockFamily HEVEA_BRASILIENSIS = BlockFamilies.register(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_PLANKS)
		.button(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_BUTTON)
		.fence(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_FENCE)
		.fenceGate(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_FENCE_GATE)
		.pressurePlate(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_PRESSURE_PLATE)
		.slab(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_SLAB)
		.stairs(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_STAIRS)
		.door(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_DOOR)
		.trapdoor(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_TRAPDOOR)
		.group("wooden")
		.unlockCriterionName("has_planks")
		.build();

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
		pack.addProvider(ExtravaganzaBlockLootTableGenerator::new);
		pack.addProvider(ExtravaganzaBlockTagProvider::new);
		pack.addProvider(ExtravaganzaItemTagProvider::new);
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
			translationBuilder.add("painting.extravaganza.reflect.author", "Aeramisu");
			translationBuilder.add("painting.extravaganza.reflect.title", "Reflect");
			translationBuilder.add("tag.item.extravaganza.festive_balls", "Festive Balls");
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
			block.equals(ExtravaganzaBlocks.BALL_POOL_REGISTRATION_TABLE) ||
			block.equals(ExtravaganzaBlocks.BALL_POOL_CONTENT) ||
			block.equals(ExtravaganzaBlocks.BALL_POOL_PROTECTION) ||
			block.equals(ExtravaganzaBlocks.BALL_DISTRIBUTOR) ||
			block.equals(ExtravaganzaBlocks.POPCORN_MACHINE) ||
			block.equals(ExtravaganzaBlocks.GARLAND) ||
			block.equals(ExtravaganzaBlocks.PINATA);

		private final static Set<Block> WITH_GENERATED_ITEM = Set.of(
			ExtravaganzaBlocks.BALL_POOL_CONTENT,
			ExtravaganzaBlocks.BALL_DISTRIBUTOR,
			ExtravaganzaBlocks.GARLAND,
			ExtravaganzaBlocks.PINATA,
			ExtravaganzaBlocks.POPCORN_MACHINE
		);

		private static final Predicate<Item> UNCOMMON_ITEMS = item -> item.equals(ExtravaganzaItems.BAT);

		private ExtravaganzaModelProvider(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
			Block logBlock = ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LOG;
			Identifier cubeColumn = Models.CUBE_COLUMN.upload(logBlock, TextureMap.sideAndEndForTop(logBlock), blockStateModelGenerator.modelCollector);
			Identifier cubeColumnHorizontal = Models.CUBE_COLUMN_HORIZONTAL.upload(logBlock, TextureMap.sideAndEndForTop(logBlock), blockStateModelGenerator.modelCollector);
			Identifier cubeColumnRubber = Models.CUBE_COLUMN.upload(
				logBlock,
				"_rubber",
				new TextureMap()
					.put(TextureKey.SIDE, TextureMap.getSubId(logBlock, "_rubber"))
					.put(TextureKey.END, TextureMap.getSubId(logBlock, "_top"))
					.put(TextureKey.PARTICLE, TextureMap.getSubId(logBlock, "rubber")),
				blockStateModelGenerator.modelCollector
			);
			VariantsBlockStateSupplier supplier = VariantsBlockStateSupplier.create(logBlock)
				.coordinate(
					BlockStateVariantMap.create(HeveaBrasiliensisLog.AXIS, HeveaBrasiliensisLog.RUBBER)
						.register(
							Direction.Axis.Y,
							false,
							BlockStateVariant.create()
								.put(VariantSettings.MODEL, cubeColumn)
						)
						.register(
							Direction.Axis.Z,
							false,
							BlockStateVariant.create()
								.put(VariantSettings.MODEL, cubeColumnHorizontal)
								.put(VariantSettings.X, VariantSettings.Rotation.R90)
						)
						.register(
							Direction.Axis.X,
							false,
							BlockStateVariant.create()
								.put(VariantSettings.MODEL, cubeColumnHorizontal)
								.put(VariantSettings.X, VariantSettings.Rotation.R90)
								.put(VariantSettings.Y, VariantSettings.Rotation.R90)
						)
						.register(
							Direction.Axis.Y,
							true,
							BlockStateVariant.create()
								.put(VariantSettings.MODEL, cubeColumnRubber)
						)
						.register(
							Direction.Axis.Z,
							true,
							BlockStateVariant.create()
								.put(VariantSettings.MODEL, cubeColumnHorizontal)
								.put(VariantSettings.X, VariantSettings.Rotation.R90)
						)
						.register(
							Direction.Axis.X,
							true,
							BlockStateVariant.create()
								.put(VariantSettings.MODEL, cubeColumnHorizontal)
								.put(VariantSettings.X, VariantSettings.Rotation.R90)
								.put(VariantSettings.Y, VariantSettings.Rotation.R90)
						)
				);
			blockStateModelGenerator.blockStateCollector.accept(supplier);
			BlockStateModelGenerator.LogTexturePool log = blockStateModelGenerator.registerLog(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LOG);
			log.wood(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_WOOD);
			BlockStateModelGenerator.LogTexturePool stripped = blockStateModelGenerator.registerLog(ExtravaganzaBlocks.STRIPPED_HEVEA_BRASILIENSIS_LOG);
			stripped.log(ExtravaganzaBlocks.STRIPPED_HEVEA_BRASILIENSIS_LOG);
			stripped.wood(ExtravaganzaBlocks.STRIPPED_HEVEA_BRASILIENSIS_WOOD);
			BlockStateModelGenerator.BlockTexturePool planks = blockStateModelGenerator.registerCubeAllModelTexturePool(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_PLANKS);
			planks.stairs(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_STAIRS);
			planks.slab(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_SLAB);
			planks.fence(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_FENCE);
			planks.fenceGate(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_FENCE_GATE);
			blockStateModelGenerator.registerDoor(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_DOOR);
			blockStateModelGenerator.registerTrapdoor(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_TRAPDOOR);
			planks.pressurePlate(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_PRESSURE_PLATE);
			planks.button(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_BUTTON);
			blockStateModelGenerator.registerSingleton(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LEAVES, TexturedModel.LEAVES);
			blockStateModelGenerator.registerSimpleState(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_SAPLING);
			blockStateModelGenerator.registerItemModel(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_SAPLING);
			Extravaganza.executeForRegistry(Registries.BLOCK, block -> {
				if (!ExtravaganzaModelProvider.UNCOMMON_BLOCKS.test(block) && !Registries.BLOCK.getId(block).getPath().contains("hevea_brasiliensis")) {
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
				else if (block.equals(ExtravaganzaBlocks.BALL_POOL_REGISTRATION_TABLE) || block.equals(ExtravaganzaBlocks.POPCORN_MACHINE)) {
					blockStateModelGenerator.registerNorthDefaultHorizontalRotation(block);
					if (block.equals(ExtravaganzaBlocks.BALL_POOL_REGISTRATION_TABLE)) {
						blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
					}
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
			});
		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
			Extravaganza.executeForRegistry(Registries.ITEM, item -> {
				if (item instanceof BlockItem blockItem) {
					Block block = blockItem.getBlock();
					if (ExtravaganzaModelProvider.WITH_GENERATED_ITEM.contains(block) || block instanceof TrashCanBlock) {
						itemModelGenerator.register(item, Models.GENERATED);
					}
				}
				else if (!ExtravaganzaModelProvider.UNCOMMON_ITEMS.test(item)) {
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
			ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ExtravaganzaBlocks.HEVEA_BRASILIENSIS_PLANKS, 4)
				.input(Ingredient.ofItems(
					ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LOG,
					ExtravaganzaBlocks.HEVEA_BRASILIENSIS_WOOD,
					ExtravaganzaBlocks.STRIPPED_HEVEA_BRASILIENSIS_LOG,
					ExtravaganzaBlocks.STRIPPED_HEVEA_BRASILIENSIS_WOOD
				))
				.group("planks")
				.criterion("has_logs", ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LOG))
				.offerTo(exporter);
			ExtravaganzaRecipeProvider.offerBarkBlockRecipe(exporter, ExtravaganzaBlocks.HEVEA_BRASILIENSIS_WOOD, ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LOG);
			ExtravaganzaRecipeProvider.offerBarkBlockRecipe(exporter, ExtravaganzaBlocks.STRIPPED_HEVEA_BRASILIENSIS_WOOD, ExtravaganzaBlocks.STRIPPED_HEVEA_BRASILIENSIS_LOG);
			ExtravaganzaRecipeProvider.generateFamily(exporter, ExtravaganzaDataGenerator.HEVEA_BRASILIENSIS, FeatureSet.of(FeatureFlags.VANILLA));
			ExtravaganzaColor.VALUES.forEach(color -> {
				Item festiveRubber = Registries.ITEM.get(Extravaganza.createId(color.asString() + "_festive_rubber"));
				Item festiveRubberStairs = Registries.ITEM.get(Extravaganza.createId(color.asString() + "_festive_rubber_stairs"));
				Item festiveRubberSlab = Registries.ITEM.get(Extravaganza.createId(color.asString() + "_festive_rubber_slab"));
				Item festiveRubberWall = Registries.ITEM.get(Extravaganza.createId(color.asString() + "_festive_rubber_wall"));
				Item trashCan = Registries.ITEM.get(Extravaganza.createId(color.asString() + "_festive_rubber_ladder"));
				Item ladder = Registries.ITEM.get(Extravaganza.createId(color.asString() + "_trash_can"));
				if (!color.equals(ExtravaganzaColor.PLANT) && !color.equals(ExtravaganzaColor.TOMATO) && !color.equals(ExtravaganzaColor.TEAR) && !color.equals(ExtravaganzaColor.NYMPH)) {
					ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, festiveRubber, 16)
						.criterion(ExtravaganzaRecipeProvider.hasItem(festiveRubber), ExtravaganzaRecipeProvider.conditionsFromItem(festiveRubber))
						.input('C', Registries.ITEM.get(Identifier.of(color.asString() + "_dye")))
						.input('R', ExtravaganzaItems.RUBBER)
						.pattern("CRC")
						.pattern("RCR")
						.pattern("CRC")
						.offerTo(exporter);
				}
				ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, festiveRubberStairs, 4)
					.criterion(ExtravaganzaRecipeProvider.hasItem(festiveRubberStairs), ExtravaganzaRecipeProvider.conditionsFromItem(festiveRubberStairs))
					.input('R', festiveRubber)
					.pattern("R  ")
					.pattern("RR ")
					.pattern("RRR")
					.offerTo(exporter);
				ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, festiveRubberSlab, 6)
					.criterion(ExtravaganzaRecipeProvider.hasItem(festiveRubberSlab), ExtravaganzaRecipeProvider.conditionsFromItem(festiveRubberSlab))
					.input('R', festiveRubber)
					.pattern("RRR")
					.offerTo(exporter);
				ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, festiveRubberWall, 6)
					.criterion(ExtravaganzaRecipeProvider.hasItem(festiveRubberWall), ExtravaganzaRecipeProvider.conditionsFromItem(festiveRubberWall))
					.input('R', festiveRubber)
					.pattern("RRR")
					.pattern("RRR")
					.offerTo(exporter);
				ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, trashCan, 4)
					.criterion(ExtravaganzaRecipeProvider.hasItem(trashCan), ExtravaganzaRecipeProvider.conditionsFromItem(trashCan))
					.input('R', festiveRubber)
					.input('S', Items.STICK)
					.pattern("R R")
					.pattern("SRS")
					.pattern("R R")
					.offerTo(exporter);
				ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ladder, 16)
					.criterion(ExtravaganzaRecipeProvider.hasItem(ladder), ExtravaganzaRecipeProvider.conditionsFromItem(ladder))
					.input('R', festiveRubber)
					.input('H', Items.HOPPER)
					.pattern("R R")
					.pattern("RHR")
					.pattern(" R ")
					.offerTo(exporter);
				Set.of(
					"striped", "poured", "sharped", "scratched",
					"dotted", "screwed", "split", "wooded",
					"barred", "perforated", "slipped", "padded",
					"curved", "bent", "windowed", "tiled"
				).forEach(
					prefix -> StonecuttingRecipeJsonBuilder.createStonecutting(
						Ingredient.ofItems(festiveRubber),
						RecipeCategory.BUILDING_BLOCKS,
						Registries.ITEM.get(Extravaganza.createId(color.asString() + "_" + prefix + "_festive_rubber"))
					).criterion(ExtravaganzaRecipeProvider.hasItem(festiveRubber), ExtravaganzaRecipeProvider.conditionsFromItem(festiveRubber)).offerTo(exporter)
				);
				StonecuttingRecipeJsonBuilder.createStonecutting(
					Ingredient.ofItems(festiveRubber),
					RecipeCategory.BUILDING_BLOCKS,
					Registries.ITEM.get(Extravaganza.createId(color.asString() + "_festive_rubber_grate"))
				).criterion(ExtravaganzaRecipeProvider.hasItem(festiveRubber), ExtravaganzaRecipeProvider.conditionsFromItem(festiveRubber)).offerTo(exporter);
			});
			ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ExtravaganzaItems.WRENCH_AGANZA)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaItems.WRENCH_AGANZA), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaItems.WRENCH_AGANZA))
				.input(ExtravaganzaItems.GOLDEN_CANDY_CANE)
				.input(ExtravaganzaItems.GREEN_CANDY_CANE)
				.input(ExtravaganzaItems.RED_CANDY_CANE)
				.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ExtravaganzaItems.RUBBER_EXTRACTOR)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaItems.RUBBER_EXTRACTOR), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaItems.RUBBER_EXTRACTOR))
				.input('I', Items.IRON_INGOT)
				.pattern("III")
				.pattern("I  ")
				.pattern("I  ")
				.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ExtravaganzaItems.GOLDEN_CANDY_CANE)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaItems.GOLDEN_CANDY_CANE), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaItems.GOLDEN_CANDY_CANE))
				.input('R', Items.YELLOW_DYE)
				.input('S', Items.SUGAR)
				.pattern("RS ")
				.pattern("S R")
				.pattern("R  ")
				.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ExtravaganzaItems.GREEN_CANDY_CANE)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaItems.GREEN_CANDY_CANE), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaItems.GREEN_CANDY_CANE))
				.input('R', Items.GREEN_DYE)
				.input('S', Items.SUGAR)
				.pattern("RS ")
				.pattern("S R")
				.pattern("R  ")
				.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ExtravaganzaItems.RED_CANDY_CANE)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaItems.RED_CANDY_CANE), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaItems.RED_CANDY_CANE))
				.input('R', Items.RED_DYE)
				.input('S', Items.SUGAR)
				.pattern("RS ")
				.pattern("S R")
				.pattern("R  ")
				.offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ExtravaganzaItems.HOT_DOG)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaItems.HOT_DOG), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaItems.HOT_DOG))
				.input(Items.COOKED_BEEF)
				.input(Items.BREAD)
				.offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ExtravaganzaItems.HOT_DOG_WITH_MAYONNAISE)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaItems.HOT_DOG_WITH_MAYONNAISE), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaItems.HOT_DOG_WITH_MAYONNAISE))
				.input(ExtravaganzaItems.HOT_DOG)
				.input(Items.EGG)
				.offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ExtravaganzaItems.EMPTY_POPCORN, 4)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaItems.EMPTY_POPCORN), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaItems.EMPTY_POPCORN))
				.input(Items.RED_DYE)
				.input(Items.PAPER)
				.input(Items.YELLOW_DYE)
				.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ExtravaganzaItems.CHERRY_BALLOON, 16)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaItems.CHERRY_BALLOON), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaItems.CHERRY_BALLOON))
				.input('D', Items.RED_DYE)
				.input('P', Items.PAPER)
				.input('S', Items.STRING)
				.pattern("DP ")
				.pattern("PD ")
				.pattern("  S")
				.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ExtravaganzaItems.CREEPER_BALLOON, 16)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaItems.CREEPER_BALLOON), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaItems.CREEPER_BALLOON))
				.input('D', Items.GREEN_DYE)
				.input('P', Items.PAPER)
				.input('S', Items.STRING)
				.pattern("DP ")
				.pattern("PD ")
				.pattern("  S")
				.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ExtravaganzaItems.BAT)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaItems.BAT), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaItems.BAT))
				.input('B', Items.LIGHT_BLUE_DYE)
				.input('Y', Items.YELLOW_DYE)
				.input('R', Items.RED_DYE)
				.input('S', Items.STICK)
				.pattern("BSB")
				.pattern("YSY")
				.pattern("RSR")
				.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ExtravaganzaItems.MERRY_GO_ROUND)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaItems.MERRY_GO_ROUND), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaItems.MERRY_GO_ROUND))
				.input('I', Items.IRON_INGOT)
				.input('R', Registries.ITEM.get(Extravaganza.createId("red_festive_rubber")))
				.input('S', Registries.ITEM.get(Extravaganza.createId("red_festive_rubber_slab")))
				.pattern("III")
				.pattern("RRR")
				.pattern("SRS")
				.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ExtravaganzaBlocks.BALL_POOL_REGISTRATION_TABLE.asItem())
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaBlocks.BALL_POOL_REGISTRATION_TABLE.asItem()), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaBlocks.BALL_POOL_REGISTRATION_TABLE.asItem()))
				.input('P', Items.PAPER)
				.input('I', Items.INK_SAC)
				.input('F', Items.FLOWER_POT)
				.input('H', ExtravaganzaBlocks.HEVEA_BRASILIENSIS_PLANKS.asItem())
				.pattern("PIF")
				.pattern("HHH")
				.pattern("H H")
				.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ExtravaganzaBlocks.BALL_POOL_CONTENT.asItem(), 16)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaBlocks.BALL_POOL_CONTENT.asItem()), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaBlocks.BALL_POOL_CONTENT.asItem()))
				.input('B', TagKey.of(RegistryKeys.ITEM, Extravaganza.createId("festive_balls")))
				.pattern("BBB")
				.pattern("BBB")
				.pattern("BBB")
				.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ExtravaganzaBlocks.BALL_DISTRIBUTOR.asItem())
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaBlocks.BALL_DISTRIBUTOR.asItem()), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaBlocks.BALL_DISTRIBUTOR.asItem()))
				.input('R', Registries.ITEM.get(Extravaganza.createId("red_festive_rubber")))
				.input('S', Registries.ITEM.get(Extravaganza.createId("red_festive_rubber_slab")))
				.input('G', Items.GLASS)
				.input('B', Registries.ITEM.get(Extravaganza.createId("black_festive_rubber")))
				.pattern("SRS")
				.pattern("GGG")
				.pattern("BBB")
				.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ExtravaganzaBlocks.POPCORN_MACHINE.asItem())
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaBlocks.POPCORN_MACHINE.asItem()), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaBlocks.POPCORN_MACHINE.asItem()))
				.input('B', Registries.ITEM.get(Extravaganza.createId("blue_festive_rubber")))
				.input('D', Registries.ITEM.get(Extravaganza.createId("blue_festive_rubber_slab")))
				.input('R', Registries.ITEM.get(Extravaganza.createId("red_festive_rubber")))
				.input('S', Registries.ITEM.get(Extravaganza.createId("red_festive_rubber_slab")))
				.pattern("BBB")
				.pattern("DRD")
				.pattern("SRS")
				.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ExtravaganzaBlocks.GARLAND.asItem(), 32)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaBlocks.GARLAND.asItem()), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaBlocks.GARLAND.asItem()))
				.input('R', Items.RED_DYE)
				.input('O', Items.ORANGE_DYE)
				.input('L', Items.LIME_DYE)
				.input('Y', Items.YELLOW_DYE)
				.input('B', Items.BLUE_DYE)
				.input('S', Items.STRING)
				.pattern("RSO")
				.pattern("SLS")
				.pattern("YSB")
				.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ExtravaganzaBlocks.PINATA.asItem(), 8)
				.criterion(ExtravaganzaRecipeProvider.hasItem(ExtravaganzaBlocks.PINATA.asItem()), ExtravaganzaRecipeProvider.conditionsFromItem(ExtravaganzaBlocks.PINATA.asItem()))
				.input('B', Registries.ITEM.get(Extravaganza.createId("blue_festive_rubber")))
				.input('G', Registries.ITEM.get(Extravaganza.createId("green_festive_rubber")))
				.input('Y', Registries.ITEM.get(Extravaganza.createId("yellow_festive_rubber")))
				.input('O', Registries.ITEM.get(Extravaganza.createId("orange_festive_rubber")))
				.input('R', Registries.ITEM.get(Extravaganza.createId("red_festive_rubber")))
				.pattern("  B")
				.pattern("  G")
				.pattern("ROY")
				.offerTo(exporter);
		}
	}

	public static class ExtravaganzaBlockLootTableGenerator extends FabricBlockLootTableProvider {

		public ExtravaganzaBlockLootTableGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(output, registryLookup);
		}

		@Override
		public void generate() {
			Extravaganza.executeForRegistry(Registries.BLOCK, block -> {
				if (block instanceof LeavesBlock) {
					this.addDrop(block, this.leavesDrops(block, ExtravaganzaBlocks.HEVEA_BRASILIENSIS_SAPLING, 0.05f, 0.0625f, 0.083333336f, 0.1f));
				}
				else {
					this.addDrop(block, this.drops(block));
				}
			});
		}
	}

	public static class ExtravaganzaBlockTagProvider extends FabricTagProvider.BlockTagProvider {

		public ExtravaganzaBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
			Extravaganza.executeKeyForRegistry(Registries.BLOCK, key -> {
				if (key.getValue().getPath().contains("hevea_brasiliensis") || key.getValue().getPath().contains("registration")) {
					this.getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(Registries.BLOCK.get(key));
					this.getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(Registries.BLOCK.get(key));
				}
				else if (!key.getValue().getPath().contains("content") && !key.getValue().getPath().contains("rubber") && !key.getValue().getPath().contains("glass") && !key.getValue().getPath().contains("garland") && !key.getValue().getPath().contains("pinata")) {
					this.getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(Registries.BLOCK.get(key));
					this.getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(Registries.BLOCK.get(key));
				}
			});
			this.getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
				.add(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LOG)
				.add(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_WOOD)
				.add(ExtravaganzaBlocks.STRIPPED_HEVEA_BRASILIENSIS_LOG)
				.add(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_WOOD);
			this.getOrCreateTagBuilder(BlockTags.PLANKS)
				.add(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_PLANKS);
			this.getOrCreateTagBuilder(BlockTags.LEAVES)
				.add(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LEAVES);
			FabricTagProvider<Block>.FabricTagBuilder climbable = this.getOrCreateTagBuilder(BlockTags.CLIMBABLE);
			FabricTagProvider<Block>.FabricTagBuilder fences = this.getOrCreateTagBuilder(BlockTags.FENCES);
			FabricTagProvider<Block>.FabricTagBuilder walls = this.getOrCreateTagBuilder(BlockTags.WALLS);
			Extravaganza.executeKeyForRegistry(Registries.BLOCK, key -> {
				if (key.getValue().getPath().contains("ladder")) {
					climbable.add(Registries.BLOCK.get(key));
				}
				if (key.getValue().getPath().contains("fence")) {
					fences.add(Registries.BLOCK.get(key));
				}
				if (key.getValue().getPath().contains("wall")) {
					walls.add(Registries.BLOCK.get(key));
				}
			});
		}
	}

	public static class ExtravaganzaItemTagProvider extends FabricTagProvider.ItemTagProvider {

		public ExtravaganzaItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
			FabricTagProvider<Item>.FabricTagBuilder builder = this.getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Extravaganza.createId("festive_balls")));
			Extravaganza.executeKeyForRegistry(Registries.ITEM, key -> {
				if (key.getValue().getPath().contains("festive_ball")) {
					builder.add(Registries.ITEM.get(key));
				}
			});
		}
	}
}
