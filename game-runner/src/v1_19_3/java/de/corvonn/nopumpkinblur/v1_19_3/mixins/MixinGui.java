package de.corvonn.nopumpkinblur.v1_19_3.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import de.corvonn.nopumpkinblur.NoPumpkinBlur;
import de.corvonn.nopumpkinblur.NoPumpkinBlurConfig;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
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
    private void mixinRenderTextureOverlay(ResourceLocation resourceLocation, float opacity, CallbackInfo ci) {
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
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, opacity);
            RenderSystem.setShaderTexture(0, resourceLocation);
            Tesselator $$2 = Tesselator.getInstance();
            BufferBuilder $$3 = $$2.getBuilder();
            $$3.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            $$3.vertex(0.0, (double)this.screenHeight, -90.0).uv(0.0F, 1.0F).endVertex();
            $$3.vertex((double)this.screenWidth, (double)this.screenHeight, -90.0).uv(1.0F, 1.0F).endVertex();
            $$3.vertex((double)this.screenWidth, 0.0, -90.0).uv(1.0F, 0.0F).endVertex();
            $$3.vertex(0.0, 0.0, -90.0).uv(0.0F, 0.0F).endVertex();
            $$2.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
