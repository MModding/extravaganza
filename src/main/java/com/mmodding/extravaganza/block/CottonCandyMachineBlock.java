package com.mmodding.extravaganza.block;

import com.mmodding.extravaganza.block.entity.CottonCandyMachineBlockEntity;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CottonCandyMachineBlock extends BlockWithEntity {

	public static final MapCodec<CottonCandyMachineBlock> CODEC = CottonCandyMachineBlock.createCodec(CottonCandyMachineBlock::new);

	public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

	public static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

	public CottonCandyMachineBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(CottonCandyMachineBlock.FACING, Direction.NORTH));
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return CottonCandyMachineBlock.CODEC;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CottonCandyMachineBlockEntity(pos, state);
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (!world.isClient()) {
			if (world.getBlockEntity(pos) instanceof CottonCandyMachineBlockEntity ccmbe) {
				player.sendMessage(Text.translatable("message.extravaganza.cotton_candy_machine", ccmbe.getSugarAmount()), true);
			}
		}
		return ActionResult.SUCCESS;
	}

	@Override
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player.getStackInHand(hand).isOf(ExtravaganzaItems.WRENCH_AGANZA) || player.getStackInHand(hand).isOf(Items.DEBUG_STICK)) {
			return ItemActionResult.FAIL;
		}
		else {
			if (!world.isClient()) {
				if (world.getBlockEntity(pos) instanceof CottonCandyMachineBlockEntity ccmbe) {
					if (ccmbe.canIncreaseSugarAmount() && stack.isOf(Items.SUGAR)){
						if (player.isSneaking()) {
							ccmbe.increaseSugarAmount(stack.getCount());
							player.setStackInHand(hand, ItemStack.EMPTY);
						} else {
							ccmbe.increaseSugarAmount(1);
							player.setStackInHand(hand, new ItemStack(Items.SUGAR, stack.getCount() - 1));
						}
						player.sendMessage(Text.translatable("message.extravaganza.cotton_candy_machine", ccmbe.getSugarAmount()), true);
					}
					else if (ccmbe.canDecreaseSugarAmount() && stack.isOf(Items.STICK)) {
						int count = player.isSneaking() ? ccmbe.clampCottonCandyCount(stack.getCount()) : 1;
						ItemStack newStack = stack.getCount() - count == 0 ? ItemStack.EMPTY : new ItemStack(Items.STICK, stack.getCount() - count);
						ItemStack cottonCandyStack = new ItemStack(ExtravaganzaItems.COTTON_CANDY, count);
						if (newStack.isEmpty()) {
							player.setStackInHand(hand, cottonCandyStack);
						}
						else {
							player.setStackInHand(hand, newStack);
							if (!player.giveItemStack(cottonCandyStack)) {
								player.dropItem(cottonCandyStack, false);
							}
						}
						ccmbe.decreaseSugarAmount(count * 4);
						player.sendMessage(Text.translatable("message.extravaganza.cotton_candy_machine", ccmbe.getSugarAmount()), true);
					}
				}
			}
			return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		}
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return CottonCandyMachineBlock.SHAPE;
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(CottonCandyMachineBlock.FACING, rotation.rotate(state.get(CottonCandyMachineBlock.FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(CottonCandyMachineBlock.FACING)));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(CottonCandyMachineBlock.FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(CottonCandyMachineBlock.FACING);
	}
}
