package com.duox.sdautofish.mixin;

import com.duox.sdautofish.AutoFishMod;
import com.kltyton.stardewfishingFabric.client.FishingMinigame;
import com.kltyton.stardewfishingFabric.client.FishingScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingScreen.class)
public class FishingAutoMixin {

    @Shadow private boolean mouseDown;
    @Shadow private FishingMinigame minigame;

    /**
     * HIT_HEIGHT = 29 (the height of the catch bar).
     * bobberPos is the TOP of the bar; fishPos is the fish target center.
     * Hold mouse to push bar up, release to let gravity pull it down.
     */
    private static final float BAR_HALF_HEIGHT = 29f / 2f; // 14.5f

    @Inject(method = "tick", at = @At("HEAD"), remap = true)
    private void autoFishTick(CallbackInfo ci) {
        if (!AutoFishMod.enabled || minigame == null) return;

        float barCenter = minigame.getBobberPos() + BAR_HALF_HEIGHT;
        float fishCenter = minigame.getFishPos();

        // Press if fish is above bar center (fish < barCenter), release if below
        mouseDown = fishCenter < barCenter;
    }
}
