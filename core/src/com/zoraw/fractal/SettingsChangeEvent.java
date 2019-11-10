package com.zoraw.fractal;

import com.badlogic.gdx.scenes.scene2d.Event;

public class SettingsChangeEvent extends Event {

    public SettingsChangeEvent(String field) {
        this.field = field;
    }

    String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
