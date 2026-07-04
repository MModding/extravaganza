package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.item.*;
import com.mmodding.library.core.api.AdvancedContainer;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.function.Function;

public class ExtravaganzaItems {

	public static final Item WRENCH_AGANZA = registerItem("wrench_aganza", WrenchAganzaItem::new, new Item.Properties().stacksTo(1));

	public static final Item RUBBER_EXTRACTOR = registerItem("rubber_extractor", RubberExtractorItem::new, new Item.Properties().stacksTo(1).durability(128));

	public static final Item RUBBER_SCRAPER = new RubberScraperItem(new Item.Properties().durability(238).component(DataComponents.TOOL, RubberScraperItem.createToolComponent()));

	public static final Item RUBBER = registerItem("rubber", new Item.Properties());

	public static final Item TEAR_DYE = registerItem("tear_dye", new Item.Properties());
	public static final Item PLANT_DYE = registerItem("plant_dye", new Item.Properties());
	public static final Item NYMPH_DYE = registerItem("nymph_dye", new Item.Properties());
	public static final Item TOMATO_DYE = registerItem("tomato_dye", new Item.Properties());

	public static final Item COMMON_FESTIVE_COIN = registerItem("common_festive_coin", new Item.Properties().stacksTo(96));
	public static final Item UNCOMMON_FESTIVE_COIN = registerItem("uncommon_festive_coin", new Item.Properties().stacksTo(96));
	public static final Item GOLDEN_FESTIVE_COIN = registerItem("golden_festive_coin", new Item.Properties().stacksTo(96));

	public static final Item GOLDEN_CANDY_CANE = registerItem("golden_candy_cane", new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(1.5f).alwaysEdible().build(), Consumable.builder().consumeSeconds(0.8f).build()));
	public static final Item GREEN_CANDY_CANE = registerItem("green_candy_cane", new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(1.5f).alwaysEdible().build(), Consumable.builder().consumeSeconds(0.8f).build()));
	public static final Item RED_CANDY_CANE = registerItem("red_candy_cane", new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(1.5f).alwaysEdible().build(), Consumable.builder().consumeSeconds(0.8f).build()));

	public static final Item HOT_DOG = registerItem("hot_dog", new Item.Properties().food(new FoodProperties(4, 1.0f, false), Consumable.builder().consumeSeconds(0.2f).build()));
	public static final Item HOT_DOG_WITH_MAYONNAISE = registerItem("hot_dog_with_mayonnaise", new Item.Properties().food(new FoodProperties(4, 1.5f, false), Consumable.builder().consumeSeconds(0.2f).build()));

	public static final Item CHEESEBURGER = registerItem("cheeseburger", new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(2.5f).build()));
	public static final Item BEESECHURGER = registerItem("beesechurger", new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(2.5f).build()));

	public static final Item WAY_TO_SUGARY_WHITECAKE = registerItem("way_to_sugary_whitecake", new Item.Properties().food(new FoodProperties.Builder().nutrition(20).saturationModifier(10.0f).build(), Consumable.builder().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 20 * 15, 0, true, false, true))).build()));

	public static final Item COTTON_CANDY = registerItem("cotton_candy", new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.75f).build()));

	public static final Item EMPTY_POPCORN = registerItem("empty_popcorn", EmptyPopcornItem::new, new Item.Properties().stacksTo(16));
	public static final Item POPCORN = registerItem("popcorn", new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.5f).alwaysEdible().build()).usingConvertsTo(ExtravaganzaItems.EMPTY_POPCORN).stacksTo(1));

	public static final Item CHERRY_BALLOON = registerItem("cherry_balloon", p -> new HeliumBalloonItem("cherry", p), new Item.Properties().stacksTo(16));
	public static final Item CREEPER_BALLOON = registerItem("creeper_ballon", p -> new HeliumBalloonItem("creeper", p), new Item.Properties().stacksTo(16));

	public static final Item BAT = registerItem("bat", BatItem::new, new Item.Properties().stacksTo(1));

	public static final Item MERRY_GO_ROUND = registerItem("merry_go_round", MerryGoRoundItem::new, new Item.Properties().stacksTo(1));

	private static Item registerItem(String path, Item.Properties properties) {
		return registerItem(path, Item::new, properties);
	}

	private static Item registerItem(String path, Function<Item.Properties, Item> factory, Item.Properties properties) {
		return Items.registerItem(Extravaganza.createKey(Registries.ITEM, path), factory, properties);
	}

	public static void register(AdvancedContainer mod) {
		mod.register(
			BuiltInRegistries.CREATIVE_MODE_TAB,
			"main",
			FabricCreativeModeTab.builder()
				.title(Component.translatable("itemGroup.extravaganza.main"))
				.icon(ExtravaganzaItems.WRENCH_AGANZA::getDefaultInstance)
				.displayItems((_, entries) -> mod.streamRegistryValues(BuiltInRegistries.ITEM).forEach(entries::accept))
				.build()
		);
	}

	static {
		ExtravaganzaColor.VALUES.forEach(color -> registerItem(
			color.getSerializedName() + "_festive_ball",
			p -> new FestiveBallItem(color, p),
			new Item.Properties().stacksTo(16)
		));
	}
}
