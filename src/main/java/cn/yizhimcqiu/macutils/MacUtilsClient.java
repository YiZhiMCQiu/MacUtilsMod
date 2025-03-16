package cn.yizhimcqiu.macutils;

import cn.yizhimcqiu.macutils.constants.MacConstants;
import cn.yizhimcqiu.macutils.keyboard.ModKeyBinds;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MacUtilsClient implements ModInitializer, ClientModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
    @Override
    public void onInitializeClient() {
        ModKeyBinds.initialize();

        this.detectEnvironment();
    }

    @Override
    public void onInitialize() {

    }

    private void detectEnvironment() {
        if (!MinecraftClient.IS_SYSTEM_MAC) {
            LOGGER.warn("The current environment is not Mac!");
        } else {
            try {
                Process process = Runtime.getRuntime().exec("sysctl sysctl.proc_translated".split(" "));
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.contains("sysctl.proc_translated: 1")) {
                        MacConstants.IS_ROSETTA = true;
                        break;
                    }
                }

                reader.close();
            } catch (IOException e) {
                LOGGER.error("Failed to detect Rosetta!");
            }
        }
    }
}
