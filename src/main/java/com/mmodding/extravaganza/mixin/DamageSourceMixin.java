package com.mmodding.extravaganza.mixin;

import com.mmodding.extravaganza.init.ExtravaganzaDamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.random.RandomGenerator;

@Debug(export = true)
@Mixin(DamageSource.class)
public class DamageSourceMixin {

	@Unique
	private int variant = -1;

	@Unique
	private boolean self;

	@Shadow
	@Final
	private @Nullable Entity causingEntity;

	@Inject(method = "<init>(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;)V", at = @At("TAIL"))
	private void setupVariant(Holder<DamageType> type, Entity directEntity, Entity causingEntity, CallbackInfo ci) {
		if (type.isBound()) {
			if (type.unwrapKey().orElseThrow() == ExtravaganzaDamageTypes.TRASH) {
				this.variant = RandomGenerator.getDefault().nextInt((directEntity == null || causingEntity == null) ? 15 : 2);
				this.self = directEntity == null || causingEntity == null;
			}
		}
	}

	@Inject(method = "getLocalizedDeathMessage", at = @At("HEAD"), cancellable = true)
	private void injectVariant(LivingEntity victim, CallbackInfoReturnable<Component> cir) {
		if (this.variant != -1) {
			if (this.self) {
				cir.setReturnValue(Component.translatable("death.trash." + this.variant, victim.getDisplayName()));
			}
			else {
				assert this.causingEntity != null;
				cir.setReturnValue(Component.translatable("death.trash.player." + this.variant, victim.getDisplayName(), this.causingEntity.getDisplayName()));
			}
		}
	}
}
