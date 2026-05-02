package com.duox.sdautofish.mixin;

import com.kltyton.stardewfishingFabric.common.FishBehavior;
import com.kltyton.stardewfishingFabric.server.FishBehaviorReloadListener;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(FishBehaviorReloadListener.class)
public class MinigameCrashFixMixin {

    @Shadow
    public static FishBehaviorReloadListener INSTANCE;

    @Inject(
            method = "getBehavior",
            at = @At("HEAD"),
            cancellable = true,
            require = 1,
            remap = false
    )
    private static void onGetBehavior(ItemStack stack, CallbackInfoReturnable<Optional<FishBehavior>> cir) {
        if (INSTANCE == null) {
            cir.setReturnValue(Optional.empty());
        }
    }
}
