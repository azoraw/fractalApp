package com.zoraw.fractal;

import com.badlogic.gdx.scenes.scene2d.Event;

public class SettingsChangeEvent extends Event {

    private final ComplexNumber initNumber;

    public int getNumberOfIteration() {
        return numberOfIteration;
    }

    private final int numberOfIteration;


    public SettingsChangeEvent(ComplexNumber initNumber, int numberOfIteration) {
        this.initNumber = initNumber;
        this.numberOfIteration = numberOfIteration;
    }

    public ComplexNumber getInitNumber() {
        return initNumber;
    }


}
