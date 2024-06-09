package com.mmodding.extravaganza.block.entity;

import com.mmodding.extravaganza.init.ExtravaganzaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class BallPoolCoreBlockEntity extends BlockEntity {

	private final PoolSettings poolSettings = new PoolSettings();

	private Box scannedPoolContent = new Box(Vec3d.ZERO, Vec3d.ZERO);

	public BallPoolCoreBlockEntity(BlockPos pos, BlockState state) {
		super(ExtravaganzaBlockEntities.BALL_POOL_CORE, pos, state);
	}

	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		NbtCompound scannedPoolContent = nbt.getCompound("scanned_pool_content");
		NbtCompound startPos = scannedPoolContent.getCompound("start_pos");
		NbtCompound endPos = scannedPoolContent.getCompound("end_pos");
		this.scannedPoolContent = new Box(
			new Vec3d(
				startPos.getInt("x"),
				startPos.getInt("y"),
				startPos.getInt("z")
			),
			new Vec3d(
				endPos.getInt("x"),
				endPos.getInt("y"),
				endPos.getInt("z")
			)
		);
		this.poolSettings.fromNbt(nbt.getCompound("pool_settings"));
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		NbtCompound startPos = new NbtCompound();
		startPos.putInt("x", (int) this.scannedPoolContent.getMinPos().getX());
		startPos.putInt("y", (int) this.scannedPoolContent.getMinPos().getY());
		startPos.putInt("z", (int) this.scannedPoolContent.getMinPos().getZ());
		NbtCompound endPos = new NbtCompound();
		endPos.putInt("x", (int) this.scannedPoolContent.getMaxPos().getX());
		endPos.putInt("y", (int) this.scannedPoolContent.getMaxPos().getY());
		endPos.putInt("z", (int) this.scannedPoolContent.getMaxPos().getZ());
		NbtCompound scannedPoolContent = new NbtCompound();
		scannedPoolContent.put("start_pos", startPos);
		scannedPoolContent.put("end_pos", startPos);
		nbt.put("scanned_pool_content", scannedPoolContent);
		nbt.put("pool_settings", this.poolSettings.toNbt());
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
}
