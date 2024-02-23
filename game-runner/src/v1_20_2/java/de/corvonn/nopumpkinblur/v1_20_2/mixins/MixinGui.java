package de.corvonn.nopumpkinblur.v1_20_2.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import de.corvonn.nopumpkinblur.NoPumpkinBlur;
import de.corvonn.nopumpkinblur.NoPumpkinBlurConfig;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {

    @Shadow
    @Final
    private static ResourceLocation PUMPKIN_BLUR_LOCATION;

    @Shadow
    private int screenWidth;

    @Shadow
    private int screenHeight;

    @Inject(method = "renderTextureOverlay", at = @At("HEAD"), cancellable = true)
    private void mixinRenderTextureOverlay(
        GuiGraphics guiGraphics, ResourceLocation resourceLocation, float opacity, CallbackInfo ci) {
        NoPumpkinBlur instance = NoPumpkinBlur.getInstance();
        if(instance == null) return; //Necessary?
        NoPumpkinBlurConfig config = instance.configuration();
        if(!config.enabled().get()) return;

        if(resourceLocation == PUMPKIN_BLUR_LOCATION) {
            opacity = config.opacity().get();

            ci.cancel();

            if(opacity == 0) return;

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, opacity);
            guiGraphics.blit(resourceLocation, 0, 0, -90, 0.0F, 0.0F, this.screenWidth, this.screenHeight, this.screenWidth, this.screenHeight);
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
