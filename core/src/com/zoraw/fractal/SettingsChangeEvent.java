package com.zoraw.fractal;

import com.badlogic.gdx.scenes.scene2d.Event;

public class SettingsChangeEvent extends Event {

    private final Settings settings;

    public SettingsChangeEvent(Settings settings) {
        this.settings = settings;
    }

    public Settings getSettings() {
        return settings;
    }



}
