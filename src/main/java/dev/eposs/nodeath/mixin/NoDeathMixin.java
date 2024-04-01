package dev.eposs.nodeath.mixin;

import dev.eposs.nodeath.Nodeath;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class NoDeathMixin {
    @Unique
    LivingEntity entity = (LivingEntity) (Object) this;

    @Inject(
            method = "damage",
            at = @At("HEAD"),
            cancellable = true
    )
    private void dontDie(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!entity.isPlayer()) return;

        if (!Nodeath.playerList.contains(entity.getUuid())) return;

        float remainingHealth = entity.getHealth() - amount;
        if (remainingHealth < 0.5) {

            boolean result = entity.damage(source, 0.0f);
            cir.setReturnValue(result);
        }
    }
}
