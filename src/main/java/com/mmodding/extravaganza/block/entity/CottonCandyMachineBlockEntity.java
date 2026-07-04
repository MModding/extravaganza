package com.mmodding.extravaganza.block.entity;

import com.mmodding.extravaganza.init.ExtravaganzaBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class CottonCandyMachineBlockEntity extends BlockEntity {

	private int sugarAmount = 0;

	public CottonCandyMachineBlockEntity(BlockPos pos, BlockState state) {
		super(ExtravaganzaBlockEntities.COTTON_CANDY_MACHINE, pos, state);
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		this.sugarAmount = input.getIntOr("sugar_amount", 0);
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		output.putInt("sugar_amount", this.sugarAmount);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return this.saveCustomOnly(registries);
	}

	public int getSugarAmount() {
		return this.sugarAmount;
	}

	public boolean canIncreaseSugarAmount() {
		return this.sugarAmount < 256;
	}

	public void increaseSugarAmount(int sugarAmount) {
		this.sugarAmount = Mth.clamp(this.sugarAmount + sugarAmount, 0, 256);
	}

	public boolean canDecreaseSugarAmount() {
		return this.sugarAmount >= 4;
	}

	public void decreaseSugarAmount(int sugarAmount) {
		this.sugarAmount = Mth.clamp(this.sugarAmount - sugarAmount, 0, 256);
	}

	public int clampCottonCandyCount(int cottonCandyCount) {
		return Math.min(cottonCandyCount, Math.floorDiv(this.sugarAmount, 4));
	}
}
