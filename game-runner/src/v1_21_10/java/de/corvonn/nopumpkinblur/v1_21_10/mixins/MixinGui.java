package de.corvonn.nopumpkinblur.v1_21_10.mixins;

import de.corvonn.nopumpkinblur.NoPumpkinBlur;
import de.corvonn.nopumpkinblur.NoPumpkinBlurConfig;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {

    @Unique
    @Final
    private static final ResourceLocation PUMPKIN_BLUR_LOCATION = ResourceLocation.withDefaultNamespace("textures/misc/pumpkinblur.png");

    @Inject(method = "renderTextureOverlay", at = @At("HEAD"), cancellable = true)
    private void mixinRenderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation resourceLocation, float opacity, CallbackInfo ci) {
        if (!resourceLocation.equals(PUMPKIN_BLUR_LOCATION)) {
            return;
        }

        NoPumpkinBlur instance = NoPumpkinBlur.getInstance();
        if (instance == null) {
            return;
        }

        NoPumpkinBlurConfig config = instance.configuration();
        if (!config.enabled().get()) {
            return;
        }

        opacity = config.opacity().get();

        ci.cancel();

        if (opacity == 0) {
            return;
        }

        guiGraphics.blit(
            RenderPipelines.GUI_TEXTURED,
            resourceLocation,
            0,
            0,
            0.0F,
            0.0F,
            guiGraphics.guiWidth(),
            guiGraphics.guiHeight(),
            guiGraphics.guiWidth(),
            guiGraphics.guiHeight(),
            ARGB.white(opacity)
        );
    }
}
