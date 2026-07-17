package com.mmodding.extravaganza.data;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.block.BallDistributorBlock;
import com.mmodding.extravaganza.block.GarlandBlock;
import com.mmodding.extravaganza.block.TrashCanBlock;
import com.mmodding.extravaganza.client.color.item.ExtravaganzaColorSource;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import com.mmodding.library.datagen.api.model.block.DefaultBlockModelProcessing;
import com.mojang.math.Quadrant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class ExtravaganzaBlockModelProcessors {

	public static void createColorful(BlockModelGenerators generator, Block block) {
		Variant model = plainModel(TexturedModel.CARPET.create(block, generator.modelOutput));
		generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, createRotatedVariants(model)));
		generator.registerSimpleFlatItemModel(block);
	}

	public static void createInkPuddleOrConfetti(BlockModelGenerators generator, Block block) {
		String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
		String name = path.contains("confetti") ? "confetti" : "ink_puddle";
		Variant model = plainModel(Extravaganza.createId("block/" + name));
		generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, createRotatedVariants(model)));
		generator.registerSimpleTintedItemModel(block, Extravaganza.createId("item/" + name), new ExtravaganzaColorSource(ExtravaganzaColor.fromString(path.substring(0, path.length() - name.length() - 1))));
	}

	public static void createPaperLantern(BlockModelGenerators generator, Block block) {
		Identifier lantern = ExtravaganzaTexturedModels.PAPER_LANTERN.create(block, generator.modelOutput);
		Identifier hangingLantern = ExtravaganzaTexturedModels.HANGING_PAPER_LANTERN.createWithSuffix(block, "_hanging", generator.modelOutput);
		generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(
			PropertyDispatch.initial(BlockStateProperties.HANGING)
				.select(false, plainVariant(lantern))
				.select(true, plainVariant(hangingLantern))
		));
		generator.registerSimpleFlatItemModel(block.asItem());
	}

	public static void createTrashCan(BlockModelGenerators generator, Block block) {
		generator.registerSimpleFlatItemModel(block.asItem());
		Identifier trashCan = ExtravaganzaTexturedModels.TRASH_CAN.create(block, generator.modelOutput);
		Identifier trashCanLid = ExtravaganzaTexturedModels.TRASH_CAN_LID.createWithSuffix(block, "_lid", generator.modelOutput);
		Identifier trashCanLidOpen = ExtravaganzaTexturedModels.TRASH_CAN_LID_OPEN.createWithSuffix(block, "_lid_open", generator.modelOutput);
		generator.blockStateOutput.accept(
			MultiPartGenerator.multiPart(block)
				.with(plainVariant(trashCan))
				.with(new ConditionBuilder().term(TrashCanBlock.OPEN, false).build(), plainVariant(trashCanLid))
				.with(
					new ConditionBuilder().term(TrashCanBlock.FACING, Direction.NORTH).term(TrashCanBlock.OPEN, true),
					plainVariant(trashCanLidOpen)
				)
				.with(
					new ConditionBuilder().term(TrashCanBlock.FACING, Direction.EAST).term(TrashCanBlock.OPEN, true),
					plainVariant(trashCanLidOpen).with(VariantMutator.Y_ROT.withValue(Quadrant.R90))
				)
				.with(
					new ConditionBuilder().term(TrashCanBlock.FACING, Direction.SOUTH).term(TrashCanBlock.OPEN, true),
					plainVariant(trashCanLidOpen).with(VariantMutator.Y_ROT.withValue(Quadrant.R180))
				)
				.with(
					new ConditionBuilder().term(TrashCanBlock.FACING, Direction.WEST).term(TrashCanBlock.OPEN, true),
					plainVariant(trashCanLidOpen).with(VariantMutator.Y_ROT.withValue(Quadrant.R270))
				)
		);
	}

	public static void createGarland(BlockModelGenerators generator, Block block) {
		generator.registerSimpleFlatItemModel(block.asItem());
		Identifier garland = Extravaganza.createId("block/garland");
		Identifier horizontal = Extravaganza.createId("block/garland_horizontal");
		Identifier vertical = Extravaganza.createId("block/garland_vertical");
		Identifier up = Extravaganza.createId("block/garland_up");
		Identifier down = Extravaganza.createId("block/garland_down");
		Identifier north = Extravaganza.createId("block/garland_north");
		Identifier south = Extravaganza.createId("block/garland_south");
		Identifier east = Extravaganza.createId("block/garland_east");
		Identifier west = Extravaganza.createId("block/garland_west");
		Identifier attachedUp = Extravaganza.createId("block/attached_garland_up");
		Identifier attachedDown = Extravaganza.createId("block/attached_garland_down");
		Identifier attachedNorth = Extravaganza.createId("block/attached_garland_north");
		Identifier attachedSouth = Extravaganza.createId("block/attached_garland_south");
		Identifier attachedEast = Extravaganza.createId("block/attached_garland_east");
		Identifier attachedWest = Extravaganza.createId("block/attached_garland_west");
		ConditionBuilder none = new ConditionBuilder()
			.term(BlockStateProperties.UP, false)
			.term(BlockStateProperties.DOWN, false)
			.term(BlockStateProperties.NORTH, false)
			.term(BlockStateProperties.EAST, false)
			.term(BlockStateProperties.SOUTH, false)
			.term(BlockStateProperties.WEST, false);
		generator.blockStateOutput.accept(
			MultiPartGenerator.multiPart(ExtravaganzaBlocks.GARLAND)
				.with(
					or(
						none,
						new ConditionBuilder().term(BlockStateProperties.UP, true),
						new ConditionBuilder().term(BlockStateProperties.DOWN, true),
						new ConditionBuilder().term(BlockStateProperties.NORTH, true),
						new ConditionBuilder().term(BlockStateProperties.SOUTH, true)
					),
					plainVariant(garland)
				)
				.with(
					or(
						none,
						new ConditionBuilder().term(BlockStateProperties.NORTH, true),
						new ConditionBuilder().term(BlockStateProperties.SOUTH, true),
						new ConditionBuilder().term(BlockStateProperties.EAST, true),
						new ConditionBuilder().term(BlockStateProperties.WEST, true)
					),
					plainVariant(horizontal)
				)
				.with(
					or(
						none,
						new ConditionBuilder().term(BlockStateProperties.UP, true),
						new ConditionBuilder().term(BlockStateProperties.DOWN, true),
						new ConditionBuilder().term(BlockStateProperties.EAST, true),
						new ConditionBuilder().term(BlockStateProperties.WEST, true)
					),
					plainVariant(vertical)
				)
				.with(
					new ConditionBuilder()
						.term(BlockStateProperties.UP, true)
						.term(GarlandBlock.ATTACHED_UP, false)
						.build(),
					plainVariant(up)
				)
				.with(
					new ConditionBuilder()
						.term(BlockStateProperties.DOWN, true)
						.term(GarlandBlock.ATTACHED_DOWN, false)
						.build(),
					plainVariant(down)
				)
				.with(
					new ConditionBuilder()
						.term(BlockStateProperties.NORTH, true)
						.term(GarlandBlock.ATTACHED_NORTH, false)
						.build(),
					plainVariant(north)
				)
				.with(
					new ConditionBuilder()
						.term(BlockStateProperties.SOUTH, true)
						.term(GarlandBlock.ATTACHED_SOUTH, false)
						.build(),
					plainVariant(south)
				)
				.with(
					new ConditionBuilder()
						.term(BlockStateProperties.EAST, true)
						.term(GarlandBlock.ATTACHED_EAST, false)
						.build(),
					plainVariant(east)
				)
				.with(
					new ConditionBuilder()
						.term(BlockStateProperties.WEST, true)
						.term(GarlandBlock.ATTACHED_WEST, false)
						.build(),
					plainVariant(west)
				)
				.with(
					new ConditionBuilder()
						.term(BlockStateProperties.UP, true)
						.term(GarlandBlock.ATTACHED_UP, true)
						.build(),
					plainVariant(attachedUp)
				)
				.with(
					new ConditionBuilder()
						.term(BlockStateProperties.DOWN, true)
						.term(GarlandBlock.ATTACHED_DOWN, true)
						.build(),
					plainVariant(attachedDown)
				)
				.with(
					new ConditionBuilder()
						.term(BlockStateProperties.NORTH, true)
						.term(GarlandBlock.ATTACHED_NORTH, true)
						.build(),
					plainVariant(attachedNorth)
				)
				.with(
					new ConditionBuilder()
						.term(BlockStateProperties.SOUTH, true)
						.term(GarlandBlock.ATTACHED_SOUTH, true)
						.build(),
					plainVariant(attachedSouth)
				)
				.with(
					new ConditionBuilder()
						.term(BlockStateProperties.EAST, true)
						.term(GarlandBlock.ATTACHED_EAST, true)
						.build(),
					plainVariant(attachedEast)
				)
				.with(
					new ConditionBuilder()
						.term(BlockStateProperties.WEST, true)
						.term(GarlandBlock.ATTACHED_WEST, true)
						.build(),
					plainVariant(attachedWest)
				)
		);
	}

	public static void createHorizontalWithFlatItem(BlockModelGenerators generator, Block block) {
		DefaultBlockModelProcessing.createDefinedModelHorizontalVariants(generator, block);
		generator.registerSimpleFlatItemModel(block.asItem());
	}

	public static void createBallDistributor(BlockModelGenerators generator, Block block) {
		Identifier up = Extravaganza.createId("block/ball_distributor_up");
		Identifier down = Extravaganza.createId("block/ball_distributor_down");
		generator.blockStateOutput.accept(
			MultiVariantGenerator.dispatch(block).with(
				PropertyDispatch.initial(BallDistributorBlock.FACING, BallDistributorBlock.HALF)
					.select(Direction.NORTH, DoubleBlockHalf.UPPER, plainVariant(up))
					.select(Direction.EAST, DoubleBlockHalf.UPPER, plainVariant(up).with(VariantMutator.Y_ROT.withValue(Quadrant.R0)))
					.select(Direction.SOUTH, DoubleBlockHalf.UPPER, plainVariant(up).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)))
					.select(Direction.WEST, DoubleBlockHalf.UPPER, plainVariant(up).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)))
					.select(Direction.NORTH, DoubleBlockHalf.LOWER, plainVariant(down))
					.select(Direction.EAST, DoubleBlockHalf.LOWER, plainVariant(down).with(VariantMutator.Y_ROT.withValue(Quadrant.R0)))
					.select(Direction.SOUTH, DoubleBlockHalf.LOWER, plainVariant(down).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)))
					.select(Direction.WEST, DoubleBlockHalf.LOWER, plainVariant(down).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)))
			)
		);
		generator.registerSimpleFlatItemModel(block.asItem());
	}
}
