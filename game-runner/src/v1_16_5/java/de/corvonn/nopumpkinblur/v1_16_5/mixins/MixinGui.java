package de.corvonn.nopumpkinblur.v1_16_5.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import de.corvonn.nopumpkinblur.NoPumpkinBlur;
import de.corvonn.nopumpkinblur.NoPumpkinBlurConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
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

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "renderPumpkin", at = @At("HEAD"), cancellable = true)
    private void mixinRenderTextureOverlay(CallbackInfo ci) {
        NoPumpkinBlur instance = NoPumpkinBlur.getInstance();
        if(instance == null) return; //Necessary?
        NoPumpkinBlurConfig config = instance.configuration();
        if(!config.enabled().get()) return;

        float opacity = config.opacity().get();

        ci.cancel();

        if(opacity == 0) return;

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, opacity);
        RenderSystem.disableAlphaTest();
        this.minecraft.getTextureManager().bind(PUMPKIN_BLUR_LOCATION);
        Tesselator lvt_1_1_ = Tesselator.getInstance();
        BufferBuilder lvt_2_1_ = lvt_1_1_.getBuilder();
        lvt_2_1_.begin(7, DefaultVertexFormat.POSITION_TEX);
        lvt_2_1_.vertex(0.0, (double)this.screenHeight, -90.0).uv(0.0F, 1.0F).endVertex();
        lvt_2_1_.vertex((double)this.screenWidth, (double)this.screenHeight, -90.0).uv(1.0F, 1.0F).endVertex();
        lvt_2_1_.vertex((double)this.screenWidth, 0.0, -90.0).uv(1.0F, 0.0F).endVertex();
        lvt_2_1_.vertex(0.0, 0.0, -90.0).uv(0.0F, 0.0F).endVertex();
        lvt_1_1_.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableAlphaTest();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, opacity);
    }
}
