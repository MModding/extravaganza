package com.mmodding.extravaganza.block.entity;

import com.mmodding.extravaganza.block.BallPitContentBlock;
import com.mmodding.extravaganza.init.ExtravaganzaBlockEntities;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BallPitRegistrationTableBlockEntity extends BlockEntity {

	private final PoolSettings poolSettings = new PoolSettings();

	private BlockPos scannedStart = BlockPos.ZERO;
	private BlockPos scannedEnd = BlockPos.ZERO;

	private SelectionMode selectionMode = SelectionMode.POSITIVE_X;

	private boolean source = true;

	public BallPitRegistrationTableBlockEntity(BlockPos pos, BlockState state) {
		super(ExtravaganzaBlockEntities.BALL_PIT_REGISTRATION_TABLE, pos, state);
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		ValueInput scannedPoolContent = input.childOrEmpty("scanned_pool_content");
		ValueInput startPos = scannedPoolContent.childOrEmpty("start_pos");
		ValueInput endPos = scannedPoolContent.childOrEmpty("end_pos");

		this.scannedStart = new BlockPos(startPos.getIntOr("x", 0), startPos.getIntOr("y", 0), startPos.getIntOr("z", 0));
		this.scannedEnd = new BlockPos(endPos.getIntOr("x", 0), endPos.getIntOr("y", 0), endPos.getIntOr("z", 0));
		this.poolSettings.fromNbt(input.childOrEmpty("pool_settings"));
		this.selectionMode = SelectionMode.valueOf(input.getStringOr("selection_mode", "positive_x").toUpperCase());
		this.source = input.getBooleanOr("source", true);
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		ValueOutput scannedPoolContent = output.child("scanned_pool_content");
		ValueOutput startPos = scannedPoolContent.child("start_pos");
		startPos.putInt("x", this.scannedStart.getX());
		startPos.putInt("y", this.scannedStart.getY());
		startPos.putInt("z", this.scannedStart.getZ());
		ValueOutput endPos = scannedPoolContent.child("end_pos");
		endPos.putInt("x", this.scannedEnd.getX());
		endPos.putInt("y", this.scannedEnd.getY());
		endPos.putInt("z", this.scannedEnd.getZ());
		this.poolSettings.save(output.child("pool_settings"));
		output.putString("selection_mode", this.selectionMode.getSerializedName());
		output.putBoolean("source", this.source);
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return this.saveCustomOnly(registries);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, BallPitRegistrationTableBlockEntity bpitbe) {
		for (BlockPos current : BlockPos.betweenClosed(bpitbe.getRelativeScannedStart(pos), bpitbe.getRelativeScannedEnd(pos))) {
			if (level.getBlockState(current).isAir()) {
				level.setBlock(current, ExtravaganzaBlocks.BALL_PIT_PROTECTION.defaultBlockState(), Block.UPDATE_ALL);
			}
			else if (level.getBlockState(current).is(ExtravaganzaBlocks.BALL_PIT_CONTENT)) {
				if (level.getBlockState(current).getValue(BallPitContentBlock.POWER) != bpitbe.getPoolSettings().power) {
					level.setBlock(current, level.getBlockState(current).setValue(BallPitContentBlock.POWER, bpitbe.getPoolSettings().power), Block.UPDATE_ALL);
				}
			}
		}
	}

	public BlockPos getScannedStart() {
		return this.scannedStart;
	}

	public BlockPos getScannedEnd() {
		return this.scannedEnd;
	}

	public AABB getFullScanned() {
		return new AABB(Vec3.atLowerCornerOf(this.getScannedStart()), Vec3.atLowerCornerOf(this.getScannedEnd())).expandTowards(1, 1, 1);
	}

	public BlockPos getRelativeScannedStart(BlockPos pos) {
		return pos.offset(this.getScannedStart());
	}

	public BlockPos getRelativeScannedEnd(BlockPos pos) {
		return pos.offset(this.getScannedEnd());
	}

	public AABB getRelativeFullScanned(BlockPos pos) {
		return new AABB(Vec3.atLowerCornerOf(this.getRelativeScannedStart(pos)), Vec3.atLowerCornerOf(this.getRelativeScannedEnd(pos))).expandTowards(1, 1, 1);
	}

	public PoolSettings getPoolSettings() {
		return this.poolSettings;
	}

	public SelectionMode getSelectionMode() {
		return this.selectionMode;
	}

	public boolean isSource() {
		return this.source;
	}

	public BlockPos getScannedCurrent() {
		return this.isSource() ? this.getScannedStart() : this.getScannedEnd();
	}

	public void setScannedStart(BlockPos pos, Consumer<BlockPos> deleter) {
		BlockPos previousStart = this.scannedStart;
		this.scannedStart = pos;
		BlockPos.betweenClosed(this.getBlockPos().offset(previousStart), this.getBlockPos().offset(this.scannedEnd)).forEach(
			current -> {
				if (!this.getFullScanned().contains(Vec3.atLowerCornerOf(current))) {
					deleter.accept(current);
				}
			}
		);
	}

	public void setScannedEnd(BlockPos pos, Consumer<BlockPos> deleter) {
		BlockPos previousEnd = this.scannedEnd;
		this.scannedEnd = pos;
		BlockPos.betweenClosed(this.getBlockPos().offset(this.scannedStart), this.getBlockPos().offset(previousEnd)).forEach(
			current -> {
				if (!this.getFullScanned().contains(Vec3.atLowerCornerOf(current))) {
					deleter.accept(current);
				}
			}
		);
	}

	public void setScannedCurrent(BlockPos pos, Consumer<BlockPos> deleter) {
		if (this.isSource()) {
			this.setScannedStart(pos, deleter);
		}
		else {
			this.setScannedEnd(pos, deleter);
		}
	}

	public void switchSelectionMode() {
		this.selectionMode = SelectionMode.values()[(this.selectionMode.ordinal() + 1) % SelectionMode.values().length];
	}

	public void switchSource() {
		this.source = !this.source;
	}

	public static class PoolSettings {

		public int power = 1;

		public void fromNbt(ValueInput nbt) {
			this.power = nbt.getIntOr("power", 1);
		}

		public void save(ValueOutput output) {
			output.putInt("power", this.power);
		}
	}

	public enum SelectionMode implements StringRepresentable {

		POSITIVE_X("positive_x"),
		NEGATIVE_X("negative_x"),
		POSITIVE_Y("positive_y"),
		NEGATIVE_Y("negative_y"),
		POSITIVE_Z("positive_z"),
		NEGATIVE_Z("negative_z"),
		SOURCE("source");

		private final String identifier;

		SelectionMode(String identifier) {
			this.identifier = identifier;
		}

		@Override
		public String getSerializedName() {
			return this.identifier;
		}
	}
}
