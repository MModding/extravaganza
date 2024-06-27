package com.mmodding.extravaganza.mixin;

import com.mmodding.extravaganza.init.ExtravaganzaDamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(DamageSource.class)
public class DamageSourceMixin {

	@Unique
	private int variant = -1;

	@Unique
	private boolean self;

	@Shadow
	@Final
	@Nullable
	private Entity source;

	@Inject(method = "<init>(Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;)V", at = @At("TAIL"))
	private void setupVariant(RegistryEntry<DamageType> type, Entity source, Entity attacker, Vec3d position, CallbackInfo ci) {
		if (type.getKey().isPresent()) {
			if (type.getKey().orElseThrow() == ExtravaganzaDamageTypes.TRASH) {
				this.variant = Random.create().nextInt((source == null || attacker == null) ? 15 : 2);
				this.self = source == null || attacker == null;
			}
		}
	}

	@Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
	private void injectVariant(LivingEntity killed, CallbackInfoReturnable<Text> cir) {
		if (this.variant != -1) {
			if (this.self) {
				cir.setReturnValue(Text.translatable("death.trash." + this.variant, killed.getDisplayName()));
			}
			else {
				assert this.source != null;
				cir.setReturnValue(Text.translatable("death.trash.player." + this.variant, killed.getDisplayName(), this.source.getDisplayName()));
			}
		}
	}
}
