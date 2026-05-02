package com.duox.sdautofish;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class AutoFishMod implements ClientModInitializer {

    public static boolean enabled = false;
    public static float fishingProgressTreasure = 1.0f;
    public static boolean comboUsed = false;

    public static KeyBinding TOGGLE_KEY;

    @Override
    public void onInitializeClient() {
        TOGGLE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.autofish.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                "key.categories.autofish"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (TOGGLE_KEY.wasPressed()) {
                enabled = !enabled;
                if (client.player != null) {
                    client.player.sendMessage(
                            Text.literal("Auto Fish: " + (enabled ? "ON" : "OFF")),
                            true
                    );
                }
            }
        });
    }
}
