package com.mmodding.extravaganza.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;

public class BatItem extends Item {

	public BatItem(Settings settings) {
		super(settings);
	}

	@Override
	public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
		return 2.5f;
	}
}
