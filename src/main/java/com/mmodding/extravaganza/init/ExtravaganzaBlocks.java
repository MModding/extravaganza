package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.block.*;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class ExtravaganzaBlocks {

	public static final Block BALL_POOL_CORE = new BallPoolCoreBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.BASALT));
	public static final Block BALL_POOL_CONTENT = new BallPoolContentBlock(AbstractBlock.Settings.create().noCollision().sounds(BlockSoundGroup.SLIME));

	public static final Block BALL_DISTRIBUTOR = new BallDistributorBlock(AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.LANTERN));

	public static final Block GARLAND = new GarlandBlock(AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.WOOD));

	public static final Block PINATA = new PinataBlock(AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.WOOL));

	public static final Block TEAR_STAINED_GLASS = new TransparentBlock(AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.GLASS));
	public static final Block PLANT_STAINED_GLASS = new TransparentBlock(AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.GLASS));
	public static final Block TOMATO_STAINED_GLASS = new TransparentBlock(AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.GLASS));
	public static final Block NYMPH_STAINED_GLASS = new TransparentBlock(AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.GLASS));

	public static void register() {
		ExtravaganzaBlocks.registerBlockWithItem("ball_pool_core", ExtravaganzaBlocks.BALL_POOL_CORE);
		ExtravaganzaBlocks.registerBlockWithItem("ball_pool_content", ExtravaganzaBlocks.BALL_POOL_CONTENT);
		ExtravaganzaBlocks.registerBlockWithItem("ball_distributor", ExtravaganzaBlocks.BALL_DISTRIBUTOR);
		ExtravaganzaBlocks.registerBlockWithItem("garland", ExtravaganzaBlocks.GARLAND);
		ExtravaganzaBlocks.registerBlockWithItem("pinata", ExtravaganzaBlocks.PINATA);
		ExtravaganzaBlocks.registerBlockWithItem("tear_stained_glass", ExtravaganzaBlocks.TEAR_STAINED_GLASS);
		ExtravaganzaBlocks.registerBlockWithItem("plant_stained_glass", ExtravaganzaBlocks.PLANT_STAINED_GLASS);
		ExtravaganzaBlocks.registerBlockWithItem("tomato_stained_glass", ExtravaganzaBlocks.TOMATO_STAINED_GLASS);
		ExtravaganzaBlocks.registerBlockWithItem("nymph_stained_glass", ExtravaganzaBlocks.NYMPH_STAINED_GLASS);
		ExtravaganzaBlocks.registerColoredBlockSet("festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("striped_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("poured_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("sharped_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("scratched_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("dotted_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("screwed_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("split_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("wooded_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD));
		ExtravaganzaBlocks.registerColoredBlockSet("festive_rubber_grate", AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("barred_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("perforated_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("slipped_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("padded_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("curved_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("bent_festive_rubber", AbstractBlock.Settings.create().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("windowed_festive_rubber", AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaBlocks.registerColoredBlockSet("tiled_festive_rubber", AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.SLIME));
		ExtravaganzaColor.VALUES.forEach(color -> ExtravaganzaBlocks.registerBlockWithItem(color.asString() + "_festive_rubber_ladder", new LadderBlock(AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.SLIME))));
	}

	private static void registerBlockWithItem(String path, Block block) {
		Registry.register(Registries.BLOCK, Extravaganza.createId(path), block);
		Registry.register(Registries.ITEM, Extravaganza.createId(path), new BlockItem(block, new Item.Settings()));
	}

	private static void registerColoredBlockSet(String path, AbstractBlock.Settings settings) {
		ExtravaganzaColor.VALUES.forEach(
			color -> {
				Block block = new Block(settings);
				ExtravaganzaBlocks.registerBlockWithItem(color.asString() + "_" + path, block);
				ExtravaganzaBlocks.registerBlockWithItem(color.asString() + "_" + path + "_stairs", new StairsBlock(block.getDefaultState(), settings));
				ExtravaganzaBlocks.registerBlockWithItem(color.asString() + "_" + path + "_slab", new SlabBlock(settings));
				ExtravaganzaBlocks.registerBlockWithItem(color.asString() + "_" + path + "_wall", new WallBlock(settings));
			}
		);
	}
}
