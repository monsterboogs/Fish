package com.duox.sdautofish.mixin;

import com.duox.sdautofish.AutoFishMod;
import com.kltyton.stardewfishingFabric.client.FishingMinigame;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Smooths bobber movement when auto-fish is active.
 * MAX_BOBBER_HEIGHT = 106, bar height (HIT_HEIGHT) = 29.
 * bobberPos is the bar's top edge in [0, MAX_BOBBER_HEIGHT - HIT_HEIGHT] = [0, 77].
 * fishPos is the fish center in [0, MAX_BOBBER_HEIGHT] = [0, 106].
 */
@Mixin(FishingMinigame.class)
public class FishingMinigameMixin {

    @Shadow private float bobberPos;
    @Shadow private float bobberVelocity;
    @Shadow private float fishPos;

    private static final float BAR_HEIGHT       = 29f;
    private static final float MAX_BOBBER_HEIGHT = 106f;

    @Inject(method = "tick(Z)V", at = @At("HEAD"), remap = false)
    private void snapBobberToFish(boolean mouseDown, CallbackInfo ci) {
        if (!AutoFishMod.enabled) return;

        // Snap the bar smoothly toward the fish target
        float targetTop = MathHelper.clamp(
                fishPos - BAR_HEIGHT / 2f,
                0f,
                MAX_BOBBER_HEIGHT - BAR_HEIGHT
        );

        bobberPos = bobberPos + (targetTop - bobberPos) * 0.3f;
        bobberVelocity = 0f;
    }
}
