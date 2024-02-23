package de.corvonn.nopumpkinblur;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
@AddonMain
public class NoPumpkinBlur extends LabyAddon<NoPumpkinBlurConfig> {
    private static NoPumpkinBlur instance;

    @Override
    protected void enable() {
    this.registerSettingCategory();
    instance = this;

    this.logger().info("Loaded " + addonInfo().getDisplayName() + " v" + addonInfo().getVersion() + " by " + addonInfo().getAuthor());
    }

    @Override
    protected Class<NoPumpkinBlurConfig> configurationClass() {
    return NoPumpkinBlurConfig.class;
    }

    public static NoPumpkinBlur getInstance() {
        return instance;
    }
}
