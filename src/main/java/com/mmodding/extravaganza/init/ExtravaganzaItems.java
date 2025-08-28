package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.item.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ExtravaganzaItems {

	public static final Item WRENCH_AGANZA = new WrenchAganzaItem(new Item.Settings().maxCount(1));

	public static final Item RUBBER_EXTRACTOR = new RubberExtractorItem(new Item.Settings().maxCount(1).maxDamage(128));

	public static final Item RUBBER_SCRAPER = new RubberScraperItem(new Item.Settings().maxDamage(238).component(DataComponentTypes.TOOL, RubberScraperItem.createToolComponent()));

	public static final Item RUBBER = new Item(new Item.Settings());

	public static final Item TEAR_DYE = new Item(new Item.Settings());
	public static final Item PLANT_DYE = new Item(new Item.Settings());
	public static final Item NYMPH_DYE = new Item(new Item.Settings());
	public static final Item TOMATO_DYE = new Item(new Item.Settings());

	public static final Item COMMON_FESTIVE_COIN = new Item(new Item.Settings().maxCount(96));
	public static final Item UNCOMMON_FESTIVE_COIN = new Item(new Item.Settings().maxCount(96));
	public static final Item GOLDEN_FESTIVE_COIN = new Item(new Item.Settings().maxCount(96));

	public static final Item GOLDEN_CANDY_CANE = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(2).saturationModifier(1.5f).alwaysEdible().snack().build()));
	public static final Item GREEN_CANDY_CANE = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(2).saturationModifier(1.5f).alwaysEdible().snack().build()));
	public static final Item RED_CANDY_CANE = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(2).saturationModifier(1.5f).alwaysEdible().snack().build()));

	public static final Item HOT_DOG = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(4).saturationModifier(1.0f).snack().build()));
	public static final Item HOT_DOG_WITH_MAYONNAISE = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(4).saturationModifier(1.5f).snack().build()));

	public static final Item CHEESEBURGER = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(2.5f).build()));
	public static final Item BEESECHURGER = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(2.5f).build()));

	public static final Item WAY_TO_SUGARY_WHITECAKE = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(20).saturationModifier(10.0f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 20 * 15, 0, true, false, true), 1.0f).build()));

	public static final Item COTTON_CANDY = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.75f).build()));

	public static final Item EMPTY_POPCORN = new EmptyPopcornItem(new Item.Settings().maxCount(16));
	public static final Item POPCORN = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.5f).alwaysEdible().usingConvertsTo(ExtravaganzaItems.EMPTY_POPCORN).build()).maxCount(1));

	public static final Item CHERRY_BALLOON = new HeliumBalloonItem("cherry", new Item.Settings().maxCount(16));
	public static final Item CREEPER_BALLOON = new HeliumBalloonItem("creeper", new Item.Settings().maxCount(16));

	public static final Item BAT = new BatItem(new Item.Settings().maxCount(1));

	public static final Item MERRY_GO_ROUND = new MerryGoRoundItem(new Item.Settings().maxCount(1));

	public static void register() {
		Registry.register(Registries.ITEM, Extravaganza.createId("wrench_aganza"), ExtravaganzaItems.WRENCH_AGANZA);
		Registry.register(Registries.ITEM, Extravaganza.createId("rubber_extractor"), ExtravaganzaItems.RUBBER_EXTRACTOR);
		Registry.register(Registries.ITEM, Extravaganza.createId("rubber_scraper"), ExtravaganzaItems.RUBBER_SCRAPER);
		Registry.register(Registries.ITEM, Extravaganza.createId("rubber"), ExtravaganzaItems.RUBBER);
		Registry.register(Registries.ITEM, Extravaganza.createId("tear_dye"), ExtravaganzaItems.TEAR_DYE);
		Registry.register(Registries.ITEM, Extravaganza.createId("plant_dye"), ExtravaganzaItems.PLANT_DYE);
		Registry.register(Registries.ITEM, Extravaganza.createId("nymph_dye"), ExtravaganzaItems.NYMPH_DYE);
		Registry.register(Registries.ITEM, Extravaganza.createId("tomato_dye"), ExtravaganzaItems.TOMATO_DYE);
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
		Registry.register(Registries.ITEM, Extravaganza.createId("cheeseburger"), ExtravaganzaItems.CHEESEBURGER);
		Registry.register(Registries.ITEM, Extravaganza.createId("beesechurger"), ExtravaganzaItems.BEESECHURGER);
		Registry.register(Registries.ITEM, Extravaganza.createId("way_too_sugary_whitecake"), ExtravaganzaItems.WAY_TO_SUGARY_WHITECAKE);
		Registry.register(Registries.ITEM, Extravaganza.createId("cotton_candy"), ExtravaganzaItems.COTTON_CANDY);
		Registry.register(Registries.ITEM, Extravaganza.createId("empty_popcorn"), ExtravaganzaItems.EMPTY_POPCORN);
		Registry.register(Registries.ITEM, Extravaganza.createId("popcorn"), ExtravaganzaItems.POPCORN);
		Registry.register(Registries.ITEM, Extravaganza.createId("cherry_balloon"), ExtravaganzaItems.CHERRY_BALLOON);
		Registry.register(Registries.ITEM, Extravaganza.createId("creeper_balloon"), ExtravaganzaItems.CREEPER_BALLOON);
		Registry.register(Registries.ITEM, Extravaganza.createId("bat"), ExtravaganzaItems.BAT);
		Registry.register(Registries.ITEM, Extravaganza.createId("merry_go_round"), ExtravaganzaItems.MERRY_GO_ROUND);

		Registry.register(
			Registries.ITEM_GROUP,
			Extravaganza.createId("main"),
			FabricItemGroup.builder()
				.displayName(Text.translatable("itemGroup.extravaganza.main"))
				.icon(ExtravaganzaItems.WRENCH_AGANZA::getDefaultStack)
				.entries((displayContext, entries) -> Extravaganza.extractFromRegistry(Registries.ITEM).forEach(entries::add))
				.build()
		);
	}
}
