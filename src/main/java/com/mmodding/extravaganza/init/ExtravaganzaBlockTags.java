package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ExtravaganzaBlockTags {

	public static final TagKey<Block> RUBBER_SCRAPPER_MINEABLE = TagKey.create(Registries.BLOCK, Extravaganza.createId("mineable/rubber_scrapper"));

	public static final TagKey<Block> FESTIVE_RUBBERS = TagKey.create(Registries.BLOCK, Extravaganza.createId("festive_rubbers"));
	public static final TagKey<Block> FESTIVE_RUBBER_LADDERS = TagKey.create(Registries.BLOCK, Extravaganza.createId("festive_rubber_ladders"));
	public static final TagKey<Block> INK_PUDDLES = TagKey.create(Registries.BLOCK, Extravaganza.createId("ink_puddles"));
	public static final TagKey<Block> CONFETTI = TagKey.create(Registries.BLOCK, Extravaganza.createId("confetti"));
	public static final TagKey<Block> PAPER_LANTERNS = TagKey.create(Registries.BLOCK, Extravaganza.createId("paper_lanterns"));
	public static final TagKey<Block> TRASH_CANS = TagKey.create(Registries.BLOCK, Extravaganza.createId("trash_cans"));
}
