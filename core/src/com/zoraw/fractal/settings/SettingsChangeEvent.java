package com.zoraw.fractal.settings;

import com.badlogic.gdx.scenes.scene2d.Event;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class SettingsChangeEvent extends Event {

    private final Settings settings;

}
