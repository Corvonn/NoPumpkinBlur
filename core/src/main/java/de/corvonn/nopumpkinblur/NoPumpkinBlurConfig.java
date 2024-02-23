package de.corvonn.nopumpkinblur;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class NoPumpkinBlurConfig extends AddonConfig {

    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SliderSetting(min = 0f, max = 1f, steps = 0.1f)
    private final ConfigProperty<Float> opacity = new ConfigProperty<>(0f);

    @Override
    public ConfigProperty<Boolean> enabled() {
    return this.enabled;
    }

    public ConfigProperty<Float> opacity() {return opacity;}

}
