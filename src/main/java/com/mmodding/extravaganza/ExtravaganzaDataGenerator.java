package com.mmodding.extravaganza;

import com.mmodding.extravaganza.block.BallDistributorBlock;
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
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
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

	public static final BlockFamily HEVEA_BRASILIENSIS = BlockFamilies.register(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LOG)
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
			block.equals(ExtravaganzaBlocks.BALL_POOL_INSCRIPTION_TABLE) ||
			block.equals(ExtravaganzaBlocks.BALL_POOL_CONTENT) ||
			block.equals(ExtravaganzaBlocks.BALL_DISTRIBUTOR) ||
			block.equals(ExtravaganzaBlocks.POPCORN_MACHINE) ||
			block.equals(ExtravaganzaBlocks.GARLAND) ||
			block.equals(ExtravaganzaBlocks.PINATA);

		private static final Predicate<Item> UNCOMMON_ITEMS = item ->
			item.equals(ExtravaganzaItems.CHERRY_BALLOON) ||
			item.equals(ExtravaganzaItems.CREEPER_BALLOON) ||
			item.equals(ExtravaganzaItems.BAT);

		private ExtravaganzaModelProvider(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
			BlockStateModelGenerator.LogTexturePool log = blockStateModelGenerator.registerLog(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LOG);
			log.log(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LOG);
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
				else if (block.equals(ExtravaganzaBlocks.BALL_POOL_INSCRIPTION_TABLE) || block.equals(ExtravaganzaBlocks.POPCORN_MACHINE)) {
					blockStateModelGenerator.registerNorthDefaultHorizontalRotation(block);
					blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
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
					if (block.equals(ExtravaganzaBlocks.BALL_POOL_CONTENT) || block.equals(ExtravaganzaBlocks.BALL_DISTRIBUTOR) || block.equals(ExtravaganzaBlocks.GARLAND) || block.equals(ExtravaganzaBlocks.PINATA) || block instanceof TrashCanBlock) {
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

	public static class ExtravaganzaBlockLootTableGenerator extends FabricBlockLootTableProvider {

		public ExtravaganzaBlockLootTableGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(output, registryLookup);
		}

		@Override
		public void generate() {
			Extravaganza.executeForRegistry(Registries.BLOCK, block -> this.addDrop(block, this.drops(block)));
		}
	}

	public static class ExtravaganzaBlockTagProvider extends FabricTagProvider.BlockTagProvider {

		public ExtravaganzaBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
			Extravaganza.executeKeyForRegistry(Registries.BLOCK, key -> {
				if (!key.getValue().getPath().contains("content") && !key.getValue().getPath().contains("rubber") && !key.getValue().getPath().contains("glass") && !key.getValue().getPath().contains("garland") && !key.getValue().getPath().contains("pinata")) {
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
		}
	}
}
