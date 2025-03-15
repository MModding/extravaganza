package com.mmodding.extravaganza.block.entity;

import com.mmodding.extravaganza.init.ExtravaganzaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class CottonCandyMachineBlockEntity extends BlockEntity {

	private int sugarAmount = 0;

	public CottonCandyMachineBlockEntity(BlockPos pos, BlockState state) {
		super(ExtravaganzaBlockEntities.COTTON_CANDY_MACHINE, pos, state);
	}

	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		this.sugarAmount = nbt.getInt("sugar_amount");
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		nbt.putInt("sugar_amount", this.sugarAmount);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		return this.createComponentlessNbt(registryLookup);
	}

	public int getSugarAmount() {
		return this.sugarAmount;
	}

	public boolean canIncreaseSugarAmount() {
		return this.sugarAmount < 256;
	}

	public void increaseSugarAmount(int sugarAmount) {
		this.sugarAmount = MathHelper.clamp(this.sugarAmount + sugarAmount, 0, 256);
	}

	public boolean canDecreaseSugarAmount() {
		return this.sugarAmount >= 4;
	}

	public void decreaseSugarAmount(int sugarAmount) {
		this.sugarAmount = MathHelper.clamp(this.sugarAmount - sugarAmount, 0, 256);
	}

	public int clampCottonCandyCount(int cottonCandyCount) {
		return Math.min(cottonCandyCount, Math.floorDiv(this.sugarAmount, 4));
	}
}
