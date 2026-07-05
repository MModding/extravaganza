package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ExtravaganzaItemTags {

	public static final TagKey<Item> CANDY_CANES = TagKey.create(Registries.ITEM, Extravaganza.createId("candy_canes"));
	public static final TagKey<Item> FESTIVE_BALLS = TagKey.create(Registries.ITEM, Extravaganza.createId("festive_balls"));
}
