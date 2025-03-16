package cn.yizhimcqiu.macutils.mixin;

import cn.yizhimcqiu.macutils.constants.MacConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Redirect(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;hasControlDown()Z"))
    private boolean hasControlDown() {
        return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL)
                || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_CONTROL);
    }

    @Redirect(method = "onFinishedLoading", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;finishedLoading:Z", opcode = Opcodes.GETFIELD))
    private boolean onFinishedLoading(MinecraftClient instance) {
        if (MacConstants.IS_ROSETTA) {
            SystemToast.add(instance.getToastManager(), SystemToast.Type.WORLD_BACKUP, Text.translatable("toast.rosetta.title"), Text.translatable("toast.rosetta.desc"));
        }
        return instance.isFinishedLoading();
    }
}
