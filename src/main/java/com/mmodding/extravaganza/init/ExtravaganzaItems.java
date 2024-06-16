package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.item.FestiveBallItem;
import com.mmodding.extravaganza.item.WrenchAganzaItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import java.util.stream.Stream;

public class ExtravaganzaItems {

	public static final Item WRENCH_AGANZA = new WrenchAganzaItem(new Item.Settings().maxCount(1));

	public static final Item RUBBER = new Item(new Item.Settings());

	public static final Item COMMON_FESTIVE_COIN = new Item(new Item.Settings().maxCount(96));
	public static final Item UNCOMMON_FESTIVE_COIN = new Item(new Item.Settings().maxCount(96));
	public static final Item GOLDEN_FESTIVE_COIN = new Item(new Item.Settings().maxCount(96));

	public static final Item GOLDEN_CANDY_CANE = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(2).saturationModifier(1.5f).alwaysEdible().snack().build()));
	public static final Item GREEN_CANDY_CANE = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(2).saturationModifier(1.5f).alwaysEdible().snack().build()));
	public static final Item RED_CANDY_CANE = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(2).saturationModifier(1.5f).alwaysEdible().snack().build()));

	public static final Item HOT_DOG = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(4).saturationModifier(1.0f).snack().build()));
	public static final Item HOT_DOG_WITH_MAYONNAISE = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(4).saturationModifier(1.5f).snack().build()));

	public static final Item BAT = new Item(new Item.Settings().maxCount(1));

	public static void register() {
		Registry.register(Registries.ITEM, Extravaganza.createId("wrench_aganza"), ExtravaganzaItems.WRENCH_AGANZA);
		Registry.register(Registries.ITEM, Extravaganza.createId("rubber"), ExtravaganzaItems.RUBBER);
		Registry.register(Registries.ITEM, Extravaganza.createId("common_festive_coin"), ExtravaganzaItems.COMMON_FESTIVE_COIN);
		Registry.register(Registries.ITEM, Extravaganza.createId("uncommon_festive_coin"), ExtravaganzaItems.UNCOMMON_FESTIVE_COIN);
		Registry.register(Registries.ITEM, Extravaganza.createId("golden_festive_coin"), ExtravaganzaItems.GOLDEN_FESTIVE_COIN);
		ExtravaganzaColor.VALUES.forEach(color -> Registry.register(
			Registries.ITEM,
			Extravaganza.createId(color.asString() + "_festive_ball"),
			new FestiveBallItem(color, new Item.Settings().maxCount(16))
		));
		Registry.register(Registries.ITEM, Extravaganza.createId("golden_candy_cane"), ExtravaganzaItems.GOLDEN_CANDY_CANE);
		Registry.register(Registries.ITEM, Extravaganza.createId("green_candy_cane"), ExtravaganzaItems.GREEN_CANDY_CANE);
		Registry.register(Registries.ITEM, Extravaganza.createId("red_candy_cane"), ExtravaganzaItems.RED_CANDY_CANE);
		Registry.register(Registries.ITEM, Extravaganza.createId("hot_dog"), ExtravaganzaItems.HOT_DOG);
		Registry.register(Registries.ITEM, Extravaganza.createId("hot_dog_with_mayonnaise"), ExtravaganzaItems.HOT_DOG_WITH_MAYONNAISE);
		Registry.register(Registries.ITEM, Extravaganza.createId("bat"), ExtravaganzaItems.BAT);

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
