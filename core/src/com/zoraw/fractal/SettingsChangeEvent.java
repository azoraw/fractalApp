package com.zoraw.fractal;

import com.badlogic.gdx.scenes.scene2d.Event;

public class SettingsChangeEvent extends Event {

    private final double initRe;
    private final double initIm;

    public SettingsChangeEvent(double initRe, double initIm) {
        this.initRe = initRe;
        this.initIm = initIm;
    }

    public double getInitRe() {
        return initRe;
    }

    public double getInitIm() {
        return initIm;
    }

}
