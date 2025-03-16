package cn.yizhimcqiu.macutils.keyboard;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeyBinds {
    public static final KeyBinding UNLOCK_MOUSE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.mu.unlock_mouse",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_GRAVE_ACCENT,
            "category.mu"
    ));

    public static void initialize() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (UNLOCK_MOUSE_KEY.wasPressed()) {
                if (client.mouse.isCursorLocked()) {
                    client.mouse.unlockCursor();
                } else {
                    client.mouse.lockCursor();
                }
            }
        });
    }
}
