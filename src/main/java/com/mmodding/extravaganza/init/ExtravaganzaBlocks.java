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

	public static final Block HEVEA_BRASILIENSIS_SAPLING = new SaplingBlock(new SaplingGenerator("hevea_brasiliensis", Optional.empty(), Optional.of(ExtravaganzaWorldGeneration.HEVEA_BRASILIENSIS), Optional.empty()), AbstractBlock.Settings.create().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.AZALEA_LEAVES));

	public static final Block BALL_POOL_REGISTRATION_TABLE = new BallPoolRegistrationTableBlock(AbstractBlock.Settings.create().nonOpaque().strength(2.0f).sounds(BlockSoundGroup.WOOD));
	public static final Block BALL_POOL_CONTENT = new BallPoolContentBlock(AbstractBlock.Settings.create().noCollision().sounds(BlockSoundGroup.SLIME));
	public static final Block BALL_POOL_PROTECTION = new BallPoolProtectionBlock(AbstractBlock.Settings.create().noCollision().allowsSpawning(Blocks::never).solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::always).replaceable());

	public static final Block BALL_DISTRIBUTOR = new BallDistributorBlock(AbstractBlock.Settings.create().requiresTool().strength(2.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.LANTERN));

	public static final Block POPCORN_MACHINE = new PopcornMachineBlock(AbstractBlock.Settings.create().requiresTool().strength(2.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.LANTERN));

	public static final Block GARLAND = new GarlandBlock(AbstractBlock.Settings.create().strength(0.5f, 2.0f).nonOpaque().sounds(BlockSoundGroup.WOOD));

	public static final Block PINATA = new PinataBlock(AbstractBlock.Settings.create().strength(0.5f, 2.0f).nonOpaque().sounds(BlockSoundGroup.WOOL));

	public static final Block TEAR_STAINED_GLASS = new TransparentBlock(AbstractBlock.Settings.create().strength(1.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.GLASS));
	public static final Block PLANT_STAINED_GLASS = new TransparentBlock(AbstractBlock.Settings.create().strength(1.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.GLASS));
	public static final Block TOMATO_STAINED_GLASS = new TransparentBlock(AbstractBlock.Settings.create().strength(1.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.GLASS));
	public static final Block NYMPH_STAINED_GLASS = new TransparentBlock(AbstractBlock.Settings.create().strength(1.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.GLASS));

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
		ExtravaganzaBlocks.registerBlockWithItem("ball_pool_registration_table", ExtravaganzaBlocks.BALL_POOL_REGISTRATION_TABLE);
		ExtravaganzaBlocks.registerBlockWithItem("ball_pool_content", ExtravaganzaBlocks.BALL_POOL_CONTENT);
		Registry.register(Registries.BLOCK, "ball_pool_protection", ExtravaganzaBlocks.BALL_POOL_PROTECTION);
		ExtravaganzaBlocks.registerBlockWithItem("ball_distributor", ExtravaganzaBlocks.BALL_DISTRIBUTOR);
		ExtravaganzaBlocks.registerBlockWithItem("popcorn_machine", ExtravaganzaBlocks.POPCORN_MACHINE);
		ExtravaganzaBlocks.registerBlockWithItem("garland", ExtravaganzaBlocks.GARLAND);
		ExtravaganzaBlocks.registerBlockWithItem("pinata", ExtravaganzaBlocks.PINATA);
		ExtravaganzaBlocks.registerBlockWithItem("tear_stained_glass", ExtravaganzaBlocks.TEAR_STAINED_GLASS);
		ExtravaganzaBlocks.registerBlockWithItem("plant_stained_glass", ExtravaganzaBlocks.PLANT_STAINED_GLASS);
		ExtravaganzaBlocks.registerBlockWithItem("tomato_stained_glass", ExtravaganzaBlocks.TOMATO_STAINED_GLASS);
		ExtravaganzaBlocks.registerBlockWithItem("nymph_stained_glass", ExtravaganzaBlocks.NYMPH_STAINED_GLASS);
		ExtravaganzaColor.VALUES.forEach(color -> ExtravaganzaBlocks.registerBlockWithItem(color.asString() + "_trash_can", new TrashCanBlock(AbstractBlock.Settings.create().requiresTool().strength(2.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.LANTERN))));
		ExtravaganzaColor.VALUES.forEach(color -> ExtravaganzaBlocks.registerBlockWithItem(color.asString() + "_festive_rubber_ladder", new LadderBlock(AbstractBlock.Settings.create().strength(1.5f, 3.0f).nonOpaque().sounds(BlockSoundGroup.SLIME))));
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
	}

	private static void registerBlockWithItem(String path, Block block) {
		Registry.register(Registries.BLOCK, Extravaganza.createId(path), block);
		Registry.register(Registries.ITEM, Extravaganza.createId(path), new BlockItem(block, new Item.Settings()));
	}

	private static void registerColoredBlockSet(String path, AbstractBlock.Settings settings) {
		ExtravaganzaColor.VALUES.forEach(
			color -> {
				Block block = new Block(settings.strength(1.5f, 3.0f));
				ExtravaganzaBlocks.registerBlockWithItem(color.asString() + "_" + path, block);
				ExtravaganzaBlocks.registerBlockWithItem(color.asString() + "_" + path + "_stairs", new StairsBlock(block.getDefaultState(), settings));
				ExtravaganzaBlocks.registerBlockWithItem(color.asString() + "_" + path + "_slab", new SlabBlock(settings));
				ExtravaganzaBlocks.registerBlockWithItem(color.asString() + "_" + path + "_wall", new WallBlock(settings));
			}
		);
	}
}
