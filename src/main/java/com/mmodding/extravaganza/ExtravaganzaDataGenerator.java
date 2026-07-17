package com.mmodding.extravaganza;

import com.mmodding.extravaganza.block.FlattenedBlock;
import com.mmodding.extravaganza.block.HeveaBrasiliensisLog;
import com.mmodding.extravaganza.block.PaperLanternBlock;
import com.mmodding.extravaganza.block.TrashCanBlock;
import com.mmodding.extravaganza.data.ExtravaganzaBlockModelProcessors;
import com.mmodding.extravaganza.init.*;
import com.mmodding.extravaganza.item.RubberScraperItem;
import com.mmodding.extravaganza.resource.ExtravaganzaDamageTypeResources;
import com.mmodding.extravaganza.resource.ExtravaganzaWorldGenerationResources;
import com.mmodding.library.block.api.wrapper.BlockRelatives;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.datagen.api.ExtendedDataGeneratorEntrypoint;
import com.mmodding.library.datagen.api.lang.DefaultLangProcessors;
import com.mmodding.library.datagen.api.management.DataManager;
import com.mmodding.library.datagen.api.management.DefaultDataHandlers;
import com.mmodding.library.datagen.api.management.resolver.DataContentResolver;
import com.mmodding.library.datagen.api.model.block.DefaultBlockModelProcessing;
import com.mmodding.library.datagen.api.provider.BuiltinRegistryTagsProvider;
import com.mmodding.library.datagen.api.provider.MModdingLanguageProvider;
import com.mmodding.library.datagen.api.provider.MModdingRecipeProvider;
import com.mmodding.library.datagen.api.recipe.RecipeGenerator;
import com.mmodding.library.datagen.api.tag.ValueTagAppender;
import com.mmodding.library.datagen.api.tag.ValueTagProcessor;
import com.mojang.math.Quadrant;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ExtravaganzaDataGenerator implements ExtendedDataGeneratorEntrypoint {

	private static final Set<String> EXCLUDING_PICKAXE_KEYWORDS = Set.of(
		"hevea_brasiliensis", "registration", "paper_lantern", "content",
		"hanging_lights", "ink_puddle", "confetti", "rubber",
		"glass", "garland", "pinata"
	);

	@Override
	public void setupManager(DataManager manager) {
		// Resources
		manager.resource(Registries.DAMAGE_TYPE, ExtravaganzaDamageTypeResources::configure);
		manager.resource(Registries.CONFIGURED_FEATURE, ExtravaganzaWorldGenerationResources::configureFeatures);
		manager.resource(Registries.PLACED_FEATURE, ExtravaganzaWorldGenerationResources::configurePlacements);

		// Assets
		manager.chain(ExtravaganzaBlocks.class, DefaultDataHandlers.BLOCK_MODELS)
			.chain(Set.of(ExtravaganzaBlocks.COLORFUL_CONFETTI, ExtravaganzaBlocks.COLORFUL_INK_PUDDLE), ExtravaganzaBlockModelProcessors::createColorful)
			.chain(block -> block instanceof FlattenedBlock, ExtravaganzaBlockModelProcessors::createInkPuddleOrConfetti)
			.chain(block -> block instanceof PaperLanternBlock, ExtravaganzaBlockModelProcessors::createPaperLantern)
			.chain(block -> block instanceof TrashCanBlock, ExtravaganzaBlockModelProcessors::createTrashCan)
			.chain(block -> block instanceof LadderBlock, DefaultBlockModelProcessing::createLadder)
			.chain(Set.of(ExtravaganzaBlocks.GARLAND), ExtravaganzaBlockModelProcessors::createGarland)
			.chain(Set.of(
				ExtravaganzaBlocks.POPCORN_MACHINE,
				ExtravaganzaBlocks.COTTON_CANDY_MACHINE,
				ExtravaganzaBlocks.PINATA,
				ExtravaganzaBlocks.CAUTION_WET_FLOOR_SIGN
			), ExtravaganzaBlockModelProcessors::createHorizontalWithFlatItem)
			.chain(Set.of(ExtravaganzaBlocks.BALL_DISTRIBUTOR), ExtravaganzaBlockModelProcessors::createBallDistributor)
			.chain(Set.of(ExtravaganzaBlocks.BALL_PIT_REGISTRATION_TABLE), DefaultBlockModelProcessing::createDefinedModelHorizontalVariants)
			.chain(Set.of(ExtravaganzaBlocks.BALL_PIT_CONTENT), (generator, block) -> { generator.createNonTemplateModelBlock(block); generator.registerSimpleFlatItemModel(block.asItem()); })
			.chain(Set.of(ExtravaganzaBlocks.BALL_PIT_PROTECTION), (generator, block) -> generator.createAirLikeBlock(block, Items.BARRIER))
			.chain(BlockModelGenerators::createTrivialCube);
		manager.chain(ExtravaganzaItems.class, DefaultDataHandlers.ITEM_MODELS)
			.chain(item -> item instanceof RubberScraperItem, (generator, item) -> generator.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM))
			.chain(Set.of(ExtravaganzaItems.BAT), ItemModelGenerators::declareCustomModelItem)
			.chain((generator, item) -> generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
		manager.task(ExtravaganzaBlocks.class, DefaultDataHandlers.getTranslationHandler(Registries.BLOCK, Block.class), DefaultLangProcessors.CLASSIC);
		manager.task(ExtravaganzaItems.class, DefaultDataHandlers.getTranslationHandler(Registries.ITEM, Item.class), DefaultLangProcessors.CLASSIC);
		// noinspection unchecked
		manager.task(ExtravaganzaEntities.class, DefaultDataHandlers.getTranslationHandler(Registries.ENTITY_TYPE, (Class<EntityType<?>>) (Class<?>) EntityType.class), DefaultLangProcessors.CLASSIC);

		// Data
		manager.task(ExtravaganzaBlocks.class, DefaultDataHandlers.BLOCK_LOOTS, block -> block != ExtravaganzaBlocks.BALL_PIT_PROTECTION, BlockLootSubProvider::dropSelf);
		manager.chain(ExtravaganzaBlocks.class, DefaultDataHandlers.BLOCK_TAGS)
			.chain(block -> {
				String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
				return EXCLUDING_PICKAXE_KEYWORDS.stream().noneMatch(path::contains);
			}, (provider, element) -> provider.apply(BlockTags.MINEABLE_WITH_PICKAXE).add(element));
		manager.chain(ExtravaganzaBlocks.class, DefaultDataHandlers.BLOCK_TAGS)
			.chain(block -> BuiltInRegistries.BLOCK.getKey(block).getPath().contains("ladder"), ValueTagProcessor.forTags(BlockTags.CLIMBABLE, ExtravaganzaBlockTags.FESTIVE_RUBBER_LADDERS))
			.chain(block -> BuiltInRegistries.BLOCK.getKey(block).getPath().contains("ink_puddle"), ValueTagProcessor.forTag(ExtravaganzaBlockTags.INK_PUDDLES))
			.chain(block -> BuiltInRegistries.BLOCK.getKey(block).getPath().contains("confetti"), ValueTagProcessor.forTag(ExtravaganzaBlockTags.CONFETTI))
			.chain(block -> block instanceof PaperLanternBlock, ValueTagProcessor.forTag(ExtravaganzaBlockTags.PAPER_LANTERNS))
			.chain(block -> block instanceof TrashCanBlock, ValueTagProcessor.forTag(ExtravaganzaBlockTags.TRASH_CANS))
			.chain(block -> BuiltInRegistries.BLOCK.getKey(block).getPath().contains("festive_rubber"), ValueTagProcessor.forTag(ExtravaganzaBlockTags.FESTIVE_RUBBERS))
			.chain(Set.of(
				ExtravaganzaBlocks.TEAR_STAINED_GLASS,
				ExtravaganzaBlocks.PLANT_STAINED_GLASS,
				ExtravaganzaBlocks.TOMATO_STAINED_GLASS,
				ExtravaganzaBlocks.NYMPH_STAINED_GLASS
			), ValueTagProcessor.forTag(ConventionalBlockTags.GLASS_BLOCKS_TINTED));
		manager.chain(ExtravaganzaItems.class, DefaultDataHandlers.ITEM_TAGS)
			.chain(Set.of(ExtravaganzaItems.RUBBER_SCRAPER), ValueTagProcessor.forTags(ItemTags.MINING_ENCHANTABLE, ItemTags.DURABILITY_ENCHANTABLE))
			.chain(item -> BuiltInRegistries.ITEM.getKey(item).getPath().contains("candy_cane"), ValueTagProcessor.forTag(ExtravaganzaItemTags.CANDY_CANES))
			.chain(item -> BuiltInRegistries.ITEM.getKey(item).getPath().contains("festive_ball"), ValueTagProcessor.forTag(ExtravaganzaItemTags.FESTIVE_BALLS));

		// Final Data Handlers
		manager.task(ExtravaganzaBlocks.class, DefaultDataHandlers.BLOCK_RELATIVES);
		manager.task(ExtravaganzaBlocks.class, DefaultDataHandlers.WOOD_SETS);
	}

	@Override
	public void onInitializeDataGenerator(AdvancedContainer mod, FabricDataGenerator generator, FabricDataGenerator.Pack pack) {
		pack.addProvider(ExtravaganzaModelProvider::new);
		pack.addProvider(ExtravaganzaLanguageProvider::new);
		pack.addProvider(ExtravaganzaRecipeProvider::new);
		pack.addProvider(ExtravaganzaBlockTagsProvider::new);
		pack.addProvider(ExtravaganzaDamageTypeTagsProvider::new);
	}

	private static class ExtravaganzaModelProvider extends FabricModelProvider {

		public ExtravaganzaModelProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> ignored) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockModelGenerators generator) {
			Block logBlock = ExtravaganzaBlocks.HEVEA_BRASILIENSIS.getLog();

			Identifier cubeColumn = ModelTemplates.CUBE_COLUMN.create(logBlock, TextureMapping.logColumn(logBlock), generator.modelOutput);
			Identifier cubeColumnHorizontal = ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(logBlock, TextureMapping.logColumn(logBlock), generator.modelOutput);
			Identifier cubeColumnRubber = ModelTemplates.CUBE_COLUMN.createWithSuffix(
				logBlock,
				"_rubber",
				new TextureMapping()
					.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(logBlock, "_rubber"))
					.put(TextureSlot.END, TextureMapping.getBlockTexture(logBlock, "_top"))
					.put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(logBlock, "rubber")),
				generator.modelOutput
			);
			Identifier cubeColumnRubberHorizontal = ModelTemplates.CUBE_COLUMN_HORIZONTAL.createWithSuffix(
				logBlock,
				"_rubber",
				new TextureMapping()
					.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(logBlock, "_rubber"))
					.put(TextureSlot.END, TextureMapping.getBlockTexture(logBlock, "_top"))
					.put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(logBlock, "rubber")),
				generator.modelOutput
			);

			generator.blockStateOutput.accept(
				MultiVariantGenerator.dispatch(logBlock).with(
					PropertyDispatch.initial(HeveaBrasiliensisLog.AXIS, HeveaBrasiliensisLog.RUBBER)
						.select(
							Direction.Axis.Y, false,
							BlockModelGenerators.plainVariant(cubeColumn)
						)
						.select(
							Direction.Axis.Z, false,
							BlockModelGenerators.plainVariant(cubeColumnHorizontal)
								.with(VariantMutator.X_ROT.withValue(Quadrant.R90))
						)
						.select(
							Direction.Axis.X, false,
							BlockModelGenerators.plainVariant(cubeColumnHorizontal)
								.with(VariantMutator.X_ROT.withValue(Quadrant.R90))
								.with(VariantMutator.Y_ROT.withValue(Quadrant.R90))
						)
						.select(
							Direction.Axis.Y, true,
							BlockModelGenerators.plainVariant(cubeColumnRubber)
						)
						.select(
							Direction.Axis.Z, true,
							BlockModelGenerators.plainVariant(cubeColumnRubberHorizontal)
								.with(VariantMutator.X_ROT.withValue(Quadrant.R90))
						)
						.select(
							Direction.Axis.X, true,
							BlockModelGenerators.plainVariant(cubeColumnRubberHorizontal)
								.with(VariantMutator.X_ROT.withValue(Quadrant.R90))
								.with(VariantMutator.Y_ROT.withValue(Quadrant.R90))
						)
				)
			);
		}

		@Override
		public void generateItemModels(ItemModelGenerators itemModelGenerators) {}
	}

	private static class ExtravaganzaLanguageProvider extends MModdingLanguageProvider {

		protected ExtravaganzaLanguageProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
		}

		@Override
		public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translations) {
			translations.add("death.trash.0", "%1$s slipped and fell into the garbage");
			translations.add("death.trash.0.uncensored", "%1$s slipped and fell into the garbage. No funeral.");
			translations.add("death.trash.1", "%1$s was stuffed into a trash can");
			translations.add("death.trash.2", "%1$s became rubbish");
			translations.add("death.trash.3", "%1$s fell into waste bin");
			translations.add("death.trash.4", "%1$s went to waste");
			translations.add("death.trash.5", "%1$s was canned");
			translations.add("death.trash.6", "%1$s was disposed of");
			translations.add("death.trash.7", "%1$s was discarded");
			translations.add("death.trash.8", "%1$s went dumpster diving");
			translations.add("death.trash.9", "%1$s invoked the cans' wrath");
			translations.add("death.trash.10", "%1$s was not recycled");
			translations.add("death.trash.11", "%1$s is due for collection this Friday");
			translations.add("death.trash.11.uncensored", "%1$s suffocated in the garbage");
			translations.add("death.trash.12", "%1$s went to raccoon heaven");
			translations.add("death.trash.12.uncensored", "%1$s will feed the raccoons well tonight");
			translations.add("death.trash.13", "The death message that used to be here was thrown away. So was %1$s");
			translations.add("death.trash.13.uncensored", "%1$s became a vessel for maggots");
			translations.add("death.trash.14", "Garbage God obliterated %1$s");
			translations.add("death.trash.player.0", "%1$s was shoved into a trash can by %2$s");
			translations.add("death.trash.player.1", "%2$s took out the trash (%1$s)");
			translations.add("itemGroup.extravaganza.main", "Extravaganza!");
			translations.add("message.extravaganza.cotton_candy_machine", "There is %s sugar inside.");
			translations.add("message.extravaganza.trash_can.right_click", "The player can open the trash by right-clicking.");
			translations.add("message.extravaganza.trash_can.quick_throw", "The player can throw items to trash (one by one) by sneaking + right-clicking whenever the trash is open or not.");
			translations.add("message.extravaganza.trash_can.opening_trash", "The player can throw entities to trash by putting them on top of the opened trash.");
			translations.add("message.extravaganza.trash_can.throw_whole_stack", "If the player wants to throw an entire stack, they need to open the trash and then throw the whole stack.");
			translations.add("painting.extravaganza.queerness.author", "Aeramisu");
			translations.add("painting.extravaganza.queerness.title", "Queerness");
			translations.add("painting.extravaganza.reflect.author", "Aeramisu");
			translations.add("painting.extravaganza.reflect.title", "Reflect");
			translations.add("tag.item.extravaganza.candy_canes", "Candy Canes");
			translations.add("tag.item.extravaganza.festive_balls", "Festive Balls");
		}
	}

	private static class ExtravaganzaRecipeProvider extends MModdingRecipeProvider {

		private static final Set<String> VARIANTS = Set.of(
			"aligned", "barred", "bent", "curved",
			"dotted", "padded", "perforated", "planked",
			"poured", "scratched", "scratched_rotated_90", "scratched_rotated_180",
			"scratched_rotated_270", "screwed", "sharped", "sharped_rotated_90",
			"sharped_rotated_180", "sharped_rotated_270", "slipped", "split",
			"striped", "tiled", "traversable", "windowed",
			"wooded", /* those are a bit special */ "glass", "grate", /* normal one */ ""
		);

		private static final Set<String> COLORFUL_VARIANTS = Set.of(
			"bent", "curved", "padded", "perforated",
			"scratched", "scratched_rotated_90", "scratched_rotated_180", "scratched_rotated_270",
			"screwed", "slipped", "striped", "tiled",
			"windowed", "chiseled", /* special ones (again) */ "bricks", "tiles",
			"pavers", "glass", "grate", /* default one */ ""
		);

		public ExtravaganzaRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
		}

		@Override
		public void createRecipes(RecipeGenerator generator) {
			generator.forItem(ExtravaganzaItems.TEAR_DYE).shapeless(
				"_from_light_blue_and_white", 2, RecipeCategory.MISC,
				recipe -> recipe.with(Items.DYE.lightBlue(), Items.DYE.white())
			);
			generator.forItem(ExtravaganzaItems.PLANT_DYE).shapeless(
				"_from_lime_and_white", 2, RecipeCategory.MISC,
				recipe -> recipe.with(Items.DYE.lime(), Items.DYE.white())
			);
			generator.forItem(ExtravaganzaItems.NYMPH_DYE).shapeless(
				"_from_magenta_and_white", 2, RecipeCategory.MISC,
				recipe -> recipe.with(Items.DYE.magenta(), Items.DYE.white())
			);
			generator.forItem(ExtravaganzaItems.TOMATO_DYE).shapeless(
				"_from_red_and_brown", 2, RecipeCategory.MISC,
				recipe -> recipe.with(Items.DYE.red(), Items.DYE.brown())
			);

			generator.forItem(ExtravaganzaBlocks.TEAR_STAINED_GLASS).shaped(
				8, RecipeCategory.BUILDING_BLOCKS,
				recipe -> recipe.key('#', Blocks.GLASS)
					.key('X', ExtravaganzaItems.TEAR_DYE)
					.pattern(
						"###",
						"#X#",
						"###"
					)
			);
			generator.forItem(ExtravaganzaBlocks.PLANT_STAINED_GLASS).shaped(
				8, RecipeCategory.BUILDING_BLOCKS,
				recipe -> recipe.key('#', Blocks.GLASS)
					.key('X', ExtravaganzaItems.PLANT_DYE)
					.pattern(
						"###",
						"#X#",
						"###"
					)
			);
			generator.forItem(ExtravaganzaBlocks.TOMATO_STAINED_GLASS).shaped(
				8, RecipeCategory.BUILDING_BLOCKS,
				recipe -> recipe.key('#', Blocks.GLASS)
					.key('X', ExtravaganzaItems.TOMATO_DYE)
					.pattern(
						"###",
						"#X#",
						"###"
					)
			);
			generator.forItem(ExtravaganzaBlocks.NYMPH_STAINED_GLASS).shaped(
				8, RecipeCategory.BUILDING_BLOCKS,
				recipe -> recipe.key('#', Blocks.GLASS)
					.key('X', ExtravaganzaItems.NYMPH_DYE)
					.pattern(
						"###",
						"#X#",
						"###"
					)
			);

			for (ExtravaganzaColor color : ExtravaganzaColor.VALUES) {
				Item festiveRubber = BuiltInRegistries.ITEM.getValue(Extravaganza.createId(color.getSerializedName() + "_festive_rubber"));
				Item inkPuddle = BuiltInRegistries.ITEM.getValue(Extravaganza.createId(color.getSerializedName() + "_ink_puddle"));
				Item confetti = BuiltInRegistries.ITEM.getValue(Extravaganza.createId(color.getSerializedName() + "_confetti"));
				Item paperLantern = BuiltInRegistries.ITEM.getValue(Extravaganza.createId(color.getSerializedName() + "_paper_lantern"));
				Item trashCan = BuiltInRegistries.ITEM.getValue(Extravaganza.createId(color.getSerializedName() + "_trash_can"));
				Item ladder = BuiltInRegistries.ITEM.getValue(Extravaganza.createId(color.getSerializedName() + "_festive_rubber_ladder"));
				Identifier dyeIdentifier;
				if (color.equals(ExtravaganzaColor.TEAR) || color.equals(ExtravaganzaColor.PLANT) || color.equals(ExtravaganzaColor.NYMPH) || color.equals(ExtravaganzaColor.TOMATO)) {
					dyeIdentifier = Extravaganza.createId(color.getSerializedName() + "_dye");
				}
				else {
					dyeIdentifier = Identifier.withDefaultNamespace(color.getSerializedName() + "_dye");
				}
				Item dye = BuiltInRegistries.ITEM.getValue(dyeIdentifier);

				generator.forItem(festiveRubber).shaped(
					16, RecipeCategory.BUILDING_BLOCKS,
					recipe -> recipe.key('C', dye)
						.key('R', ExtravaganzaItems.RUBBER)
						.pattern(
							"CRC",
							"RCR",
							"CRC"
						)
				);
				generator.forItem(inkPuddle).shapeless(
					4, RecipeCategory.BUILDING_BLOCKS,
					recipe -> recipe.with(Items.INK_SAC, dye)
				);
				generator.forItem(confetti).shapeless(
					4, RecipeCategory.BUILDING_BLOCKS,
					recipe -> recipe.with(Items.PAPER, dye)
				);
				generator.forItem(paperLantern).shaped(
					4, RecipeCategory.BUILDING_BLOCKS,
					recipe -> recipe.key('P', Items.PAPER)
						.key('C', dye)
						.pattern(
							"PCP",
							"CPC",
							"PCP"
						)
				);
				generator.forItem(trashCan).shaped(
					16, RecipeCategory.BUILDING_BLOCKS,
					recipe -> recipe.key('R', festiveRubber)
						.key('H', Items.HOPPER)
						.pattern(
							"R R",
							"RHR",
							" R "
						)
				);
				generator.forItem(ladder).shaped(
					4, RecipeCategory.BUILDING_BLOCKS,
					recipe -> recipe.key('R', festiveRubber)
						.key('S', Items.STICK)
						.pattern(
							"R R",
							"SRS",
							"R R"
						)
				);

				for (String variant : VARIANTS) {
					String path = !variant.equals("glass") && !variant.equals("grate") ? variant + "_festive_rubber" : "festive_rubber_" + variant;
					if (path.contains("_rotated_90")) {
						path = path.replace("_rotated_90", "") + "_rotated_90";
					}
					else if (path.contains("_rotated_180")) {
						path = path.replace("_rotated_180", "") + "_rotated_180";
					}
					else if (path.contains("_rotated_270")) {
						path = path.replace("_rotated_270", "") + "_rotated_270";
					}
					Identifier identifier = Extravaganza.createId(color.getSerializedName() + (!variant.isEmpty() ? "_" + path : "_festive_rubber"));
					Item currentFestiveRubber = BuiltInRegistries.ITEM.getValue(identifier);
					if (!variant.isEmpty()) {
						generator.forItem(currentFestiveRubber).cutting(
							festiveRubber, RecipeCategory.BUILDING_BLOCKS, 1
						);
					}
				}
			}

			// Colorful
			generator.forItem(ExtravaganzaBlocks.COLORFUL_FESTIVE_RUBBER_LADDER).shaped(
				4, RecipeCategory.BUILDING_BLOCKS,
				recipe -> recipe.key('R', ExtravaganzaBlocks.COLORFUL_FESTIVE_RUBBER.getMain())
					.key('S', Items.STICK)
					.pattern(
						"R R",
						"SRS",
						"R R"
					)
			);
			generator.forItem(ExtravaganzaBlocks.COLORFUL_FESTIVE_RUBBER.getMain()).shaped(
				16, RecipeCategory.BUILDING_BLOCKS,
				recipe -> recipe.key('Y', Items.DYE.yellow())
					.key('B', Items.DYE.blue())
					.key('P', Items.DYE.magenta())
					.key('L', Items.DYE.lime())
					.key('E', Items.DYE.red())
					.key('R', ExtravaganzaItems.RUBBER)
					.pattern(
						"YRB",
						"RPR",
						"LRE"
					)
			);
			generator.forItem(ExtravaganzaBlocks.COLORFUL_INK_PUDDLE).shapeless(
				4, RecipeCategory.BUILDING_BLOCKS,
				recipe -> recipe.with(Items.INK_SAC, Items.DYE.yellow(), Items.DYE.blue(), Items.DYE.magenta(), Items.DYE.lime(), Items.DYE.red())
			);
			generator.forItem(ExtravaganzaBlocks.COLORFUL_CONFETTI).shapeless(
				4, RecipeCategory.BUILDING_BLOCKS,
				recipe -> recipe.with(Items.PAPER, Items.DYE.yellow(), Items.DYE.blue(), Items.DYE.magenta(), Items.DYE.lime(), Items.DYE.red())
			);
			generator.forItem(ExtravaganzaBlocks.COLORFUL_PAPER_LANTERN).shaped(
				4, RecipeCategory.BUILDING_BLOCKS,
				recipe -> recipe.key('P', Items.PAPER)
					.key('Y', Items.DYE.yellow())
					.key('B', Items.DYE.blue())
					.key('L', Items.DYE.lime())
					.key('R', Items.DYE.red())
					.pattern(
						"PYP",
						"BPL",
						"PRP"
					)
			);

			for (String variant : COLORFUL_VARIANTS) {
				String path = !Set.of("bricks", "tiles", "pavers", "glass", "grate").contains(variant) ? variant + "_festive_rubber" : "festive_rubber_" + variant;
				if (path.contains("_rotated_90")) {
					path = path.replace("_rotated_90", "") + "_rotated_90";
				}
				else if (path.contains("_rotated_180")) {
					path = path.replace("_rotated_180", "") + "_rotated_180";
				}
				else if (path.contains("_rotated_270")) {
					path = path.replace("_rotated_270", "") + "_rotated_270";
				}
				Identifier identifier = Extravaganza.createId("colorful" + (!variant.isEmpty() ? "_" + path : "_festive_rubber"));
				Item currentColorfulFestiveRubber = BuiltInRegistries.ITEM.getValue(identifier);
				if (!variant.isEmpty()) {
					generator.forItem(currentColorfulFestiveRubber).cutting(
						ExtravaganzaBlocks.COLORFUL_FESTIVE_RUBBER.getMain(),
						RecipeCategory.BUILDING_BLOCKS, 1
					);
				}
			}

			// Tools
			generator.forItem(ExtravaganzaItems.WRENCH_AGANZA).shapeless(
				RecipeCategory.TOOLS,
				recipe -> recipe.with(
					ExtravaganzaItems.GOLDEN_CANDY_CANE,
					ExtravaganzaItems.GREEN_CANDY_CANE,
					ExtravaganzaItems.RED_CANDY_CANE
				)
			);
			generator.forItem(ExtravaganzaItems.RUBBER_SCRAPER).shapeless(
				RecipeCategory.TOOLS,
				recipe -> recipe.with(
					Ingredient.of(Items.IRON_INGOT),
					Ingredient.of(generator.getItemLookup().getOrThrow(ExtravaganzaItemTags.CANDY_CANES))
				),
				Items.IRON_INGOT
			);
			generator.forItem(ExtravaganzaItems.RUBBER_EXTRACTOR).shaped(
				RecipeCategory.TOOLS,
				recipe -> recipe.key('I', Items.IRON_INGOT)
					.pattern(
						"III",
						"I  ",
						"I  "
					)
			);

			// Food
			generator.forItem(ExtravaganzaItems.GOLDEN_CANDY_CANE).shaped(
				RecipeCategory.FOOD,
				recipe -> recipe.key('R', Items.DYE.yellow())
					.key('S', Items.SUGAR)
					.pattern(
						"RS ",
						"S R",
						"R  "
					)
			);
			generator.forItem(ExtravaganzaItems.GREEN_CANDY_CANE).shaped(
				RecipeCategory.FOOD,
				recipe -> recipe.key('R', Items.DYE.green())
					.key('S', Items.SUGAR)
					.pattern(
						"RS ",
						"S R",
						"R  "
					)
			);
			generator.forItem(ExtravaganzaItems.RED_CANDY_CANE).shaped(
				RecipeCategory.FOOD,
				recipe -> recipe.key('R', Items.DYE.red())
					.key('S', Items.SUGAR)
					.pattern(
						"RS ",
						"S R",
						"R  "
					)
			);
			generator.forItem(ExtravaganzaItems.HOT_DOG).shapeless(
				RecipeCategory.FOOD,
				recipe -> recipe.with(Items.COOKED_BEEF, Items.BREAD)
			);
			generator.forItem(ExtravaganzaItems.HOT_DOG_WITH_MAYONNAISE).shapeless(
				RecipeCategory.FOOD,
				recipe -> recipe.with(ExtravaganzaItems.HOT_DOG, Items.EGG)
			);
			generator.forItem(ExtravaganzaItems.CHEESEBURGER).shaped(
				RecipeCategory.FOOD,
				recipe -> recipe.key('B', Items.BREAD)
					.key('M', Items.MILK_BUCKET)
					.key('C', Items.COOKED_BEEF)
					.pattern(
						" B ",
						"MCM",
						" B "
					)
			);
			generator.forItem(ExtravaganzaItems.BEESECHURGER).shaped(
				RecipeCategory.FOOD,
				recipe -> recipe.key('M', Items.MILK_BUCKET)
					.key('B', Items.BREAD)
					.key('C', Items.COOKED_BEEF)
					.pattern(
						" M ",
						"BCB",
						" M "
					)
			);
			generator.forItem(ExtravaganzaItems.WAY_TOO_SUGARY_WHITECAKE).shaped(
				RecipeCategory.FOOD,
				recipe -> recipe.key('S', Items.SUGAR)
					.key('C', Items.CAKE)
					.pattern(
						"SSS",
						"SCS",
						"SSS"
					)
			);
			generator.forItem(ExtravaganzaItems.EMPTY_POPCORN).shapeless(
				4, RecipeCategory.FOOD,
				recipe -> recipe.with(Items.DYE.red(), Items.PAPER, Items.DYE.yellow())
			);

			// Miscellaneous
			generator.forItem(ExtravaganzaItems.CHERRY_BALLOON).shaped(
				16, RecipeCategory.MISC,
				recipe -> recipe.key('D', Items.DYE.red())
					.key('P', Items.PAPER)
					.key('S', Items.STRING)
					.pattern(
						"DP ",
						"PD ",
						"  S"
					)
			);
			generator.forItem(ExtravaganzaItems.CREEPER_BALLOON).shaped(
				16, RecipeCategory.MISC,
				recipe -> recipe.key('D', Items.DYE.green())
					.key('P', Items.PAPER)
					.key('S', Items.STRING)
					.pattern(
						"DP ",
						"PD ",
						"  S"
					)
			);
			generator.forItem(ExtravaganzaItems.ENDERMAN_BALLOON).shaped(
				16, RecipeCategory.MISC,
				recipe -> recipe.key('D', Items.DYE.black())
					.key('P', Items.PAPER)
					.key('S', Items.STRING)
					.pattern(
						"DP ",
						"PD ",
						"  S"
					)
			);
			generator.forItem(ExtravaganzaItems.BAT).shaped(
				RecipeCategory.MISC,
				recipe -> recipe.key('B', Items.DYE.lightBlue())
					.key('Y', Items.DYE.yellow())
					.key('R', Items.DYE.red())
					.key('S', Items.STICK)
					.pattern(
						"BSB",
						"YSY",
						"RSR"
					)
			);
			generator.forItem(ExtravaganzaItems.MERRY_GO_ROUND).shaped(
				RecipeCategory.MISC,
				recipe -> recipe.key('I', Items.IRON_INGOT)
					.key('R', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.RED).getMain())
					.key('S', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.RED).get(BlockFamily.Variant.SLAB))
					.pattern(
						"III",
						"RRR",
						"SRS"
					)
			);
			generator.forItem(ExtravaganzaBlocks.BALL_PIT_REGISTRATION_TABLE).shaped(
				RecipeCategory.MISC,
				recipe -> recipe.key('P', Items.PAPER)
					.key('I', Items.INK_SAC)
					.key('F', Items.FLOWER_POT)
					.key('H', ExtravaganzaBlocks.HEVEA_BRASILIENSIS.getPlankRelatives().getMain())
					.pattern(
						"PIF",
						"HHH",
						"H H"
					)
			);
			generator.forItem(ExtravaganzaBlocks.BALL_PIT_CONTENT).shaped(
				RecipeCategory.MISC,
				recipe -> recipe.key('B', Ingredient.of(generator.getItemLookup().getOrThrow(ExtravaganzaItemTags.FESTIVE_BALLS)))
					.pattern(
						"BBB",
						"BBB",
						"BBB"
					),
				ExtravaganzaItems.RUBBER
			);
			generator.forItem(ExtravaganzaBlocks.BALL_DISTRIBUTOR).shaped(
				RecipeCategory.MISC,
				recipe -> recipe.key('R', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.RED).getMain())
					.key('S', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.RED).get(BlockFamily.Variant.SLAB))
					.key('G', Items.GLASS)
					.key('B', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.BLACK).getMain())
					.pattern(
						"SRS",
						"GGG",
						"BBB"
					)
			);
			generator.forItem(ExtravaganzaBlocks.POPCORN_MACHINE).shaped(
				RecipeCategory.FOOD,
				recipe -> recipe.key('B', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.BLUE).getMain())
					.key('D', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.BLUE).get(BlockFamily.Variant.SLAB))
					.key('R', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.RED).getMain())
					.key('S', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.RED).get(BlockFamily.Variant.SLAB))
					.pattern(
						"BBB",
						"DRD",
						"SRS"
					)
			);
			generator.forItem(ExtravaganzaBlocks.COTTON_CANDY_MACHINE).shaped(
				RecipeCategory.FOOD,
				recipe -> recipe.key('G', Blocks.GLASS)
					.key('S', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.PURPLE).get(BlockFamily.Variant.SLAB))
					.key('W', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.PURPLE).get(BlockFamily.Variant.WALL))
					.pattern(
						"G",
						"W",
						"S"
					)
			);
			generator.forItem(ExtravaganzaBlocks.GARLAND).shaped(
				32, RecipeCategory.BUILDING_BLOCKS,
				recipe -> recipe.key('R', Items.DYE.red())
					.key('O', Items.DYE.orange())
					.key('L', Items.DYE.lime())
					.key('Y', Items.DYE.yellow())
					.key('B', Items.DYE.blue())
					.key('S', Items.STRING)
					.pattern(
						"RSO",
						"SLS",
						"YSB"
					)
			);
			generator.forItem(ExtravaganzaBlocks.PINATA).shaped(
				8, RecipeCategory.BUILDING_BLOCKS,
				recipe -> recipe.key('B', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.BLUE).getMain())
					.key('G', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.GREEN).getMain())
					.key('Y', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.YELLOW).getMain())
					.key('O', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.ORANGE).getMain())
					.key('R', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.RED).getMain())
					.pattern(
						"  B",
						"  G",
						"ROY"
					)
			);
			generator.forItem(ExtravaganzaBlocks.CAUTION_WET_FLOOR_SIGN).shaped(
				8, RecipeCategory.BUILDING_BLOCKS,
				recipe -> recipe.key('Y', ExtravaganzaBlocks.FESTIVE_RUBBER.variants().get(ExtravaganzaColor.YELLOW).getMain())
					.key('I', Items.INK_SAC)
					.pattern(
						"YYY",
						"YIY",
						"YYY"
					)
			);
			generator.forItem(ExtravaganzaBlocks.HANGING_LIGHTS).shapeless(
				24, RecipeCategory.BUILDING_BLOCKS,
				recipe -> recipe.with(
					BuiltInRegistries.BLOCK.getValue(Extravaganza.createId("cyan_paper_lantern")),
					BuiltInRegistries.BLOCK.getValue(Extravaganza.createId("magenta_paper_lantern")),
					BuiltInRegistries.BLOCK.getValue(Extravaganza.createId("red_paper_lantern")),
					BuiltInRegistries.BLOCK.getValue(Extravaganza.createId("green_paper_lantern")),
					BuiltInRegistries.BLOCK.getValue(Extravaganza.createId("yellow_paper_lantern")),
					BuiltInRegistries.BLOCK.getValue(Extravaganza.createId("magenta_paper_lantern"))
				)
			);
		}

		@Override
		public String getName() {
			return "Extravaganza Recipes";
		}
	}

	private static class ExtravaganzaBlockTagsProvider extends BuiltinRegistryTagsProvider.BlockTagsProvider {

		public ExtravaganzaBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
		}

		@Override
		protected void addTags(HolderLookup.Provider registries) {
			AdvancedContainer mod = AdvancedContainer.of(FabricLoader.getInstance().getModContainer("extravaganza").orElseThrow());
			ValueTagAppender<Block> rubberScrapperMineable = this.valueBuilder(ExtravaganzaBlockTags.RUBBER_SCRAPPER_MINEABLE);
			mod.iterateOverRegistry(BuiltInRegistries.BLOCK, (key, block) -> {
				if (key.identifier().getPath().contains("rubber")) {
					rubberScrapperMineable.add(block);
				}
			});
		}
	}

	private static class ExtravaganzaDamageTypeTagsProvider extends FabricTagsProvider<DamageType> {

		public ExtravaganzaDamageTypeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, Registries.DAMAGE_TYPE, future);
		}

		@Override
		protected void addTags(HolderLookup.Provider provider) {
			this.tag(DamageTypeTags.BYPASSES_ARMOR).add(ExtravaganzaDamageTypes.TRASH);
			this.tag(DamageTypeTags.BYPASSES_INVULNERABILITY).add(ExtravaganzaDamageTypes.TRASH);
			this.tag(DamageTypeTags.BYPASSES_RESISTANCE).add(ExtravaganzaDamageTypes.TRASH);
			this.tag(DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL).add(ExtravaganzaDamageTypes.TRASH);
			this.tag(DamageTypeTags.NO_KNOCKBACK).add(ExtravaganzaDamageTypes.TRASH);
		}
	}

	static {
		DataContentResolver.<ExtravaganzaBlocks.ExtravaganzaColoredVariants, BlockRelatives>register(ExtravaganzaBlocks.ExtravaganzaColoredVariants.class, BlockRelatives.class, input -> input.variants().values().stream().toList());
	}
}
