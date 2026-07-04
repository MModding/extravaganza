package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.block.*;
import com.mmodding.library.block.api.catalog.SimpleHorizontalFacingBlock;
import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.api.wrapper.BlockHeap;
import com.mmodding.library.block.api.wrapper.BlockRelatives;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.woodset.api.WoodSet;
import com.mmodding.library.woodset.api.WoodSetBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ExtravaganzaBlocks {

	public static final WoodSet HEVEA_BRASILIENSIS = WoodSetBuilder.create(
		Extravaganza.namespace(),
		"hevea_brasiliensis",
		WoodTypeBuilder.copyOf(WoodType.OAK),
		BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
	).buildAndRegister();

	public static final Block BALL_PIT_REGISTRATION_TABLE = register("ball_pit_registration_table", BallPitRegistrationTableBlock::new, BlockBehaviour.Properties.of().noOcclusion().strength(2.0f).sound(SoundType.WOOD)).registerItem();
	public static final Block BALL_PIT_CONTENT = register("ball_pit_content", BallPitContentBlock::new, BlockBehaviour.Properties.of().noCollision().sound(SoundType.SLIME_BLOCK)).registerItem();
	public static final Block BALL_PIT_PROTECTION = register("ball_pit_protection", BallPitProtectionBlock::new, BlockBehaviour.Properties.of().noCollision().isValidSpawn(Blocks::never).isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::always).replaceable()).registerItem();

	public static final Block BALL_DISTRIBUTOR = register("ball_distributor", BallDistributorBlock::new, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).noOcclusion().sound(SoundType.LANTERN)).registerItem();

	public static final Block POPCORN_MACHINE = register("popcorn_machine", SimpleHorizontalFacingBlock::new, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).noOcclusion().sound(SoundType.LANTERN)).registerItem();

	public static final Block COTTON_CANDY_MACHINE = register("cotton_candy_machine", CottonCandyMachineBlock::new, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).noOcclusion().sound(SoundType.LANTERN)).registerItem();

	public static final Block GARLAND = register("garland", GarlandBlock::new, BlockBehaviour.Properties.of().strength(0.5f, 2.0f).noOcclusion().sound(SoundType.WOOD)).registerItem();

	public static final Block PINATA = register("pinata", PinataBlock::new, BlockBehaviour.Properties.of().strength(0.5f, 2.0f).noOcclusion().sound(SoundType.WOOL)).registerItem();

	public static final Block CAUTION_WET_FLOOR_SIGN = register("caution_wet_floor_sign", CautionWetFloorSignBlock::new, BlockBehaviour.Properties.of().strength(1.0f, 2.0f).mapColor(MapColor.COLOR_YELLOW).noOcclusion().sound(SoundType.WOOD)).registerItem();

	// Not meant to be climbable.
	public static final Block HANGING_LIGHTS = register("hanging_lights", LadderBlock::new, BlockBehaviour.Properties.of().instabreak().lightLevel(ignored -> 9).noOcclusion().sound(SoundType.WOOD)).registerItem();

	public static final Block TEAR_STAINED_GLASS = register("tear_stained_glass", TransparentBlock::new, BlockBehaviour.Properties.of().strength(1.5f, 3.0f).noOcclusion().sound(SoundType.GLASS)).registerItem();
	public static final Block PLANT_STAINED_GLASS = register("plant_stained_glass", TransparentBlock::new, BlockBehaviour.Properties.of().strength(1.5f, 3.0f).noOcclusion().sound(SoundType.GLASS)).registerItem();
	public static final Block TOMATO_STAINED_GLASS = register("tomato_stained_glass", TransparentBlock::new, BlockBehaviour.Properties.of().strength(1.5f, 3.0f).noOcclusion().sound(SoundType.GLASS)).registerItem();
	public static final Block NYMPH_STAINED_GLASS = register("nymph_stained_glass", TransparentBlock::new, BlockBehaviour.Properties.of().strength(1.5f, 3.0f).noOcclusion().sound(SoundType.GLASS)).registerItem();

	public static final ExtravaganzaColoredVariants FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants ALIGNED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("aligned_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants BARRED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("barred_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants BENT_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("bent_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants CURVED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("curved_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants DOTTED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("dotted_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants FESTIVE_RUBBER_GLASS = ExtravaganzaBlocks.registerColoredBlockSet("festive_rubber_glass", BlockBehaviour.Properties.of().noOcclusion().sound(SoundType.PACKED_MUD), TransparentBlock::new);
	public static final ExtravaganzaColoredVariants FESTIVE_RUBBER_GRATE = ExtravaganzaBlocks.registerColoredBlockSet("festive_rubber_grate", BlockBehaviour.Properties.of().noOcclusion().sound(SoundType.PACKED_MUD), TransparentBlock::new);
	public static final ExtravaganzaColoredVariants PADDED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("padded_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants PERFORATED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("perforated_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants PLANKED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("planked_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.WOOD));
	public static final ExtravaganzaColoredVariants POURED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("poured_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants SCRATCHED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("scratched_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants SCRATCHED_FESTIVE_RUBBER_ROTATED_90 = ExtravaganzaBlocks.registerColoredBlockSet("scratched_festive_rubber_rotated_90", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants SCRATCHED_FESTIVE_RUBBER_ROTATED_180 = ExtravaganzaBlocks.registerColoredBlockSet("scratched_festive_rubber_rotated_180", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants SCRATCHED_FESTIVE_RUBBER_ROTATED_270 = ExtravaganzaBlocks.registerColoredBlockSet("scratched_festive_rubber_rotated_270", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants SCREWED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("screwed_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants SHARPED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("sharped_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants SHARPED_FESTIVE_RUBBER_ROTATED_90 = ExtravaganzaBlocks.registerColoredBlockSet("sharped_festive_rubber_rotated_90", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants SHARPED_FESTIVE_RUBBER_ROTATED_180 = ExtravaganzaBlocks.registerColoredBlockSet("sharped_festive_rubber_rotated_180", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants SHARPED_FESTIVE_RUBBER_ROTATED_270 = ExtravaganzaBlocks.registerColoredBlockSet("sharped_festive_rubber_rotated_270", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants SLIPPED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("slipped_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants SPLIT_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("split_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants STRIPED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("striped_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants TILED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("tiled_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD));
	public static final ExtravaganzaColoredVariants TRAVERSABLE_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("traversable_festive_rubber", BlockBehaviour.Properties.of().isSuffocating(Blocks::never).noOcclusion().sound(SoundType.PACKED_MUD), TraversableRubberBlock::new, TraversableRubberStairsBlock::new, TraversableRubberSlabBlock::new, TraversableRubberWallBlock::new);
	public static final ExtravaganzaColoredVariants WINDOWED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("windowed_festive_rubber", BlockBehaviour.Properties.of().noOcclusion().sound(SoundType.PACKED_MUD), TransparentBlock::new);
	public static final ExtravaganzaColoredVariants WOODED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerColoredBlockSet("wooded_festive_rubber", BlockBehaviour.Properties.of().sound(SoundType.WOOD));

	public static final BlockHeap INK_PUDDLE = BlockHeap.register(FlattenedBlock::new, sColor -> sColor + "_ink_puddle", sColor -> BlockBehaviour.Properties.of().instabreak().friction(0.98f).noOcclusion().isRedstoneConductor(Blocks::never).sound(SoundType.PACKED_MUD).mapColor(ExtravaganzaColor.fromString(sColor).getMapColor()), Extravaganza.namespace(), ExtravaganzaColor.STRINGS);
	public static final BlockHeap CONFETTI = BlockHeap.register(FlattenedBlock::new, sColor -> sColor + "_confetti", sColor -> BlockBehaviour.Properties.of().instabreak().noOcclusion().isRedstoneConductor(Blocks::never).sound(SoundType.PACKED_MUD).mapColor(ExtravaganzaColor.fromString(sColor).getMapColor()), Extravaganza.namespace(), ExtravaganzaColor.STRINGS);
	public static final BlockHeap PAPER_LANTERN = BlockHeap.register(PaperLanternBlock::new, sColor -> sColor + "_paper_lantern", sColor -> BlockBehaviour.Properties.of().strength(1.5f, 3.0f).lightLevel(ignored -> 13).noOcclusion().sound(SoundType.PACKED_MUD).mapColor(ExtravaganzaColor.fromString(sColor).getMapColor()), Extravaganza.namespace(), ExtravaganzaColor.STRINGS);
	public static final BlockHeap TRASH_CAN = BlockHeap.register(TrashCanBlock::new, sColor -> sColor + "_trash_can", sColor -> BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).noOcclusion().sound(SoundType.LANTERN).mapColor(ExtravaganzaColor.fromString(sColor).getMapColor()), Extravaganza.namespace(), ExtravaganzaColor.STRINGS);
	public static final BlockHeap FESTIVE_RUBBER_LADDER = BlockHeap.register(RubberLadderBlock::new, sColor -> sColor + "_festive_rubber_ladder", sColor -> BlockBehaviour.Properties.of().strength(1.5f, 3.0f).noOcclusion().sound(SoundType.PACKED_MUD).mapColor(ExtravaganzaColor.fromString(sColor).getMapColor()), Extravaganza.namespace(), ExtravaganzaColor.STRINGS);

	public static final Supplier<BlockBehaviour.Properties> COLORFUL_SETTINGS = () -> BlockBehaviour.Properties.of().strength(1.5f, 3.0f).mapColor(MapColor.ICE).sound(SoundType.PACKED_MUD);

	public static final BlockRelatives COLORFUL_FESTIVE_RUBBER = ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_FESTIVE_RUBBER_BRICKS = ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_bricks", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_FESTIVE_RUBBER_TILES = ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_tiles", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_FESTIVE_RUBBER_PAVERS = ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_pavers", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_BENT_FESTIVE_RUBBER = ExtravaganzaBlocks.registerBlockSet("colorful_bent_festive_rubber", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_CURVED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerBlockSet("colorful_curved_festive_rubber", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_FESTIVE_RUBBER_GLASS = ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_glass", COLORFUL_SETTINGS.get().noOcclusion(), TransparentBlock::new);
	public static final BlockRelatives COLORFUL_FESTIVE_RUBBER_GRATE = ExtravaganzaBlocks.registerBlockSet("colorful_festive_rubber_grate", COLORFUL_SETTINGS.get().noOcclusion(), TransparentBlock::new);
	public static final BlockRelatives COLORFUL_PADDED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerBlockSet("colorful_padded_festive_rubber", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_PERFORATED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerBlockSet("colorful_perforated_festive_rubber", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_SCRATCHED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerBlockSet("colorful_scratched_festive_rubber", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_SCRATCHED_FESTIVE_RUBBER_ROTATED_90 = ExtravaganzaBlocks.registerBlockSet("colorful_scratched_festive_rubber_rotated_90", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_SCRATCHED_FESTIVE_RUBBER_ROTATED_180 = ExtravaganzaBlocks.registerBlockSet("colorful_scratched_festive_rubber_rotated_180", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_SCRATCHED_FESTIVE_RUBBER_ROTATED_270 = ExtravaganzaBlocks.registerBlockSet("colorful_scratched_festive_rubber_rotated_270", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_SCREWED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerBlockSet("colorful_screwed_festive_rubber", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_SLIPPED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerBlockSet("colorful_slipped_festive_rubber", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_STRIPED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerBlockSet("colorful_striped_festive_rubber", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_TILED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerBlockSet("colorful_tiled_festive_rubber", COLORFUL_SETTINGS.get());
	public static final BlockRelatives COLORFUL_WINDOWED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerBlockSet("colorful_windowed_festive_rubber", COLORFUL_SETTINGS.get().noOcclusion(), TransparentBlock::new);
	public static final BlockRelatives COLORFUL_CHISELED_FESTIVE_RUBBER = ExtravaganzaBlocks.registerBlockSet("colorful_chiseled_festive_rubber", COLORFUL_SETTINGS.get());

	public static final Block COLORFUL_INK_PUDDLE = register("colorful_ink_puddle", FlattenedBlock::new, COLORFUL_SETTINGS.get().instabreak().friction(0.98f).noOcclusion().isRedstoneConductor(Blocks::never));
	public static final Block COLORFUL_CONFETTI = register("colorful_confetti", FlattenedBlock::new, COLORFUL_SETTINGS.get().instabreak().noOcclusion().isRedstoneConductor(Blocks::never));
	public static final Block COLORFUL_PAPER_LANTERN = register("colorful_paper_lantern", PaperLanternBlock::new, COLORFUL_SETTINGS.get().lightLevel(_ -> 13).noOcclusion());
	public static final Block COLORFUL_FESTIVE_RUBBER_LADDER = register("colorful_festive_rubber_ladder", RubberLadderBlock::new, COLORFUL_SETTINGS.get().noOcclusion());

	public static void register() {}

	private static ExtravaganzaColoredVariants registerColoredBlockSet(String path, BlockBehaviour.Properties settings) {
		return ExtravaganzaBlocks.registerColoredBlockSet(path, settings, Block::new);
	}

	private static <T extends Block> ExtravaganzaColoredVariants registerColoredBlockSet(String path, BlockBehaviour.Properties settings, BlockFactory<T> blockFactory) {
		return ExtravaganzaBlocks.registerColoredBlockSet(path, settings, blockFactory, StairBlock::new, SlabBlock::new, WallBlock::new);
	}

	private static <T extends Block, S extends StairBlock, L extends SlabBlock, W extends WallBlock> ExtravaganzaColoredVariants registerColoredBlockSet(String path, BlockBehaviour.Properties settings, BlockFactory<T> blockFactory, BiFunction<BlockState, BlockBehaviour.Properties, S> stairsBlockFactory, BlockFactory<L> slabBlockFactory, BlockFactory<W> wallBlockFactory) {
		Map<ExtravaganzaColor, BlockRelatives> variants = new Object2ObjectOpenHashMap<>();
		ExtravaganzaColor.VALUES.forEach(color -> variants.put(color, ExtravaganzaBlocks.registerBlockSet(
			color.getSerializedName() + "_" + path,
			settings.strength(1.5f, 3.0f).mapColor(color.getMapColor()),
			blockFactory,
			stairsBlockFactory,
			slabBlockFactory,
			wallBlockFactory
		)));
		return new ExtravaganzaColoredVariants(variants);
	}

	private static BlockRelatives registerBlockSet(String path, BlockBehaviour.Properties settings) {
		return ExtravaganzaBlocks.registerBlockSet(path, settings, Block::new);
	}

	private static <T extends Block> BlockRelatives registerBlockSet(String path, BlockBehaviour.Properties settings, BlockFactory<T> blockFactory) {
		return ExtravaganzaBlocks.registerBlockSet(path, settings, blockFactory, StairBlock::new, SlabBlock::new, WallBlock::new);
	}

	private static <T extends Block, S extends StairBlock, L extends SlabBlock, W extends WallBlock> BlockRelatives registerBlockSet(String path, BlockBehaviour.Properties settings, BlockFactory<T> blockFactory, BiFunction<BlockState, BlockBehaviour.Properties, S> stairsBlockFactory, BlockFactory<L> slabBlockFactory, BlockFactory<W> wallBlockFactory) {
		String tweaked = Extravaganza.nameTweak(path);
		String suffix = !path.equals(tweaked) ? "s" : "";
		BlockRelatives relatives = BlockRelatives.register(
			Extravaganza.createId(tweaked),
			BlockSetType.STONE,
			settings,
			suffix,
			blockFactory
		);
		relatives.register(BlockFamily.Variant.STAIRS, properties -> stairsBlockFactory.apply(relatives.getMain().defaultBlockState(), properties));
		relatives.register(BlockFamily.Variant.SLAB, slabBlockFactory);
		relatives.register(BlockFamily.Variant.WALL, wallBlockFactory);
		return relatives;
	}

	private static <T extends Block> Block register(String path, BlockFactory<T> factory, BlockBehaviour.Properties properties) {
		return Blocks.register(ResourceKey.create(Registries.BLOCK, Extravaganza.createId(path)), factory::make, properties);
	}

	public static void register(AdvancedContainer mod) {
	}

	public record ExtravaganzaColoredVariants(Map<ExtravaganzaColor, BlockRelatives> variants) {}
}
