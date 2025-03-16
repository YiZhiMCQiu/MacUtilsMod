package cn.yizhimcqiu.macutils.system;

import net.minecraft.client.MinecraftClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Fixed the problem that Minecraft switches the current line when switching the line of the Chinese input method suggestion table.
 * @see cn.yizhimcqiu.macutils.mixin.fixes.textinput.ChatScreenMixin
 * @see cn.yizhimcqiu.macutils.mixin.fixes.textinput.ChatInputSuggestorMixin
 */
public class ChatInputFix {
    public static boolean isInPinyin() {
        if (!MinecraftClient.IS_SYSTEM_MAC) return false;
        try {
            Process process = Runtime.getRuntime().exec(new String[] {"/bin/bash", "-c", "defaults read ~/Library/Preferences/com.apple.HIToolbox.plist AppleSelectedInputSources"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean isChineseInputMethod = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("\"Bundle ID\" = \"com.apple.inputmethod.SCIM\";")) {
                    isChineseInputMethod = true;
                    break;
                }
            }
            reader.close();
            return isChineseInputMethod;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
