package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.block.*;
import com.mmodding.library.block.api.catalog.SimpleHorizontalFacingBlock;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.woodset.api.WoodSet;
import com.mmodding.library.woodset.api.WoodSetBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExtravaganzaBlocks {

	public static final WoodSet HEVEA_BRASILIENSIS = WoodSetBuilder.create(Extravaganza.id(), "hevea_brasiliensis", WoodTypeBuilder.copyOf(WoodType.OAK), BlockSetTypeBuilder.copyOf(BlockSetType.OAK))
		.buildAndRegister();

	public static final Block BALL_PIT_REGISTRATION_TABLE = new BallPitRegistrationTableBlock(BlockBehaviour.Properties.of().noOcclusion().strength(2.0f).sound(SoundType.WOOD));
	public static final Block BALL_PIT_CONTENT = new BallPitContentBlock(BlockBehaviour.Properties.of().noCollision().sound(SoundType.SLIME_BLOCK));
	public static final Block BALL_PIT_PROTECTION = new BallPitProtectionBlock(BlockBehaviour.Properties.of().noCollision().isValidSpawn(Blocks::never).isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::always).replaceable());

	public static final Block BALL_DISTRIBUTOR = new BallDistributorBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).noOcclusion().sound(SoundType.LANTERN));

	public static final Block POPCORN_MACHINE = new SimpleHorizontalFacingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).noOcclusion().sound(SoundType.LANTERN));

	public static final Block COTTON_CANDY_MACHINE = new CottonCandyMachineBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).noOcclusion().sound(SoundType.LANTERN));

	public static final Block GARLAND = new GarlandBlock(BlockBehaviour.Properties.of().strength(0.5f, 2.0f).noOcclusion().sound(SoundType.WOOD));

	public static final Block PINATA = new PinataBlock(BlockBehaviour.Properties.of().strength(0.5f, 2.0f).noOcclusion().sound(SoundType.WOOL));

	public static final Block CAUTION_WET_FLOOR_SIGN = new CautionWetFloorSignBlock(BlockBehaviour.Properties.of().strength(1.0f, 2.0f).mapColor(MapColor.COLOR_YELLOW).noOcclusion().sound(SoundType.WOOD));

	// Not meant to be climbable.
	public static final Block HANGING_LIGHTS = new LadderBlock(BlockBehaviour.Properties.of().instabreak().lightLevel(ignored -> 9).noOcclusion().sound(SoundType.WOOD));

	public static final Block TEAR_STAINED_GLASS = new TransparentBlock(BlockBehaviour.Properties.of().strength(1.5f, 3.0f).noOcclusion().sound(SoundType.GLASS));
	public static final Block PLANT_STAINED_GLASS = new TransparentBlock(BlockBehaviour.Properties.of().strength(1.5f, 3.0f).noOcclusion().sound(SoundType.GLASS));
	public static final Block TOMATO_STAINED_GLASS = new TransparentBlock(BlockBehaviour.Properties.of().strength(1.5f, 3.0f).noOcclusion().sound(SoundType.GLASS));
	public static final Block NYMPH_STAINED_GLASS = new TransparentBlock(BlockBehaviour.Properties.of().strength(1.5f, 3.0f).noOcclusion().sound(SoundType.GLASS));

	public static final Supplier<BlockBehaviour.Properties> COLORFUL_SETTINGS = () -> BlockBehaviour.Properties.of().strength(1.5f, 3.0f).mapColor(MapColor.ICE).sound(SoundType.PACKED_MUD);

	public static void register() {
		ExtravaganzaBlocks.registerBlockWithItem("ball_pit_registration_table", ExtravaganzaBlocks.BALL_PIT_REGISTRATION_TABLE);
		ExtravaganzaBlocks.registerBlockWithItem("ball_pit_content", ExtravaganzaBlocks.BALL_PIT_CONTENT);
		Registry.register(BuiltInRegistries.BLOCK, Extravaganza.createId("ball_pit_protection"), ExtravaganzaBlocks.BALL_PIT_PROTECTION);
		ExtravaganzaBlocks.registerBlockWithItem("ball_distributor", ExtravaganzaBlocks.BALL_DISTRIBUTOR);
		ExtravaganzaBlocks.registerBlockWithItem("popcorn_machine", ExtravaganzaBlocks.POPCORN_MACHINE);
		ExtravaganzaBlocks.registerBlockWithItem("cotton_candy_machine", ExtravaganzaBlocks.COTTON_CANDY_MACHINE);
		ExtravaganzaBlocks.registerBlockWithItem("garland", ExtravaganzaBlocks.GARLAND);
		ExtravaganzaBlocks.registerBlockWithItem("pinata", ExtravaganzaBlocks.PINATA);
		ExtravaganzaBlocks.registerBlockWithItem("caution_wet_floor_sign", ExtravaganzaBlocks.CAUTION_WET_FLOOR_SIGN);
		ExtravaganzaBlocks.registerBlockWithItem("hanging_lights", ExtravaganzaBlocks.HANGING_LIGHTS);
		ExtravaganzaBlocks.registerBlockWithItem("tear_stained_glass", ExtravaganzaBlocks.TEAR_STAINED_GLASS);
		ExtravaganzaBlocks.registerBlockWithItem("plant_stained_glass", ExtravaganzaBlocks.PLANT_STAINED_GLASS);
		ExtravaganzaBlocks.registerBlockWithItem("tomato_stained_glass", ExtravaganzaBlocks.TOMATO_STAINED_GLASS);
		ExtravaganzaBlocks.registerBlockWithItem("nymph_stained_glass", ExtravaganzaBlocks.NYMPH_STAINED_GLASS);
		ExtravaganzaColor.VALUES.forEach(color -> ExtravaganzaBlocks.registerBlockWithItem(color.getSerializedName() + "_ink_puddle", new FlattenedBlock(BlockBehaviour.Properties.of().instabreak().friction(0.98f).noOcclusion().isRedstoneConductor(Blocks::never).sound(SoundType.PACKED_MUD).mapColor(color.getMapColor()))));
		ExtravaganzaColor.VALUES.forEach(color -> ExtravaganzaBlocks.registerBlockWithItem(color.getSerializedName() + "_confetti", new FlattenedBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().isRedstoneConductor(Blocks::never).sound(SoundType.PACKED_MUD).mapColor(color.getMapColor()))));
		ExtravaganzaColor.VALUES.forEach(color -> ExtravaganzaBlocks.registerBlockWithItem(color.getSerializedName() + "_paper_lantern", new PaperLanternBlock(BlockBehaviour.Properties.of().strength(1.5f, 3.0f).lightLevel(ignored -> 13).noOcclusion().sound(SoundType.PACKED_MUD).mapColor(color.getMapColor()))));
		ExtravaganzaColor.VALUES.forEach(color -> ExtravaganzaBlocks.registerBlockWithItem(color.getSerializedName() + "_trash_can", new TrashCanBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).noOcclusion().sound(SoundType.LANTERN).mapColor(color.getMapColor()))));
		ExtravaganzaColor.VALUES.forEach(color -> ExtravaganzaBlocks.registerBlockWithItem(color.getSerializedName() + "_festive_rubber_ladder", new RubberLadderBlock(BlockBehaviour.Properties.of().strength(1.5f, 3.0f).noOcclusion().sound(SoundType.PACKED_MUD).mapColor(color.getMapColor()))));
		ExtravaganzaBlocks.registerColoredBlockSet("festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("aligned_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("barred_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("bent_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("curved_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("dotted_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("festive_rubber_glass", BlockBehaviour.Properties.of().noOcclusion().sound(SoundType.PACKED_MUD), TransparentBlock::new);
		ExtravaganzaBlocks.registerColoredBlockSet("festive_rubber_grate", BlockBehaviour.Properties.of().noOcclusion().sound(SoundType.PACKED_MUD), TransparentBlock::new);
		ExtravaganzaBlocks.registerColoredBlockSet("padded_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("perforated_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("planked_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.WOOD));
		ExtravaganzaBlocks.registerColoredBlockSet("poured_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("scratched_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("scratched_festive_rubber_rotated_90", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("scratched_festive_rubber_rotated_180", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("scratched_festive_rubber_rotated_270", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("screwed_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("sharped_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("sharped_festive_rubber_rotated_90", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("sharped_festive_rubber_rotated_180", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("sharped_festive_rubber_rotated_270", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("slipped_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("split_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("striped_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("tiled_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
		ExtravaganzaBlocks.registerColoredBlockSet("traversable_festive_rubber", BlockBehaviour.Properties.of().isSuffocating(Blocks::never).noOcclusion().sound(SoundType.PACKED_MUD), TraversableRubberBlock::new, TraversableRubberStairsBlock::new, TraversableRubberSlabBlock::new, TraversableRubberWallBlock::new);
		ExtravaganzaBlocks.registerColoredBlockSet("windowed_festive_rubber", BlockBehaviour.Properties.of().noOcclusion().sound(SoundType.PACKED_MUD), TransparentBlock::new);
		ExtravaganzaBlocks.registerColoredBlockSet("wooded_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.WOOD));
		ExtravaganzaBlocks.registerBlockWithItem("colorful_ink_puddle", new FlattenedBlock(COLORFUL_SETTINGS.get().instabreak().friction(0.98f).noOcclusion().isRedstoneConductor(Blocks::never)));
		ExtravaganzaBlocks.registerBlockWithItem("colorful_confetti", new FlattenedBlock(COLORFUL_SETTINGS.get().instabreak().noOcclusion().isRedstoneConductor(Blocks::never)));
		ExtravaganzaBlocks.registerBlockWithItem("colorful_paper_lantern", new PaperLanternBlock(COLORFUL_SETTINGS.get().lightLevel(_ -> 13).noOcclusion()));
		ExtravaganzaBlocks.registerBlockWithItem("colorful_festive_rubber_ladder", new LadderBlock(COLORFUL_SETTINGS.get().noOcclusion()));
		ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_bricks", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_tiles", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_pavers", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_bent_festive_rubber", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_curved_festive_rubber", COLORFUL_SETTINGS.get());
		ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_glass", COLORFUL_SETTINGS.get().noOcclusion(), TransparentBlock::new);
		ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_grate", COLORFUL_SETTINGS.get().noOcclusion(), TransparentBlock::new);
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
		ExtravaganzaBlocks.registerBlockSet("colorful_windowed_festive_rubber", COLORFUL_SETTINGS.get().noOcclusion(), TransparentBlock::new);
		ExtravaganzaBlocks.registerBlockSet("colorful_chiseled_festive_rubber", COLORFUL_SETTINGS.get());
	}

	private static void registerColoredBlockSet(String path, BlockBehaviour.Properties settings) {
		ExtravaganzaBlocks.registerColoredBlockSet(path, settings, Block::new);
	}

	private static <T extends Block> void registerColoredBlockSet(String path, BlockBehaviour.Properties settings, Function<BlockBehaviour.Properties, T> blockFactory) {
		ExtravaganzaBlocks.registerColoredBlockSet(path, settings, blockFactory, StairBlock::new, SlabBlock::new, WallBlock::new);
	}

	private static <T extends Block, S extends StairBlock, L extends SlabBlock, W extends WallBlock> void registerColoredBlockSet(String path, BlockBehaviour.Properties settings, Function<BlockBehaviour.Properties, T> blockFactory, BiFunction<BlockState, BlockBehaviour.Properties, S> stairsBlockFactory, Function<BlockBehaviour.Properties, L> slabBlockFactory, Function<BlockBehaviour.Properties, W> wallBlockFactory) {
		ExtravaganzaColor.VALUES.forEach(color -> ExtravaganzaBlocks.registerBlockSet(
			color.getSerializedName() + "_" + path,
			settings.strength(1.5f, 3.0f).mapColor(color.getMapColor()),
			blockFactory,
			stairsBlockFactory,
			slabBlockFactory,
			wallBlockFactory
		));
	}

	private static void registerBlockSet(String path, BlockBehaviour.Properties settings) {
		ExtravaganzaBlocks.registerBlockSet(path, settings, Block::new);
	}

	private static <T extends Block> void registerBlockSet(String path, BlockBehaviour.Properties settings, Function<BlockBehaviour.Properties, T> blockFactory) {
		ExtravaganzaBlocks.registerBlockSet(path, settings, blockFactory, StairBlock::new, SlabBlock::new, WallBlock::new);
	}

	private static <T extends Block, S extends StairBlock, L extends SlabBlock, W extends WallBlock> void registerBlockSet(String path, BlockBehaviour.Properties settings, Function<BlockBehaviour.Properties, T> blockFactory, BiFunction<BlockState, BlockBehaviour.Properties, S> stairsBlockFactory, Function<BlockBehaviour.Properties, L> slabBlockFactory, Function<BlockBehaviour.Properties, W> wallBlockFactory) {
		T block = blockFactory.apply(settings);
		ExtravaganzaBlocks.registerBlockWithItem(path, block);
		ExtravaganzaBlocks.registerBlockWithItem(Extravaganza.nameTweak(path) + "_stairs", stairsBlockFactory.apply(block.defaultBlockState(), settings));
		ExtravaganzaBlocks.registerBlockWithItem(Extravaganza.nameTweak(path) + "_slab", slabBlockFactory.apply(settings));
		ExtravaganzaBlocks.registerBlockWithItem(Extravaganza.nameTweak(path) + "_wall", wallBlockFactory.apply(settings));
	}

	private static void registerBlockWithItem(String path, Block block) {
		Registry.register(BuiltInRegistries.BLOCK, Extravaganza.createId(path), block);
		Registry.register(BuiltInRegistries.ITEM, Extravaganza.createId(path), new BlockItem(block, new Item.Properties()));
	}

	public static void register(AdvancedContainer mod) {
	}
}
