package com.mmodding.extravaganza.block;

import com.mmodding.extravaganza.block.entity.CottonCandyMachineBlockEntity;
import com.mmodding.extravaganza.init.ExtravaganzaItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CottonCandyMachineBlock extends BaseEntityBlock {

	public static final MapCodec<CottonCandyMachineBlock> CODEC = simpleCodec(CottonCandyMachineBlock::new);

	public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

	public static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

	public CottonCandyMachineBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(CottonCandyMachineBlock.FACING, Direction.NORTH));
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CottonCandyMachineBlock.CODEC;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(CottonCandyMachineBlock.FACING);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CottonCandyMachineBlockEntity(pos, state);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!level.isClientSide()) {
			if (level.getBlockEntity(pos) instanceof CottonCandyMachineBlockEntity ccmbe) {
				player.sendOverlayMessage(Component.translatable("message.extravaganza.cotton_candy_machine", ccmbe.getSugarAmount()));
			}
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (player.getItemInHand(hand).is(ExtravaganzaItems.WRENCH_AGANZA) || player.getItemInHand(hand).is(Items.DEBUG_STICK)) {
			return InteractionResult.FAIL;
		}
		else {
			if (!level.isClientSide()) {
				if (level.getBlockEntity(pos) instanceof CottonCandyMachineBlockEntity ccmbe) {
					if (ccmbe.canIncreaseSugarAmount() && stack.is(Items.SUGAR)){
						if (player.isShiftKeyDown()) {
							ccmbe.increaseSugarAmount(stack.getCount());
							player.setItemInHand(hand, ItemStack.EMPTY);
						} else {
							ccmbe.increaseSugarAmount(1);
							player.setItemInHand(hand, new ItemStack(Items.SUGAR, stack.getCount() - 1));
						}
						player.sendOverlayMessage(Component.translatable("message.extravaganza.cotton_candy_machine", ccmbe.getSugarAmount()));
					}
					else if (ccmbe.canDecreaseSugarAmount() && stack.is(Items.STICK)) {
						int count = player.isShiftKeyDown() ? ccmbe.clampCottonCandyCount(stack.getCount()) : 1;
						ItemStack newStack = stack.getCount() - count == 0 ? ItemStack.EMPTY : new ItemStack(Items.STICK, stack.getCount() - count);
						ItemStack cottonCandyStack = new ItemStack(ExtravaganzaItems.COTTON_CANDY, count);
						if (newStack.isEmpty()) {
							player.setItemInHand(hand, cottonCandyStack);
						}
						else {
							player.setItemInHand(hand, newStack);
							if (!player.addItem(cottonCandyStack)) {
								player.drop(cottonCandyStack, false);
							}
						}
						ccmbe.decreaseSugarAmount(count * 4);
						player.sendOverlayMessage(Component.translatable("message.extravaganza.cotton_candy_machine", ccmbe.getSugarAmount()));
					}
				}
			}
			return InteractionResult.PASS;
		}
	}

	@Override
	protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
		return CottonCandyMachineBlock.SHAPE;
	}

	@Override
	protected BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(CottonCandyMachineBlock.FACING, rotation.rotate(state.getValue(CottonCandyMachineBlock.FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(CottonCandyMachineBlock.FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(CottonCandyMachineBlock.FACING, ctx.getHorizontalDirection().getOpposite());
	}
}
