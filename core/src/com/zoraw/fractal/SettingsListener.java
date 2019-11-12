package com.zoraw.fractal;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lombok.Builder;

@Builder
public class SettingsListener extends ClickListener {

    private final TextField initRe;
    private final TextField initIm;
    private final TextField numberOfIteration;
    private final TextField r;
    private final TextField g;
    private final TextField b;

    private final SettingsTable settingsTable;

    @Override
    public void clicked(InputEvent event, float x, float y) {
        updateSettings();
    }

    private void updateSettings() {
        settingsTable.fire(new SettingsChangeEvent(
                Settings.builder()
                        .initNumber(new ComplexNumber(Double.parseDouble(initRe.getText()),
                                Double.parseDouble(initIm.getText())))
                        .rMultiplier(Integer.parseInt(r.getText()))
                        .gMultiplier(Integer.parseInt(g.getText()))
                        .bMultiplier(Integer.parseInt(b.getText()))
                        .numberOfIteration(Integer.parseInt(numberOfIteration.getText()))
                        .build()));
    }
}
