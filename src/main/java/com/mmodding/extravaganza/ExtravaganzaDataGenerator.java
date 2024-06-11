package com.mmodding.extravaganza;

import com.mmodding.extravaganza.init.ExtravaganzaBlocks;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.*;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class ExtravaganzaDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ExtravaganzaLanguageProvider::new);
		pack.addProvider(ExtravaganzaModelProvider::new);
	}

	public static class ExtravaganzaLanguageProvider extends FabricLanguageProvider {

		private ExtravaganzaLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
			translationBuilder.add("itemGroup.extravaganza.main", "Extravaganza!");
			Extravaganza.executeKeyForRegistry(Registries.ITEM, key -> translationBuilder.add(Registries.ITEM.get(key), this.makeItReadable(key)));
		}

		private <T> String makeItReadable(RegistryKey<T> key) {
			String path = key.getValue().getPath();
			String[] words = path.split("_");
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < words.length; i++) {
				String word = words[i];
				builder.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
				if (i != words.length - 1) {
					builder.append(" ");
				}
			}
			builder.delete(builder.toString().length() - 1, builder.toString().length() - 1);
			return builder.toString();
		}
	}

	public static class ExtravaganzaModelProvider extends FabricModelProvider {

		private static final Predicate<Block> UNCOMMON = block ->
			block instanceof TransparentBlock ||
			block instanceof LadderBlock ||
			block.equals(ExtravaganzaBlocks.BALL_POOL_CORE) ||
			block.equals(ExtravaganzaBlocks.BALL_POOL_CONTENT) ||
			block.equals(ExtravaganzaBlocks.PINATA);

		private ExtravaganzaModelProvider(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
			Extravaganza.executeForRegistry(Registries.BLOCK, block -> {
				if (!ExtravaganzaModelProvider.UNCOMMON.test(block)) {
					if (!(block instanceof StairsBlock) && !(block instanceof SlabBlock) && !(block instanceof WallBlock)) {
						BlockStateModelGenerator.BlockTexturePool pool = blockStateModelGenerator.registerCubeAllModelTexturePool(block);
						Identifier identifier = Registries.BLOCK.getId(block);
						Extravaganza.getLogger().info(identifier.toString());
						pool.stairs(Registries.BLOCK.get(identifier.withPath(string -> string + "_stairs")));
						pool.slab(Registries.BLOCK.get(identifier.withPath(string -> string + "_slab")));
						pool.wall(Registries.BLOCK.get(identifier.withPath(string -> string + "_wall")));
					}
				}
				else if (block instanceof TransparentBlock) {
					blockStateModelGenerator.registerSimpleCubeAll(block);
				}
				else if (block instanceof LadderBlock) {
					blockStateModelGenerator.registerNorthDefaultHorizontalRotation(block);
					blockStateModelGenerator.registerItemModel(block);
				}
				else if (block.equals(ExtravaganzaBlocks.BALL_POOL_CONTENT) || block.equals(ExtravaganzaBlocks.PINATA)) {
					blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
				}
			});
		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
			Extravaganza.executeForRegistry(Registries.ITEM, item -> {
				if (!(item instanceof BlockItem)) {
					itemModelGenerator.register(item, Models.GENERATED);
				}
			});
		}
	}
}
