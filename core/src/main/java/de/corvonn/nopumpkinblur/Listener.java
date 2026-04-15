package de.corvonn.nopumpkinblur;


import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.overlay.CameraOverlayRenderEvent;
import net.labymod.api.event.client.render.overlay.CameraOverlayRenderEvent.OverlayType;
import org.jetbrains.annotations.NotNull;

public class Listener {
    private final NoPumpkinBlurConfig config;

    public Listener(@NotNull NoPumpkinBlurConfig config) {
        this.config = config;
    }

    @Subscribe
    public void onCameraOverlayRender(@NotNull CameraOverlayRenderEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }

        if (event.overlayType() == OverlayType.PUMPKIN) {
            event.setOpacity(this.config.opacity().get());
        }
    }
}
