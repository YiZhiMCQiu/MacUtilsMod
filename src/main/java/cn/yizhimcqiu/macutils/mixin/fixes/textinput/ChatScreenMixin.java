package cn.yizhimcqiu.macutils.mixin.fixes.textinput;

import cn.yizhimcqiu.macutils.system.ChatInputFix;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @Redirect(method = "keyPressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ChatScreen;setChatFromHistory(I)V"))
    private void setChatFromHistory(ChatScreen instance, int offset) {
        if (!ChatInputFix.isInPinyin()) {
            instance.setChatFromHistory(offset);
        }
    }
}
