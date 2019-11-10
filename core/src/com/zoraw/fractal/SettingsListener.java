package com.zoraw.fractal;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SettingsListener extends ClickListener {

    private final TextField initRe;
    private final TextField initIm;
    private final TextField numberOfIteration;

    private final SettingsTable settingsTable;

    public SettingsListener(TextField initRe, TextField initIm, TextField numberOfIteration, SettingsTable settingsTable) {
        this.initRe = initRe;
        this.initIm = initIm;
        this.numberOfIteration = numberOfIteration;
        this.settingsTable = settingsTable;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        updateSettings();
    }

    private void updateSettings() {
        settingsTable.fire(new SettingsChangeEvent(new ComplexNumber(Double.parseDouble(initRe.getText()), Double.parseDouble(initIm.getText())), Integer.parseInt(numberOfIteration.getText())));
    }
}
