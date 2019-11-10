package com.zoraw.fractal;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public class SettingsChangeListener implements EventListener {

    @Override
    public boolean handle(Event event) {
        if (event instanceof SettingsChangeEvent) {
            return true;
        }
        return false;
    }
}
