package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.item.FestiveBallItem;
import com.mmodding.extravaganza.item.WrenchAganzaItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import java.util.stream.Stream;

public class ExtravaganzaItems {

	public static final Item WRENCH_AGANZA = new WrenchAganzaItem(new Item.Settings().maxCount(1));

	public static final Item BLACK_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.BLACK_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item BLUE_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.BLUE_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item BROWN_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.BROWN_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item CYAN_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.CYAN_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item GRAY_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.GRAY_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item GREEN_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.GREEN_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item LIGHT_BLUE_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.LIGHT_BLUE_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item LIGHT_GRAY_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.LIGHT_GRAY_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item LIME_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.LIME_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item MAGENTA_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.MAGENTA_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item ORANGE_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.ORANGE_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item PINK_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.PINK_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item PURPLE_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.PURPLE_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item RED_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.RED_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item WHITE_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.WHITE_FESTIVE_BALL, new Item.Settings().maxCount(16));
	public static final Item YELLOW_FESTIVE_BALL = new FestiveBallItem(() -> ExtravaganzaEntities.YELLOW_FESTIVE_BALL, new Item.Settings().maxCount(16));

	public static void register() {
		Registry.register(Registries.ITEM, Extravaganza.createId("wrench_aganza"), ExtravaganzaItems.WRENCH_AGANZA);
		Registry.register(Registries.ITEM, Extravaganza.createId("black_festive_ball"), ExtravaganzaItems.BLACK_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("blue_festive_ball"), ExtravaganzaItems.BLUE_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("brown_festive_ball"), ExtravaganzaItems.BROWN_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("cyan_festive_ball"), ExtravaganzaItems.CYAN_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("gray_festive_ball"), ExtravaganzaItems.GRAY_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("green_festive_ball"), ExtravaganzaItems.GREEN_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("light_blue_festive_ball"), ExtravaganzaItems.LIGHT_BLUE_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("light_gray_festive_ball"), ExtravaganzaItems.LIGHT_GRAY_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("lime_festive_ball"), ExtravaganzaItems.LIME_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("magenta_festive_ball"), ExtravaganzaItems.MAGENTA_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("orange_festive_ball"), ExtravaganzaItems.ORANGE_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("pink_festive_ball"), ExtravaganzaItems.PINK_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("purple_festive_ball"), ExtravaganzaItems.PURPLE_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("red_festive_ball"), ExtravaganzaItems.RED_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("white_festive_ball"), ExtravaganzaItems.WHITE_FESTIVE_BALL);
		Registry.register(Registries.ITEM, Extravaganza.createId("yellow_festive_ball"), ExtravaganzaItems.YELLOW_FESTIVE_BALL);

		Stream<Item> stream = Extravaganza.extractFromRegistry(Registries.ITEM);

		Registry.register(
			Registries.ITEM_GROUP,
			Extravaganza.createId("main"),
			FabricItemGroup.builder()
				.displayName(Text.translatable("itemGroup.extravaganza.main"))
				.icon(ExtravaganzaItems.WRENCH_AGANZA::getDefaultStack)
				.entries((displayContext, entries) -> stream.forEach(entries::add))
				.build()
		);
	}
}
