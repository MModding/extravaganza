package com.mmodding.extravaganza.client.color.item;

import com.mmodding.extravaganza.ExtravaganzaColor;
import com.mmodding.extravaganza.client.init.ExtravaganzaColorProviders;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record ExtravaganzaColorSource(ExtravaganzaColor color) implements ItemTintSource {

	public static final MapCodec<ExtravaganzaColorSource> CODEC = RecordCodecBuilder.mapCodec(
		i -> i.group(ExtravaganzaColor.CODEC.fieldOf("default").forGetter(ExtravaganzaColorSource::color))
			.apply(i, ExtravaganzaColorSource::new)
	);

	@Override
	public int calculate(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
		return ExtravaganzaColorProviders.COLORS.get(this.color.getSerializedName()).toDecimal();
	}

	@Override
	public MapCodec<ExtravaganzaColorSource> type() {
		return CODEC;
	}
}
