package com.zoraw.fractal;

import com.badlogic.gdx.scenes.scene2d.Event;

public class SettingsChangeEvent extends Event {

    private final ComplexNumber initNumber;

    public SettingsChangeEvent(ComplexNumber initNumber) {
        this.initNumber = initNumber;
    }

    public ComplexNumber getInitNumber() {
        return initNumber;
    }


}
