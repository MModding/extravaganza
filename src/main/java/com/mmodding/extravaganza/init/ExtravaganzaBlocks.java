package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.block.BallPoolContentBlock;
import com.mmodding.extravaganza.block.BallPoolCoreBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ExtravaganzaBlocks {

	public static final Block BALL_POOL_CORE = new BallPoolCoreBlock(AbstractBlock.Settings.create());
	public static final Block BALL_POOL_CONTENT = new BallPoolContentBlock(AbstractBlock.Settings.create().noCollision());

	public static void register() {
		ExtravaganzaBlocks.registerBlockWithItem("ball_pool_core", ExtravaganzaBlocks.BALL_POOL_CORE);
		ExtravaganzaBlocks.registerBlockWithItem("ball_pool_content", ExtravaganzaBlocks.BALL_POOL_CONTENT);
		ExtravaganzaBlocks.registerColoredBlockSet("striped_festive_rubber", AbstractBlock.Settings.create());
		ExtravaganzaBlocks.registerColoredBlockSet("poured_festive_rubber", AbstractBlock.Settings.create());
		ExtravaganzaBlocks.registerColoredBlockSet("sharped_festive_rubber", AbstractBlock.Settings.create());
		ExtravaganzaBlocks.registerColoredBlockSet("scratched_festive_rubber", AbstractBlock.Settings.create());
		ExtravaganzaBlocks.registerColoredBlockSet("dotted_festive_rubber", AbstractBlock.Settings.create());
		ExtravaganzaBlocks.registerColoredBlockSet("screwed_festive_rubber", AbstractBlock.Settings.create());
		ExtravaganzaBlocks.registerColoredBlockSet("festive_rubber", AbstractBlock.Settings.create());
		ExtravaganzaBlocks.registerColoredBlockSet("wooded_festive_rubber", AbstractBlock.Settings.create());
	}

	private static void registerBlockWithItem(String path, Block block) {
		Registry.register(Registries.BLOCK, Extravaganza.createId(path), block);
		Registry.register(Registries.ITEM, Extravaganza.createId(path), new BlockItem(block, new Item.Settings()));
	}

	private static void registerColoredBlockSet(String path, AbstractBlock.Settings settings) {
		Extravaganza.COLOR_QUALIFIERS.forEach(
			color -> ExtravaganzaBlocks.registerBlockWithItem(color + "_" + path, new Block(settings))
		);
	}
}
