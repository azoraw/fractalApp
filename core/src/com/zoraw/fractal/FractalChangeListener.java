package com.zoraw.fractal;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class FractalChangeListener implements EventListener {

    private final ChangeFractalSettings changeFractalSettings;

    public FractalChangeListener(ChangeFractalSettings changeFractalSettings) {
        this.changeFractalSettings = changeFractalSettings;
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof ChangeListener.ChangeEvent) {
            ChangeListener.ChangeEvent change = (ChangeListener.ChangeEvent) event;
            String selectedFractal = (String) ((SelectBox) change.getListenerActor()).getSelection().getLastSelected();
            this.changeFractalSettings.changeFractal(Fractal.fromString(selectedFractal));
            return false;
        }
        return true;
    }
}
