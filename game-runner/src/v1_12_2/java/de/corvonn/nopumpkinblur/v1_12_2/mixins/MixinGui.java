package de.corvonn.nopumpkinblur.v1_12_2.mixins;

import de.corvonn.nopumpkinblur.NoPumpkinBlur;
import de.corvonn.nopumpkinblur.NoPumpkinBlurConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)

public class MixinGui {
    @Shadow
    @Final
    private static ResourceLocation PUMPKIN_BLUR_TEX_PATH;

    @Shadow
    @Final
    private Minecraft mc;

    @Inject(method = "renderPumpkinOverlay", at = @At("HEAD"), cancellable = true)
    private void mixinRenderTextureOverlay(ScaledResolution lvt_1_1_, CallbackInfo ci) {
        NoPumpkinBlur instance = NoPumpkinBlur.getInstance();
        if(instance == null) return; //Necessary?
        NoPumpkinBlurConfig config = instance.configuration();
        if(!config.enabled().get()) return;

        float opacity = config.opacity().get();

        ci.cancel();

        if(opacity == 0) return;

        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, opacity);
        GlStateManager.disableAlpha();
        this.mc.getTextureManager().bindTexture(PUMPKIN_BLUR_TEX_PATH);
        Tessellator lvt_2_1_ = Tessellator.getInstance();
        BufferBuilder lvt_3_1_ = lvt_2_1_.getBuffer();
        lvt_3_1_.begin(7, DefaultVertexFormats.POSITION_TEX);
        lvt_3_1_.pos(0.0, (double)lvt_1_1_.getScaledHeight(), -90.0).tex(0.0, 1.0).endVertex();
        lvt_3_1_.pos((double)lvt_1_1_.getScaledWidth(), (double)lvt_1_1_.getScaledHeight(), -90.0).tex(1.0, 1.0).endVertex();
        lvt_3_1_.pos((double)lvt_1_1_.getScaledWidth(), 0.0, -90.0).tex(1.0, 0.0).endVertex();
        lvt_3_1_.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex();
        lvt_2_1_.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, opacity);
    }
}
