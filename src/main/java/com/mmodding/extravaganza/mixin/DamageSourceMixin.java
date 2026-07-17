package com.mmodding.extravaganza.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.extravaganza.init.ExtravaganzaDamageTypes;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.random.RandomGenerator;

@Debug(export = true)
@Mixin(DamageSource.class)
public class DamageSourceMixin {

	@Unique
	private static final IntSet CENSORED_VARIANTS = IntSet.of(0, 11, 12, 13);

	@Unique
	private int variant = -1;

	@Unique
	private boolean self;

	@Shadow
	@Final
	private @Nullable Entity causingEntity;

	@Inject(method = "<init>(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;)V", at = @At("TAIL"))
	private void setupVariant(Holder<DamageType> type, Entity directEntity, Entity causingEntity, Vec3 damageSourcePosition, CallbackInfo ci) {
		if (type.isBound()) {
			if (type.unwrapKey().orElseThrow() == ExtravaganzaDamageTypes.TRASH) {
				this.variant = RandomGenerator.getDefault().nextInt((directEntity == null || causingEntity == null) ? 15 : 2);
				this.self = directEntity == null || causingEntity == null;
			}
		}
	}

	@WrapMethod(method = "getLocalizedDeathMessage")
	private Component injectVariant(LivingEntity victim, Operation<Component> original) {
		if (this.variant != -1) {
			if (this.self) {
				if (Extravaganza.CONFIG.getContent().bool("censored_death_messages") || !CENSORED_VARIANTS.contains(this.variant)) {
					return Component.translatable("death.trash." + this.variant, victim.getDisplayName());
				}
				else {
					return Component.translatable("death.trash." + this.variant + ".uncensored", victim.getDisplayName());
				}
			}
			else {
				assert this.causingEntity != null;
				return Component.translatable("death.trash.player." + this.variant, victim.getDisplayName(), this.causingEntity.getDisplayName());
			}
		}
		else {
			return original.call(victim);
		}
	}
}
