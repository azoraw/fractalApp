package com.zoraw.fractal;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SettingsListener extends ClickListener {
    private final TextField textField;
    private final SettingsTable settingsTable;

    public SettingsListener(TextField textField, SettingsTable settingsTable) {
        this.textField = textField;
        this.settingsTable = settingsTable;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        updateSettings();
    }

    private void updateSettings() {
        settingsTable.fire(new SettingsChangeEvent(textField.getText()));
    }
}
