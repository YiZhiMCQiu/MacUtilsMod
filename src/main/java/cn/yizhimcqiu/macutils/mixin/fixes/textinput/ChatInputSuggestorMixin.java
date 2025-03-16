package cn.yizhimcqiu.macutils.mixin.fixes.textinput;

import cn.yizhimcqiu.macutils.system.ChatInputFix;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatInputSuggestor.class)
public class ChatInputSuggestorMixin {
    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void scroll(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (ChatInputFix.isInPinyin()) cir.setReturnValue(false);
    }
}