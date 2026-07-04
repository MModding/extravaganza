package com.mmodding.extravaganza.item;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;

public class BatItem extends Item {

	public BatItem(Properties properties) {
		super(properties);
	}

	@Override
	public float getAttackDamageBonus(Entity victim, float damage, DamageSource damageSource) {
		return 2.5f;
	}
}
