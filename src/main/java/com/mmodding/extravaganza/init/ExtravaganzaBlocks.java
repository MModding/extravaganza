package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.block.*;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExtravaganzaBlocks {

	public static final BlockSetType HEVEA_BRASILIENSIS_TYPE = new BlockSetType("hevea_brasiliensis");

	public static final WoodType HEVEA_BRASILIENSIS = new WoodType("hevea_brasiliensis", ExtravaganzaBlocks.HEVEA_BRASILIENSIS_TYPE);

	public static final Block HEVEA_BRASILIENSIS_LOG = new HeveaBrasiliensisLog(AbstractBlock.Settings.create().ticksRandomly().strength(2.0f).sounds(BlockSoundGroup.WOOD).burnable());
	public static final Block HEVEA_BRASILIENSIS_WOOD = new PillarBlock(AbstractBlock.Settings.create().strength(2.0f).sounds(BlockSoundGroup.WOOD).burnable());

	public static final Block STRIPPED_HEVEA_BRASILIENSIS_LOG = new PillarBlock(AbstractBlock.Settings.create().strength(2.0f).sounds(BlockSoundGroup.WOOD).burnable());
	public static final Block STRIPPED_HEVEA_BRASILIENSIS_WOOD = new PillarBlock(AbstractBlock.Settings.create().strength(2.0f).sounds(BlockSoundGroup.WOOD).burnable());

	public static final Block HEVEA_BRASILIENSIS_PLANKS = new Block(AbstractBlock.Settings.create().strength(2.0f).sounds(BlockSoundGroup.WOOD).burnable());
	public static final Block HEVEA_BRASILIENSIS_STAIRS = new StairsBlock(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_PLANKS.getDefaultState(), AbstractBlock.Settings.create().strength(2.0f).sounds(BlockSoundGroup.WOOD).burnable());
	public static final Block HEVEA_BRASILIENSIS_SLAB = new SlabBlock(AbstractBlock.Settings.create().strength(2.0f).sounds(BlockSoundGroup.WOOD).burnable());
	public static final Block HEVEA_BRASILIENSIS_FENCE = new FenceBlock(AbstractBlock.Settings.create().strength(2.0f).sounds(BlockSoundGroup.WOOD).burnable());
	public static final Block HEVEA_BRASILIENSIS_FENCE_GATE = new FenceGateBlock(ExtravaganzaBlocks.HEVEA_BRASILIENSIS, AbstractBlock.Settings.create().strength(2.0f).sounds(BlockSoundGroup.WOOD).burnable());
	public static final Block HEVEA_BRASILIENSIS_DOOR = new DoorBlock(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_TYPE, AbstractBlock.Settings.create().strength(2.0f).sounds(BlockSoundGroup.WOOD).burnable()) {
		@Override
		protected List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
			return state.get(BallDistributorBlock.HALF).equals(DoubleBlockHalf.LOWER) ? super.getDroppedStacks(state, builder) : List.of();
		}
	};
	public static final Block HEVEA_BRASILIENSIS_TRAPDOOR = new TrapdoorBlock(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_TYPE, AbstractBlock.Settings.create().strength(2.0f).sounds(BlockSoundGroup.WOOD).burnable());
	public static final Block HEVEA_BRASILIENSIS_PRESSURE_PLATE = new PressurePlateBlock(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_TYPE, AbstractBlock.Settings.create().strength(2.0f).sounds(BlockSoundGroup.WOOD).burnable());
	public static final Block HEVEA_BRASILIENSIS_BUTTON = Blocks.createWoodenButtonBlock(ExtravaganzaBlocks.HEVEA_BRASILIENSIS_TYPE);

	public static final Block HEVEA_BRASILIENSIS_LEAVES = Blocks.createLeavesBlock(BlockSoundGroup.AZALEA_LEAVES);

	public static final Block HEVEA_BRASILIENSIS_SAPLING = new SaplingBlock(new SaplingGenerator("hevea_brasiliensis", Optional.empty(), Optional.of(ExtravaganzaWorldGeneration.HEVEA_BRASILIENSIS), Optional.empty()), AbstractBlock.Settings.create().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.AZALEA_LEAVES));

	public static final Block BALL_PIT_REGISTRATION_TABLE = new BallPitRegistrationTableBlock(AbstractBlock.Settings.create().nonOpaque().strength(2.0f).sounds(BlockSoundGroup.WOOD));
	public static final Block BALL_PIT_CONTENT = new BallPitContentBlock(AbstractBlock.Settings.create().noCollision().sounds(BlockSoundGroup.SLIME));
	public static final Block BALL_PIT_PROTECTION = new BallPitProtectionBlock(AbstractBlock.Settings.create().noCollision().allowsSpawning(Blocks::never).solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::always).replaceable());

	public static final Block BALL_DISTRIBUTOR = new BallDistributorBlock(AbstractBlock.Settings.create().requiresTool().strength(2.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.LANTERN));

	public static final Block POPCORN_MACHINE = new PopcornMachineBlock(AbstractBlock.Settings.create().requiresTool().strength(2.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.LANTERN));

	public static final Block GARLAND = new GarlandBlock(AbstractBlock.Settings.create().strength(0.5f, 2.0f).nonOpaque().sounds(BlockSoundGroup.WOOD));

	public static final Block PINATA = new PinataBlock(AbstractBlock.Settings.create().strength(0.5f, 2.0f).nonOpaque().sounds(BlockSoundGroup.WOOL));

	public static final Block TEAR_STAINED_GLASS = new TransparentBlock(AbstractBlock.Settings.create().strength(1.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.GLASS));
	public static final Block PLANT_STAINED_GLASS = new TransparentBlock(AbstractBlock.Settings.create().strength(1.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.GLASS));
	public static final Block TOMATO_STAINED_GLASS = new TransparentBlock(AbstractBlock.Settings.create().strength(1.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.GLASS));
	public static final Block NYMPH_STAINED_GLASS = new TransparentBlock(AbstractBlock.Settings.create().strength(1.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.GLASS));

	public static final Supplier<AbstractBlock.Settings> COLORFUL_SETTINGS = () -> AbstractBlock.Settings.create().strength(1.5f, 3.0f).mapColor(MapColor.PALE_PURPLE).sounds(BlockSoundGroup.PACKED_MUD);

	public static void register() {
		ExtravaganzaBlocks.registerBlockWithItem("hevea_brasiliensis_log", ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LOG);
		ExtravaganzaBlocks.registerBlockWithItem("hevea_brasiliensis_wood", ExtravaganzaBlocks.HEVEA_BRASILIENSIS_WOOD);
		ExtravaganzaBlocks.registerBlockWithItem("stripped_hevea_brasiliensis_log", ExtravaganzaBlocks.STRIPPED_HEVEA_BRASILIENSIS_LOG);
		ExtravaganzaBlocks.registerBlockWithItem("stripped_hevea_brasiliensis_wood", ExtravaganzaBlocks.STRIPPED_HEVEA_BRASILIENSIS_WOOD);
		ExtravaganzaBlocks.registerBlockWithItem("hevea_brasiliensis_planks", ExtravaganzaBlocks.HEVEA_BRASILIENSIS_PLANKS);
		ExtravaganzaBlocks.registerBlockWithItem("hevea_brasiliensis_stairs", ExtravaganzaBlocks.HEVEA_BRASILIENSIS_STAIRS);
		ExtravaganzaBlocks.registerBlockWithItem("hevea_brasiliensis_slab", ExtravaganzaBlocks.HEVEA_BRASILIENSIS_SLAB);
		ExtravaganzaBlocks.registerBlockWithItem("hevea_brasiliensis_fence", ExtravaganzaBlocks.HEVEA_BRASILIENSIS_FENCE);
		ExtravaganzaBlocks.registerBlockWithItem("hevea_brasiliensis_fence_gate", ExtravaganzaBlocks.HEVEA_BRASILIENSIS_FENCE_GATE);
		ExtravaganzaBlocks.registerBlockWithItem("hevea_brasiliensis_door", ExtravaganzaBlocks.HEVEA_BRASILIENSIS_DOOR);
		ExtravaganzaBlocks.registerBlockWithItem("hevea_brasiliensis_trapdoor", ExtravaganzaBlocks.HEVEA_BRASILIENSIS_TRAPDOOR);
		ExtravaganzaBlocks.registerBlockWithItem("hevea_brasiliensis_pressure_plate", ExtravaganzaBlocks.HEVEA_BRASILIENSIS_PRESSURE_PLATE);
		ExtravaganzaBlocks.registerBlockWithItem("hevea_brasiliensis_button", ExtravaganzaBlocks.HEVEA_BRASILIENSIS_BUTTON);
		ExtravaganzaBlocks.registerBlockWithItem("hevea_brasiliensis_leaves", ExtravaganzaBlocks.HEVEA_BRASILIENSIS_LEAVES);
		ExtravaganzaBlocks.registerBlockWithItem("hevea_brasiliensis_sapling", ExtravaganzaBlocks.HEVEA_BRASILIENSIS_SAPLING);
		ExtravaganzaBlocks.registerBlockWithItem("ball_pit_registration_table", ExtravaganzaBlocks.BALL_PIT_REGISTRATION_TABLE);
		ExtravaganzaBlocks.registerBlockWithItem("ball_pit_content", ExtravaganzaBlocks.BALL_PIT_CONTENT);
		Registry.register(Registries.BLOCK, "ball_pit_protection", ExtravaganzaBlocks.BALL_PIT_PROTECTION);
		ExtravaganzaBlocks.registerBlockWithItem("ball_distributor", ExtravaganzaBlocks.BALL_DISTRIBUTOR);
		ExtravaganzaBlocks.registerBlockWithItem("popcorn_machine", ExtravaganzaBlocks.POPCORN_MACHINE);
		ExtravaganzaBlocks.registerBlockWithItem("garland", ExtravaganzaBlocks.GARLAND);
		ExtravaganzaBlocks.registerBlockWithItem("pinata", ExtravaganzaBlocks.PINATA);
		ExtravaganzaBlocks.registerBlockWithItem("tear_stained_glass", ExtravaganzaBlocks.TEAR_STAINED_GLASS);
		ExtravaganzaBlocks.registerBlockWithItem("plant_stained_glass", ExtravaganzaBlocks.PLANT_STAINED_GLASS);
		ExtravaganzaBlocks.registerBlockWithItem("tomato_stained_glass", ExtravaganzaBlocks.TOMATO_STAINED_GLASS);
		ExtravaganzaBlocks.registerBlockWithItem("nymph_stained_glass", ExtravaganzaBlocks.NYMPH_STAINED_GLASS);
		ExtravaganzaColor.VALUES.forEach(color -> ExtravaganzaBlocks.registerBlockWithItem(color.asString() + "_trash_can", new TrashCanBlock(AbstractBlock.Settings.create().requiresTool().strength(2.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.LANTERN).mapColor(color.getMapColor()))));
		ExtravaganzaColor.VALUES.forEach(color -> ExtravaganzaBlocks.registerBlockWithItem(color.asString() + "_festive_rubber_ladder", new RubberLadderBlock(AbstractBlock.Settings.create().strength(1.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.PACKED_MUD).mapColor(color.getMapColor()))));
		ExtravaganzaBlocks.registerColoredBlockSet("festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("aligned_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("barred_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("bent_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("curved_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("dotted_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("festive_rubber_glass", AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.PACKED_MUD), TransparentBlock::new);
		ExtravaganzaBlocks.registerColoredBlockSet("festive_rubber_grate", AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.PACKED_MUD), TransparentBlock::new);
		ExtravaganzaBlocks.registerColoredBlockSet("padded_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("perforated_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("planked_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD));
		ExtravaganzaBlocks.registerColoredBlockSet("poured_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("scratched_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("scratched_festive_rubber_rotated_90", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("scratched_festive_rubber_rotated_180", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("scratched_festive_rubber_rotated_270", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("screwed_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("sharped_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("sharped_festive_rubber_rotated_90", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("sharped_festive_rubber_rotated_180", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("sharped_festive_rubber_rotated_270", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("slipped_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("split_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("striped_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("tiled_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("traversable_festive_rubber", AbstractBlock.Settings.create().suffocates(Blocks::never).nonOpaque().sounds(BlockSoundGroup.PACKED_MUD), TraversableRubberBlock::new, TraversableRubberStairsBlock::new, TraversableRubberSlabBlock::new, TraversableRubberWallBlock::new);
		ExtravaganzaBlocks.registerColoredBlockSet("windowed_festive_rubber", AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.PACKED_MUD), TransparentBlock::new);
		ExtravaganzaBlocks.registerColoredBlockSet("wooded_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD));
		ExtravaganzaBlocks.registerBlockWithItem("colorful_festive_rubber_ladder", new LadderBlock(COLORFUL_SETTINGS.get().nonOpaque()));
		ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_bricks", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_tiles", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_pavers", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_bent_festive_rubber", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_curved_festive_rubber", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_glass", COLORFUL_SETTINGS.get().nonOpaque(), TransparentBlock::new);
		ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_grate", COLORFUL_SETTINGS.get().nonOpaque(), TransparentBlock::new);
		ExtravaganzaBlocks.registerBlockSet("colorful_padded_festive_rubber", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_perforated_festive_rubber", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_scratched_festive_rubber", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_scratched_festive_rubber_rotated_90", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_scratched_festive_rubber_rotated_180", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_scratched_festive_rubber_rotated_270", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_screwed_festive_rubber", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_slipped_festive_rubber", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_striped_festive_rubber", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_tiled_festive_rubber", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_windowed_festive_rubber", COLORFUL_SETTINGS.get().nonOpaque(), TransparentBlock::new);
		ExtravaganzaBlocks.registerBlockSet("colorful_chiseled_festive_rubber", COLORFUL_SETTINGS.get());
	}

	private static void registerColoredBlockSet(String path, AbstractBlock.Settings settings) {
		ExtravaganzaBlocks.registerColoredBlockSet(path, settings, Block::new);
	}

	private static <T extends Block> void registerColoredBlockSet(String path, AbstractBlock.Settings settings, Function<AbstractBlock.Settings, T> blockFactory) {
		ExtravaganzaBlocks.registerColoredBlockSet(path, settings, blockFactory, StairsBlock::new, SlabBlock::new, WallBlock::new);
	}

	private static <T extends Block, S extends StairsBlock, L extends SlabBlock, W extends WallBlock> void registerColoredBlockSet(String path, AbstractBlock.Settings settings, Function<AbstractBlock.Settings, T> blockFactory, BiFunction<BlockState, AbstractBlock.Settings, S> stairsBlockFactory, Function<AbstractBlock.Settings, L> slabBlockFactory, Function<AbstractBlock.Settings, W> wallBlockFactory) {
		ExtravaganzaColor.VALUES.forEach(color -> ExtravaganzaBlocks.registerBlockSet(
			color.asString() + "_" + path,
			settings.strength(1.5f, 3.0f).mapColor(color.getMapColor()),
			blockFactory,
			stairsBlockFactory,
			slabBlockFactory,
			wallBlockFactory
		));
	}

	private static void registerBlockSet(String path, AbstractBlock.Settings settings) {
		ExtravaganzaBlocks.registerBlockSet(path, settings, Block::new);
	}

	private static <T extends Block> void registerBlockSet(String path, AbstractBlock.Settings settings, Function<AbstractBlock.Settings, T> blockFactory) {
		ExtravaganzaBlocks.registerBlockSet(path, settings, blockFactory, StairsBlock::new, SlabBlock::new, WallBlock::new);
	}

	private static <T extends Block, S extends StairsBlock, L extends SlabBlock, W extends WallBlock> void registerBlockSet(String path, AbstractBlock.Settings settings, Function<AbstractBlock.Settings, T> blockFactory, BiFunction<BlockState, AbstractBlock.Settings, S> stairsBlockFactory, Function<AbstractBlock.Settings, L> slabBlockFactory, Function<AbstractBlock.Settings, W> wallBlockFactory) {
		T block = blockFactory.apply(settings);
		ExtravaganzaBlocks.registerBlockWithItem(path, block);
		ExtravaganzaBlocks.registerBlockWithItem(Extravaganza.nameTweak(path) + "_stairs", stairsBlockFactory.apply(block.getDefaultState(), settings));
		ExtravaganzaBlocks.registerBlockWithItem(Extravaganza.nameTweak(path) + "_slab", slabBlockFactory.apply(settings));
		ExtravaganzaBlocks.registerBlockWithItem(Extravaganza.nameTweak(path) + "_wall", wallBlockFactory.apply(settings));
	}

	private static void registerBlockWithItem(String path, Block block) {
		Registry.register(Registries.BLOCK, Extravaganza.createId(path), block);
		Registry.register(Registries.ITEM, Extravaganza.createId(path), new BlockItem(block, new Item.Settings()));
	}
}
