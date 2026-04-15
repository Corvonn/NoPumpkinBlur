package de.corvonn.nopumpkinblur;

import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
@AddonMain
public class NoPumpkinBlur extends LabyAddon<NoPumpkinBlurConfig> {
    @Override
    protected void enable() {
        this.registerSettingCategory();
        Laby.labyAPI().eventBus().registerListener(new Listener(this.configuration()));
    }

    @Override
    protected Class<NoPumpkinBlurConfig> configurationClass() {
    return NoPumpkinBlurConfig.class;
    }
}
