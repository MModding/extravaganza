package com.mmodding.extravaganza.block.entity;

import com.mmodding.extravaganza.block.BallPoolContentBlock;
import com.mmodding.extravaganza.init.ExtravaganzaBlockEntities;
import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BallPoolRegistrationTableBlockEntity extends BlockEntity  {

	private final PoolSettings poolSettings = new PoolSettings();

	private BlockPos scannedStart = BlockPos.ORIGIN;
	private BlockPos scannedEnd = BlockPos.ORIGIN;

	private SelectionMode selectionMode = SelectionMode.POSITIVE_X;

	private boolean source = true;

	public BallPoolRegistrationTableBlockEntity(BlockPos pos, BlockState state) {
		super(ExtravaganzaBlockEntities.BALL_POOl_REGISTRATION_TABLE, pos, state);
	}

	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		NbtCompound scannedPoolContent = nbt.getCompound("scanned_pool_content");
		NbtCompound startPos = scannedPoolContent.getCompound("start_pos");
		NbtCompound endPos = scannedPoolContent.getCompound("end_pos");

		this.scannedStart = new BlockPos(startPos.getInt("x"), startPos.getInt("y"), startPos.getInt("z"));
		this.scannedEnd = new BlockPos(endPos.getInt("x"), endPos.getInt("y"), endPos.getInt("z"));
		this.poolSettings.fromNbt(nbt.getCompound("pool_settings"));
		this.selectionMode = SelectionMode.valueOf(nbt.getString("selection_mode").toUpperCase());
		this.source = nbt.getBoolean("source");
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		NbtCompound startPos = new NbtCompound();
		startPos.putInt("x", this.scannedStart.getX());
		startPos.putInt("y", this.scannedStart.getY());
		startPos.putInt("z", this.scannedStart.getZ());
		NbtCompound endPos = new NbtCompound();
		endPos.putInt("x", this.scannedEnd.getX());
		endPos.putInt("y", this.scannedEnd.getY());
		endPos.putInt("z", this.scannedEnd.getZ());
		NbtCompound scannedPoolContent = new NbtCompound();
		scannedPoolContent.put("start_pos", startPos);
		scannedPoolContent.put("end_pos", endPos);
		nbt.put("scanned_pool_content", scannedPoolContent);
		nbt.put("pool_settings", this.poolSettings.toNbt());
		nbt.putString("selection_mode", this.selectionMode.asString());
		nbt.putBoolean("source", this.source);
	}

	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		return this.createComponentlessNbt(registryLookup);
	}

	public static void tick(World world, BlockPos pos, BlockState state, BallPoolRegistrationTableBlockEntity bpitbe) {
		for (BlockPos current : BlockPos.iterate(bpitbe.getRelativeScannedStart(pos), bpitbe.getRelativeScannedEnd(pos))) {
			if (world.getBlockState(current).isAir()) {
				world.setBlockState(current, ExtravaganzaBlocks.BALL_POOL_PROTECTION.getDefaultState());
			}
			else if (world.getBlockState(current).isOf(ExtravaganzaBlocks.BALL_POOL_CONTENT)) {
				if (world.getBlockState(current).get(BallPoolContentBlock.POWER) != bpitbe.getPoolSettings().power) {
					world.setBlockState(current, world.getBlockState(current).with(BallPoolContentBlock.POWER, bpitbe.getPoolSettings().power));
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

	public Box getFullScanned() {
		return new Box(Vec3d.of(this.getScannedStart()), Vec3d.of(this.getScannedEnd())).stretch(1, 1, 1);
	}

	public BlockPos getRelativeScannedStart(BlockPos pos) {
		return pos.add(this.getScannedStart());
	}

	public BlockPos getRelativeScannedEnd(BlockPos pos) {
		return pos.add(this.getScannedEnd());
	}

	public Box getRelativeFullScanned(BlockPos pos) {
		return new Box(Vec3d.of(this.getRelativeScannedStart(pos)), Vec3d.of(this.getRelativeScannedEnd(pos))).stretch(1, 1, 1);
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
		BlockPos.iterate(this.getPos().add(previousStart), this.getPos().add(this.scannedEnd)).forEach(
			current -> {
				if (!this.getFullScanned().contains(Vec3d.of(current))) {
					deleter.accept(current);
				}
			}
		);
	}

	public void setScannedEnd(BlockPos pos, Consumer<BlockPos> deleter) {
		BlockPos previousEnd = this.scannedEnd;
		this.scannedEnd = pos;
		BlockPos.iterate(this.getPos().add(this.scannedStart), this.getPos().add(previousEnd)).forEach(
			current -> {
				if (!this.getFullScanned().contains(Vec3d.of(current))) {
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

		public void fromNbt(NbtCompound nbt) {
			this.power = nbt.getInt("power");
		}

		public NbtCompound toNbt() {
			NbtCompound nbt = new NbtCompound();
			nbt.putInt("power", this.power);
			return nbt;
		}
	}

	public enum SelectionMode implements StringIdentifiable {

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
		public String asString() {
			return this.identifier;
		}
	}
}
